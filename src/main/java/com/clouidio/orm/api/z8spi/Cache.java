package com.clouidio.orm.api.z8spi;

import com.clouidio.orm.api.z8spi.meta.DboTableMeta;

public interface Cache {

	RowHolder<Row> fromCache(DboTableMeta colFamily, byte[] key);

	void cacheRow(DboTableMeta colFamily, byte[] b, Row value);

}
