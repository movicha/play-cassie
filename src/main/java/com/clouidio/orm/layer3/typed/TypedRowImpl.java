package com.clouidio.orm.layer3.typed;

import java.util.HashMap;
import java.util.Map;

import com.clouidio.orm.api.z8spi.meta.DboTableMeta;
import com.clouidio.orm.api.z8spi.meta.TypedColumn;
import com.clouidio.orm.api.z8spi.meta.TypedRow;
import com.clouidio.orm.api.z8spi.meta.ViewInfo;

public class TypedRowImpl extends TypedRow {

	private Map<String, TypedColumn> originalValues = new HashMap<String, TypedColumn>();

	public TypedRowImpl(ViewInfo view, DboTableMeta metaClass) {
		super(view, metaClass);
	}

	public Map<String, TypedColumn> getOriginalValues() {
		return originalValues;
	}

	public void setOriginalValues(Map<String, TypedColumn> originalValues) {
		this.originalValues = originalValues;
	}

}