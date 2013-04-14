package com.clouidio.orm.layer0.base;

import java.util.Iterator;

import com.clouidio.orm.api.z8spi.conv.Precondition;
import com.clouidio.orm.api.z8spi.iter.AbstractIterator;

public class IterProxy extends AbstractIterator<byte[]> {

	private Iterator<byte[]> iterator;

	public IterProxy(Iterator<byte[]> iter) {
		Precondition.check(iter, "iter");
		this.iterator = iter;
	}

	@Override
	public com.clouidio.orm.api.z8spi.iter.AbstractIterator.IterHolder<byte[]> nextImpl() {
		if (!iterator.hasNext())
			return null;
		return new IterHolder<byte[]>(iterator.next());
	}
}
