package com.clouidio.playcassie.monitor.bindings;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.clouidio.playcassie.monitor.api.MonitorServiceFactory;
import com.clouidio.playcassie.monitor.impl.Config;
import com.clouidio.playcassie.monitor.impl.HashGenerator;
import com.clouidio.playcassie.monitor.impl.HashGeneratorImpl;

import com.google.inject.Binder;
import com.google.inject.Module;

public class MonitorProdBindings implements Module {

	public static final String SCHEDULER = "com.clouidio.playcassie.monitor.scheduler";
	public static final String HASH_GENERATOR = "com.clouidio.playcassie.monitor.hashGenerator";

	private ScheduledExecutorService svc;
	private long rate;
	private Config config;
	private HashGenerator generator;

	public MonitorProdBindings(Map<String, Object> properties) {
		Object obj = properties.get(SCHEDULER);
		if (obj == null)
			svc = Executors.newScheduledThreadPool(1);
		else
			svc = (ScheduledExecutorService) obj;

		Object rateObj = properties.get(MonitorServiceFactory.SCAN_RATE_MILLIS);
		if (!(rateObj instanceof String))
			throw new IllegalArgumentException(
					"SCAN_RATE_MILLIS must be a long as a String");
		String rateStr = (String) rateObj;
		rate = Long.parseLong(rateStr);

		String host = (String) properties
				.get(MonitorServiceFactory.HOST_UNIQUE_NAME);

		config = new Config(rate, host);

		generator = (HashGenerator) properties.get(HASH_GENERATOR);
		if (generator == null)
			generator = new HashGeneratorImpl();
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(ScheduledExecutorService.class).toInstance(svc);
		binder.bind(HashGenerator.class).toInstance(generator);

		binder.bind(Config.class).toInstance(config);
	}
}
