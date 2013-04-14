package com.clouidio.orm.layer9z.spi.db.cassandra;

import com.clouidio.orm.api.z8spi.KeyValue;
import com.clouidio.orm.api.z8spi.Row;
import com.clouidio.orm.api.z8spi.iter.AbstractCursor;
import com.clouidio.orm.api.z8spi.iter.DirectCursor;

public class CursorReturnsEmptyRows extends AbstractCursor<KeyValue<Row>> {

	private DirectCursor<byte[]> keys;

	public CursorReturnsEmptyRows(DirectCursor<byte[]> keys) {
		if (keys == null)
			throw new IllegalArgumentException("keys cannot be null");
		this.keys = keys;
		beforeFirst();
	}

	@Override
	public void beforeFirst() {
		keys.beforeFirst();
	}

	@Override
	public void afterLast() {
		keys.afterLast();
	}

	@Override
	public com.clouidio.orm.api.z8spi.iter.AbstractCursor.Holder<KeyValue<Row>> nextImpl() {
		Holder<byte[]> holder = keys.nextImpl();
		if (holder == null)
			return null;

		byte[] key = holder.getValue();
		KeyValue<Row> kv = new KeyValue<Row>();
		kv.setKey(key);

		return new Holder<KeyValue<Row>>(kv);
	}

	@Override
	public com.clouidio.orm.api.z8spi.iter.AbstractCursor.Holder<KeyValue<Row>> previousImpl() {
		Holder<byte[]> holder = keys.previousImpl();
		if (holder == null)
			return null;

		byte[] key = holder.getValue();
		KeyValue<Row> kv = new KeyValue<Row>();
		kv.setKey(key);

		return new Holder<KeyValue<Row>>(kv);
	}
}
