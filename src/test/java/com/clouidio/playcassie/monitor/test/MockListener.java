package com.clouidio.playcassie.monitor.test;

import com.clouidio.playcassie.monitor.api.MonitorListener;
import com.clouidio.playcassie.monitor.api.PlayCassieMonitor;

public class MockListener implements MonitorListener {

	private PlayCassieMonitor lastFired;

	@Override
	public void monitorFired(PlayCassieMonitor m) {
		this.lastFired = m;
	}

	public PlayCassieMonitor getLastFiredMonitor() {
		PlayCassieMonitor temp = lastFired;
		lastFired = null;
		return temp;
	}

}
