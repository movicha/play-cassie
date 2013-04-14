package com.clouidio.orm.api.z8spi;

public interface BatchListener {

	public void beforeFetchingNextBatch();

	public void afterFetchingNextBatch(int numFetched);

}
