package com.clouidio.orm.api.z8spi.meta;

import com.clouidio.orm.api.z8spi.action.IndexColumn;
import com.clouidio.orm.api.z8spi.conv.StandardConverters;

public class IndexData {
	private String columnFamilyName;
	private String rowKey;
	private IndexColumn indexColumn = new IndexColumn();

	public void setColumnFamilyName(String indexTableName) {
		this.columnFamilyName = indexTableName;
	}

	public void setRowKey(String key) {
		this.rowKey = key;
	}

	public String getColumnFamilyName() {
		return columnFamilyName;
	}

	public String getRowKey() {
		return rowKey;
	}

	public byte[] getRowKeyBytes() {
		return StandardConverters.convertToBytes(rowKey);
	}

	public IndexColumn getIndexColumn() {
		return indexColumn;
	}

}
