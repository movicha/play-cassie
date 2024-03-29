package com.clouidio.test.db;

import com.clouidio.orm.api.base.anno.NoSqlEntity;
import com.clouidio.orm.api.base.anno.NoSqlId;

@NoSqlEntity
public class EntityWithIntKey {

	@NoSqlId(usegenerator = false)
	private int id;

	private String something;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSomething() {
		return something;
	}

	public void setSomething(String something) {
		this.something = something;
	}

}
