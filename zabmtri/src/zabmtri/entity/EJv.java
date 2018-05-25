package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zabmtri.AppData;

public class EJv {
	public Integer jvid;
	public String jvnumber;
	public Date transdate;
	public Integer glyear;
	public Integer glperiod;
	public String source;
	public String transtype;
	public BigDecimal jvamount;
	public Integer posted;
	public Integer autoreversed;
	public Integer glhistid;
	public String locked_by;
	public Date locked_time;
	public String chequeno;
	public String payee;
	public Integer voidcheque;
	public Integer reconcileid;
	public Integer templateid;
	public String branchcode;
	public Integer transactionid;
	public Integer importedtransactionid;
	public Integer branchcodeid;
	public String transdescription;
	public Integer recurringid;

	public List<EJvDet> detail;

	public static List<EJv> readAll(Connection conn, String transType) {
		try {
			PreparedStatement ps = conn.prepareStatement(getSqlQuery());
			ps.setDate(1, Date.valueOf(AppData.dateCutOff));
			ps.setString(2, transType);

			ResultSet rs = ps.executeQuery();

			List<EJv> result = new ArrayList<EJv>();
			while (rs.next()) {
				EJv row = EJv.read(rs);
				result.add(row);
			}

			for (EJv row : result) {
				row.detail = EJvDet.readAll(conn, row.jvid);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static String getSqlQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT jv.* FROM jv ");
		sb.append("WHERE jv.transdate > ? AND jv.source = 'GL' AND jv.transtype = ? ");
		sb.append("ORDER BY jv.transdate, jv.jvid ");

		return sb.toString();
	}

	public static EJv read(ResultSet rs) throws SQLException {
		EJv entity = new EJv();
		entity.jvid = rs.getInt("jvid");
		entity.jvnumber = rs.getString("jvnumber");
		entity.transdate = rs.getDate("transdate");
		entity.glyear = rs.getInt("glyear");
		entity.glperiod = rs.getInt("glperiod");
		entity.source = rs.getString("source");
		entity.transtype = rs.getString("transtype");
		entity.jvamount = rs.getBigDecimal("jvamount");
		entity.posted = rs.getInt("posted");
		entity.autoreversed = rs.getInt("autoreversed");
		entity.glhistid = rs.getInt("glhistid");
		entity.locked_by = rs.getString("locked_by");
		entity.locked_time = rs.getDate("locked_time");
		entity.chequeno = rs.getString("chequeno");
		entity.payee = rs.getString("payee");
		entity.voidcheque = rs.getInt("voidcheque");
		entity.reconcileid = rs.getInt("reconcileid");
		entity.templateid = rs.getInt("templateid");
		entity.branchcode = rs.getString("branchcode");
		entity.transactionid = rs.getInt("transactionid");
		entity.importedtransactionid = rs.getInt("importedtransactionid");
		entity.branchcodeid = rs.getInt("branchcodeid");
		entity.transdescription = rs.getString("transdescription");
		entity.recurringid = rs.getInt("recurringid");

		return entity;
	}
}
