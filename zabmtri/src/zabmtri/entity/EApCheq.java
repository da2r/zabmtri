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
import zabmtri.DbUtil;

public class EApCheq {

	public Integer chequeid;
	public String chequeno;
	public String bankaccnt;
	public Integer vendorid;
	public Integer glyear;
	public Integer glperiod;
	public Date chequedate;
	public BigDecimal chequeamount;
	public Integer reconciled;
	public Integer posted;
	public Integer isVoid;
	public BigDecimal discountamount;
	public Integer glhistid;
	public BigDecimal rate;
	public String locked_by;
	public Date locked_time;
	public String sequenceno;
	public Date paymentdate;
	public Integer fiscalpmt;
	public String payee;
	public Integer reconcileid;
	public Integer templateid;
	public Integer deptid;
	public Integer projectid;
	public Integer transactionid;
	public Integer importedtransactionid;
	public Integer branchcodeid;
	public String description;

	public String vendorno;
	public String currencyname;

	public List<EApInvChq> detail;

	public static List<EApCheq> readAll(Connection conn) {
		try {
			PreparedStatement ps = conn.prepareStatement(getSqlQuery());
			ps.setDate(1, Date.valueOf(AppData.dateCutOff));

			ResultSet rs = ps.executeQuery();

			List<EApCheq> result = new ArrayList<EApCheq>();
			while (rs.next()) {
				EApCheq row = EApCheq.read(rs);
				result.add(row);
			}

			for (EApCheq row : result) {
				row.vendorno = DbUtil.getPersonNo(conn, row.vendorid);
				row.currencyname = DbUtil.getPersonCurrencyName(conn, row.vendorid);
				row.detail = EApInvChq.readAll(conn, row.chequeid);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static String getSqlQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ApCheq ");
		sb.append("WHERE chequedate > ? ");
		sb.append("ORDER BY chequedate, chequeid ");

		return sb.toString();
	}

	public static EApCheq read(ResultSet rs) throws SQLException {
		EApCheq entity = new EApCheq();
		entity.chequeid = rs.getInt("chequeid");
		entity.chequeno = rs.getString("chequeno");
		entity.bankaccnt = rs.getString("bankaccnt");
		entity.vendorid = rs.getInt("vendorid");
		entity.glyear = rs.getInt("glyear");
		entity.glperiod = rs.getInt("glperiod");
		entity.chequedate = rs.getDate("chequedate");
		entity.chequeamount = rs.getBigDecimal("chequeamount");
		entity.reconciled = rs.getInt("reconciled");
		entity.posted = rs.getInt("posted");
		entity.isVoid = rs.getInt("void");
		entity.discountamount = rs.getBigDecimal("discountamount");
		entity.glhistid = rs.getInt("glhistid");
		entity.rate = rs.getBigDecimal("rate");
		entity.locked_by = rs.getString("locked_by");
		entity.locked_time = rs.getDate("locked_time");
		entity.sequenceno = rs.getString("sequenceno");
		entity.paymentdate = rs.getDate("paymentdate");
		entity.fiscalpmt = rs.getInt("fiscalpmt");
		entity.payee = rs.getString("payee");
		entity.reconcileid = rs.getInt("reconcileid");
		entity.templateid = rs.getInt("templateid");
		entity.deptid = rs.getInt("deptid");
		entity.projectid = rs.getInt("projectid");
		entity.transactionid = rs.getInt("transactionid");
		entity.importedtransactionid = rs.getInt("importedtransactionid");
		entity.branchcodeid = rs.getInt("branchcodeid");
		entity.description = rs.getString("description");

		return entity;
	}
}
