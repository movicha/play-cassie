package com.clouidio.playcassie.monitor.api;

import java.util.List;

public interface MonitorService {

	void start();

	void addListener(MonitorListener listener);

	void saveMonitor(PlayCassieMonitor monitor);

	PlayCassieMonitor getMonitor(String id);

	List<PlayCassieMonitor> getMonitors(List<String> ids);

}
