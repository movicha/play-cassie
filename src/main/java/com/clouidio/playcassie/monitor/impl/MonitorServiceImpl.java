package com.clouidio.playcassie.monitor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.joda.time.DateTime;
import com.clouidio.playcassie.monitor.api.MonitorListener;
import com.clouidio.playcassie.monitor.api.MonitorService;
import com.clouidio.playcassie.monitor.api.PlayCassieMonitor;
import com.clouidio.playcassie.monitor.impl.db.MonitorDbo;
import com.clouidio.playcassie.monitor.impl.db.WebNodeDbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clouidio.orm.api.base.NoSqlEntityManager;
import com.clouidio.orm.api.base.NoSqlEntityManagerFactory;
import com.clouidio.orm.api.z8spi.KeyValue;
import com.clouidio.orm.api.z8spi.iter.Cursor;

public class MonitorServiceImpl implements MonitorService {

	private static final Logger log = LoggerFactory
			.getLogger(MonitorServiceImpl.class);

	@Inject
	private ScheduledExecutorService svc;
	@Inject
	private Config config;
	@Inject
	private CheckClusterRunnable clusterRunnable;

	private NoSqlEntityManagerFactory factory;

	@Override
	public void start() {
		String host = config.getHostName();
		WebNodeDbo node = new WebNodeDbo();
		node.setLastSeen(new DateTime());
		node.setWebServerName(host);
		node.setUp(true);

		NoSqlEntityManager mgr = factory.createEntityManager();
		mgr.put(node);
		mgr.flush();

		clusterRunnable.setFactory(factory);
		log.info("running monitor service at rate=" + config.getRate()
				+ " milliseconds");
		svc.scheduleAtFixedRate(clusterRunnable, 30000, config.getRate(),
				TimeUnit.MILLISECONDS);
	}

	@Override
	public void addListener(MonitorListener listener) {
		clusterRunnable.setListener(listener);
	}

	@Override
	public void saveMonitor(PlayCassieMonitor monitor) {
		MonitorDbo m = CopyUtil.copy(monitor);
		NoSqlEntityManager mgr = factory.createEntityManager();
		mgr.put(m, false);
		mgr.flush();
	}

	@Override
	public PlayCassieMonitor getMonitor(String id) {
		NoSqlEntityManager mgr = factory.createEntityManager();
		MonitorDbo mon = mgr.find(MonitorDbo.class, id);
		return CopyUtil.copy(mon);
	}

	public List<PlayCassieMonitor> getMonitors(List<String> ids) {
		NoSqlEntityManager mgr = factory.createEntityManager();
		Cursor<KeyValue<MonitorDbo>> cursor = mgr
				.findAll(MonitorDbo.class, ids);
		List<PlayCassieMonitor> monitors = new ArrayList<PlayCassieMonitor>();
		while (cursor.next()) {
			KeyValue<MonitorDbo> kv = cursor.getCurrent();
			MonitorDbo mon = kv.getValue();
			monitors.add(CopyUtil.copy(mon));
		}
		return monitors;
	}

	public void setFactory(NoSqlEntityManagerFactory factory2) {
		this.factory = factory2;
	}

}
