package com.clouidio.ssql.cmdline;

import com.clouidio.orm.api.base.NoSqlEntityManager;
import com.clouidio.orm.api.exc.ParseException;
import com.clouidio.orm.api.z3api.NoSqlTypedSession;

public class CmdDelete {
	void processDelete(String cmd, NoSqlEntityManager mgr) {
		NoSqlTypedSession s = mgr.getTypedSession();
		try {
			int count = s.executeQuery(cmd);
			mgr.flush();
			println(count + " row deleted");
		} catch (ParseException e) {
			Throwable childExc = e.getCause();
			throw new InvalidCommand(
					"Scalable-SQL command was invalid.  Reason="
							+ childExc.getMessage()
							+ " AND you may want to add -v option to playcli to get more info",
					e);
		}
	}

	void processDeleteColumn(String cmd, NoSqlEntityManager mgr) {
		NoSqlTypedSession s = mgr.getTypedSession();
		try {
			int count = s.executeQuery(cmd);
			mgr.flush();
			println("Column deleted from " + count + " rows");
		} catch (ParseException e) {
			Throwable childExc = e.getCause();
			throw new InvalidCommand(
					"Scalable-SQL command was invalid.  Reason="
							+ childExc.getMessage()
							+ " AND you may want to add -v option to playcli to get more info",
					e);
		}
	}

	private void println(String msg) {
		System.out.println(msg);
	}
}
