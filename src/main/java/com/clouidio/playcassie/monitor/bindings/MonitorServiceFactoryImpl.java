package com.clouidio.playcassie.monitor.bindings;

import java.util.Map;

import com.clouidio.playcassie.monitor.api.MonitorService;
import com.clouidio.playcassie.monitor.api.MonitorServiceFactory;
import com.clouidio.playcassie.monitor.impl.MonitorServiceImpl;

import com.clouidio.orm.api.base.NoSqlEntityManagerFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MonitorServiceFactoryImpl extends MonitorServiceFactory {

	@Override
	protected MonitorService createService(Map<String, Object> properties) {
		Injector injector = Guice.createInjector(new MonitorProdBindings(
				properties));
		MonitorServiceImpl impl = injector
				.getInstance(MonitorServiceImpl.class);

		Object factoryObj = properties
				.get(MonitorServiceFactory.NOSQL_MGR_FACTORY);
		if (factoryObj == null)
			throw new IllegalArgumentException(
					"NOSQL_MGR_FACTORY is required and must be set");
		else if (!(factoryObj instanceof NoSqlEntityManagerFactory))
			throw new IllegalArgumentException(
					"NOSQL_MGR_FACTORY is not an instance of NoSqlEntityManagerFactory");
		NoSqlEntityManagerFactory factory = (NoSqlEntityManagerFactory) factoryObj;
		impl.setFactory(factory);
		return impl;
	}

}
