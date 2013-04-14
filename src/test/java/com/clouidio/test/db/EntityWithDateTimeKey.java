package com.clouidio.test.db;

import org.joda.time.LocalDateTime;

import com.clouidio.orm.api.base.anno.NoSqlEntity;
import com.clouidio.orm.api.base.anno.NoSqlId;

@NoSqlEntity
public class EntityWithDateTimeKey {

	@NoSqlId(usegenerator = false)
	private LocalDateTime id;

	private String something;

	public LocalDateTime getId() {
		return id;
	}

	public void setId(LocalDateTime id) {
		this.id = id;
	}

	public String getSomething() {
		return something;
	}

	public void setSomething(String something) {
		this.something = something;
	}

}
