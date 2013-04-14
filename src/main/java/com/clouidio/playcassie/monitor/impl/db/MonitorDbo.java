package com.clouidio.playcassie.monitor.impl.db;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.clouidio.orm.api.base.NoSqlEntityManager;
import com.clouidio.orm.api.base.Query;
import com.clouidio.orm.api.base.anno.NoSqlEmbedded;
import com.clouidio.orm.api.base.anno.NoSqlEntity;
import com.clouidio.orm.api.base.anno.NoSqlId;
import com.clouidio.orm.api.base.anno.NoSqlIndexed;
import com.clouidio.orm.api.base.anno.NoSqlQuery;
import com.clouidio.orm.api.z8spi.KeyValue;
import com.clouidio.orm.api.z8spi.iter.Cursor;

@NoSqlEntity
@NoSqlQuery(name = "all", query = "select m from TABLE as m")
public class MonitorDbo {

	@NoSqlId(usegenerator = false)
	private String id;

	@NoSqlIndexed
	private long timePeriodMillis;

	private String rawProperties = "";

	@NoSqlEmbedded
	private List<MonitorProperty> properties = new ArrayList<MonitorProperty>();

	private DateTime lastRun;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getTimePeriodMillis() {
		return timePeriodMillis;
	}

	public void setTimePeriodMillis(long timePeriodMillis) {
		this.timePeriodMillis = timePeriodMillis;
	}

	public DateTime getLastRun() {
		return lastRun;
	}

	public void setLastRun(DateTime lastRun) {
		this.lastRun = lastRun;
	}

	public static Cursor<KeyValue<MonitorDbo>> findAll(NoSqlEntityManager mgr) {
		Query<MonitorDbo> query = mgr.createNamedQuery(MonitorDbo.class, "all");
		return query.getResults();
	}

	public void setRawProperties(String props) {
		this.rawProperties = props;
	}

	public String getRawProperties() {
		return rawProperties;
	}
}
