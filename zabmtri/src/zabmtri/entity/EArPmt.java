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

public class EArPmt {

	public Integer paymentid;
	public String chequeno;
	public String bankaccount;
	public Date chequedate;
	public Integer billtoid;
	public Integer glyear;
	public Integer glperiod;
	public Date paymentdate;
	public BigDecimal chequeamount;
	public BigDecimal discountamount;
	public Integer posted;
	public BigDecimal rate;
	public Integer glhistid;
	public String locked_by;
	public Date locked_time;
	public BigDecimal overpay;
	public Integer fiscalpmt;
	public String sequenceno;
	public String aroverpay;
	public BigDecimal overpayused;
	public Integer applyfromcredit;
	public Integer reconcileid;
	public Integer templateid;
	public Integer deptid;
	public Integer projectid;
	public Integer transactionid;
	public Integer importedtransactionid;
	public Integer branchcodeid;
	public Integer isVoid;
	public Integer returncredit;
	public String description;

	public String currencyname;
	
	public List<EArInvPmt> detail;

	public static List<EArPmt> readAll(Connection conn) {
		try {
			PreparedStatement ps = conn.prepareStatement(getSqlQuery());
			ps.setDate(1, Date.valueOf(AppData.dateCutOff));

			ResultSet rs = ps.executeQuery();

			List<EArPmt> result = new ArrayList<EArPmt>();
			while (rs.next()) {
				EArPmt row = EArPmt.read(rs);
				result.add(row);
			}

			for (EArPmt row : result) {
				row.currencyname = DbUtil.getPersonCurrencyName(conn, row.billtoid);
				//
				row.detail = EArInvPmt.readAll(conn, row.paymentid);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static String getSqlQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM arpmt ");
		sb.append("WHERE paymentdate > ? ");
		sb.append("ORDER BY paymentdate, paymentid ");

		return sb.toString();
	}

	public static EArPmt read(ResultSet rs) throws SQLException {
		EArPmt entity = new EArPmt();
		entity.paymentid = rs.getInt("paymentid");
		entity.chequeno = rs.getString("chequeno");
		entity.bankaccount = rs.getString("bankaccount");
		entity.chequedate = rs.getDate("chequedate");
		entity.billtoid = rs.getInt("billtoid");
		entity.glyear = rs.getInt("glyear");
		entity.glperiod = rs.getInt("glperiod");
		entity.paymentdate = rs.getDate("paymentdate");
		entity.chequeamount = rs.getBigDecimal("chequeamount");
		entity.discountamount = rs.getBigDecimal("discountamount");
		entity.posted = rs.getInt("posted");
		entity.rate = rs.getBigDecimal("rate");
		entity.glhistid = rs.getInt("glhistid");
		entity.locked_by = rs.getString("locked_by");
		entity.locked_time = rs.getDate("locked_time");
		entity.overpay = rs.getBigDecimal("overpay");
		entity.fiscalpmt = rs.getInt("fiscalpmt");
		entity.sequenceno = rs.getString("sequenceno");
		entity.aroverpay = rs.getString("aroverpay");
		entity.overpayused = rs.getBigDecimal("overpayused");
		entity.applyfromcredit = rs.getInt("applyfromcredit");
		entity.reconcileid = rs.getInt("reconcileid");
		entity.templateid = rs.getInt("templateid");
		entity.deptid = rs.getInt("deptid");
		entity.projectid = rs.getInt("projectid");
		entity.transactionid = rs.getInt("transactionid");
		entity.importedtransactionid = rs.getInt("importedtransactionid");
		entity.branchcodeid = rs.getInt("branchcodeid");
		entity.isVoid = rs.getInt("void");
		entity.returncredit = rs.getInt("returncredit");
		entity.description = rs.getString("description");

		return entity;
	}
}
