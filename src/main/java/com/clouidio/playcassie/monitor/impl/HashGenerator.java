package com.clouidio.playcassie.monitor.impl;

public interface HashGenerator {

	/**
	 * Returns the node number this should run on
	 * 
	 * @param monitorHash
	 * @param numUpWebNodes
	 * @return the node number this should run on
	 */
	int generate(int monitorHash, int numUpWebNodes);

}
