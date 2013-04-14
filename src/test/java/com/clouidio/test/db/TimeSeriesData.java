package com.clouidio.test.db;

import com.clouidio.orm.api.base.NoSqlEntityManager;
import com.clouidio.orm.api.base.Query;
import com.clouidio.orm.api.base.anno.NoSqlEntity;
import com.clouidio.orm.api.base.anno.NoSqlId;
import com.clouidio.orm.api.base.anno.NoSqlIndexed;
import com.clouidio.orm.api.base.anno.NoSqlQueries;
import com.clouidio.orm.api.base.anno.NoSqlQuery;
import com.clouidio.orm.api.base.anno.NoSqlVirtualCf;

@NoSqlEntity
@NoSqlVirtualCf(storedInCf = "ourstuff")
@NoSqlQueries({
		@NoSqlQuery(name = "findById", query = "select t from TABLE as t where t.key = :key"),
		@NoSqlQuery(name = "findByTemp", query = "select t from TABLE as t where t.temp = :temp") })
public class TimeSeriesData {

	@NoSqlId(usegenerator = false)
	@NoSqlIndexed
	private Long key;

	private String someName;

	@NoSqlIndexed
	private float temp;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getSomeName() {
		return someName;
	}

	public void setSomeName(String someName) {
		this.someName = someName;
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float number) {
		this.temp = number;
	}

	public static TimeSeriesData findById(NoSqlEntityManager mgr, Long id) {
		Query<TimeSeriesData> query = mgr.createNamedQuery(
				TimeSeriesData.class, "findById");
		query.setParameter("key", id);
		return query.getSingleObject();
	}

	public static TimeSeriesData findByTemp(NoSqlEntityManager mgr, float f) {
		Query<TimeSeriesData> query = mgr.createNamedQuery(
				TimeSeriesData.class, "findByTemp");
		query.setParameter("temp", f);
		return query.getSingleObject();
	}

}
