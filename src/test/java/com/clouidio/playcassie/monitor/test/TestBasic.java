package com.clouidio.playcassie.monitor.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.clouidio.playcassie.monitor.api.MonitorService;
import com.clouidio.playcassie.monitor.api.MonitorServiceFactory;
import com.clouidio.playcassie.monitor.api.PlayCassieMonitor;
import com.clouidio.playcassie.monitor.bindings.MonitorProdBindings;

import com.clouidio.orm.api.base.NoSqlEntityManagerFactory;
import com.clouidio.test.FactorySingleton;

public class TestBasic {

	private MonitorService server1Monitor;
	private MonitorService server2Monitor;
	private Runnable clusterChecker1;
	private Runnable clusterChecker2;
	private NoSqlEntityManagerFactory factory;
	private MockListener listener1;
	private MockListener listener2;
	private MockHash mockHash;

	@Before
	public void setup() {
		factory = FactorySingleton.createFactoryOnce();

		MockScheduler mock = new MockScheduler();
		mockHash = new MockHash();

		listener1 = new MockListener();
		listener2 = new MockListener();

		int rate = 5 * 60 * 1000;
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(MonitorServiceFactory.NOSQL_MGR_FACTORY, factory);
		props.put(MonitorProdBindings.SCHEDULER, mock);
		props.put(MonitorProdBindings.HASH_GENERATOR, mockHash);
		props.put(MonitorServiceFactory.SCAN_RATE_MILLIS, "" + rate);
		props.put(MonitorServiceFactory.HOST_UNIQUE_NAME, "host1");
		server1Monitor = MonitorServiceFactory.create(props);
		props.put(MonitorServiceFactory.HOST_UNIQUE_NAME, "host2");
		server2Monitor = MonitorServiceFactory.create(props);
		server1Monitor.addListener(listener1);
		server2Monitor.addListener(listener2);

		server1Monitor.start();
		clusterChecker1 = mock.getLastRunnable();

		server2Monitor.start();
		clusterChecker2 = mock.getLastRunnable();

		clusterChecker1.run();
		clusterChecker2.run();
		Assert.assertNull(listener1.getLastFiredMonitor());
		Assert.assertNull(listener2.getLastFiredMonitor());
	}

	@Test
	public void testBasic() throws InterruptedException {
		PlayCassieMonitor monitor = new PlayCassieMonitor();
		monitor.setId("asdf");
		monitor.setTimePeriodMillis(1);
		monitor.addProperty("email", "alex@clouidio.com");
		monitor.addProperty("myName", "alex");
		server1Monitor.saveMonitor(monitor);

		PlayCassieMonitor m = server1Monitor.getMonitor(monitor.getId());
		Assert.assertEquals(monitor.getTimePeriodMillis(),
				m.getTimePeriodMillis());
		String email1 = monitor.getProperties().get("email");
		String emailB = m.getProperties().get("email");
		Assert.assertEquals(email1, emailB);

		mockHash.addReturnValue(1); // identify the second server and run server
									// 1 then server 2
		clusterChecker1.run();
		mockHash.addReturnValue(1);
		clusterChecker2.run();

		Assert.assertNull(listener1.getLastFiredMonitor());
		m = listener2.getLastFiredMonitor();
		Assert.assertEquals(monitor.getId(), m.getId());

		Thread.sleep(30);// now if we run again, the period is one millisecond
							// so it should run again

		mockHash.addReturnValue(1);
		clusterChecker2.run();

		m = listener2.getLastFiredMonitor();
		Assert.assertEquals(monitor.getId(), m.getId());

		String email2 = m.getProperties().get("email");
		Assert.assertEquals(email1, email2);
	}
}
