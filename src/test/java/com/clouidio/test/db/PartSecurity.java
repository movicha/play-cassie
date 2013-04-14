package com.clouidio.test.db;

import com.clouidio.orm.api.base.anno.NoSqlEntity;
import com.clouidio.orm.api.base.anno.NoSqlId;
import com.clouidio.orm.api.base.anno.NoSqlIndexed;

@NoSqlEntity
public class PartSecurity {

	@NoSqlId
	private String id;

	@NoSqlIndexed
	private String securityType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

}
