package com.clouidio.playcassie.monitor.impl;

import com.clouidio.playcassie.monitor.api.PlayCassieMonitor;
import com.clouidio.playcassie.monitor.impl.db.MonitorDbo;

public class CopyUtil {

	public static MonitorDbo copy(PlayCassieMonitor monitor) {
		if (monitor == null)
			return null;
		MonitorDbo m = new MonitorDbo();
		m.setId(monitor.getId());
		m.setTimePeriodMillis(monitor.getTimePeriodMillis());

		String props = "";
		for (String key : monitor.getProperties().keySet()) {
			String value = monitor.getProperties().get(key);
			props += key + "=" + value + "|";
		}
		m.setRawProperties(props);
		return m;
	}

	public static PlayCassieMonitor copy(MonitorDbo monitor) {
		if (monitor == null)
			return null;
		PlayCassieMonitor mon = new PlayCassieMonitor();
		mon.setId(monitor.getId());
		mon.setTimePeriodMillis(monitor.getTimePeriodMillis());
		String props = monitor.getRawProperties();
		String[] propsArray = props.split("\\|");
		for (String prop : propsArray) {
			String[] kv = prop.split("=");
			mon.addProperty(kv[0], kv[1]);
		}
		return mon;
	}

}
