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
import zabmtri.Util;

public class EApInv {
	public Integer apinvoiceid;
	public Integer vendorid;
	public String invoiceno;
	public Integer invoiceseqno;
	public Date invoicedate;
	public Integer termsid;
	public BigDecimal invoiceamount;
	public BigDecimal expenseamount;
	public BigDecimal itemamount;
	public BigDecimal rate;
	public BigDecimal paidamount;
	public BigDecimal termdiscount;
	public BigDecimal returnamount;
	public Integer discounttaken;
	public BigDecimal owing;
	public String purchaseorderno;
	public Integer glperiod;
	public Integer glyear;
	public Integer posted;
	public Integer cashpurchase;
	public BigDecimal cashdiscount;
	public String cashdiscpc;
	public BigDecimal payment;
	public String paidfrom;
	public Date chequedate;
	public String chequeno;
	public Integer glhistid;
	public Integer warehouseid;
	public Integer templateid;
	public String apaccount;
	public Integer getfrompo;
	public String locked_by;
	public Date locked_time;
	public String sequenceno;
	public BigDecimal fiscalrate;
	public Integer shipvia;
	public Date shipdate;
	public String fob;
	public BigDecimal freight;
	public Integer tax1id;
	public Integer tax2id;
	public String tax1code;
	public String tax2code;
	public BigDecimal tax1rate;
	public BigDecimal tax2rate;
	public BigDecimal tax1amount;
	public BigDecimal tax2amount;
	public Integer freighttocost;
	public Integer bill;
	public String freightaccnt;
	public Integer shipvendid;
	public Integer freighttovendor;
	public BigDecimal owingdc;
	public String invtaxno1;
	public String invtaxno2;
	public Integer reconciled;
	public Integer reconcileid;
	public Integer inclusivetax;
	public Integer invoiceistaxable;
	public Integer invfrompr;
	public Date taxdate;
	public Integer reportedtax1;
	public Integer reportedtax2;
	public Date sspdate;
	public BigDecimal roundedtax1amount;
	public BigDecimal roundedtax2amount;
	public Integer transactionid;
	public Integer importedtransactionid;
	public Integer branchcodeid;
	public Integer recurringid;
	public BigDecimal taxreturnamount;
	public BigDecimal returnnotax;
	public BigDecimal invamtbeforetax;
	public BigDecimal taxdiscpayment;
	public BigDecimal discpayment;
	public BigDecimal taxpaidamount;
	public BigDecimal basetaxinvamt;
	public Integer isoutstanding;
	public Integer outstandingri;
	public Date maxchequedate;
	public Integer ratetype;
	public String dono;
	public BigDecimal tax1amountdp;
	public BigDecimal tax2amountdp;
	public BigDecimal roundedtax1dp;
	public BigDecimal roundedtax2dp;
	public Integer isdppo;
	public Integer expensesjournaldatetype;
	public Integer expensesofbillid;
	public String expensesofbillaccnt;
	public BigDecimal pph23amount;
	public Integer updatebybilltype;
	public String differenceunbilledaccnt;
	public BigDecimal dpamount;
	public BigDecimal dptax;
	public String description;

	public String vendorno;
	public String currencyname;
	public String warehousename;
	public String shipvendname;
	public String termsname;
	public String shipvianame;
	public String expensesofbillname;

	public List<EApItmDet> detail;
	public List<EApInvDet> expense;

	public static List<EApInv> readAll(Connection conn, int posted) {
		try {
			PreparedStatement ps = conn.prepareStatement(getSqlQuery());
			ps.setDate(1, Date.valueOf(AppData.dateCutOff));
			ps.setInt(2, posted);

			ResultSet rs = ps.executeQuery();

			List<EApInv> result = new ArrayList<EApInv>();
			while (rs.next()) {
				EApInv row = EApInv.read(rs);
				result.add(row);
			}

			for (EApInv row : result) {
				row.vendorno = DbUtil.getPersonNo(conn, row.vendorid);
				row.currencyname = DbUtil.getPersonCurrencyName(conn, row.vendorid);
				row.warehousename = DbUtil.getWarehouseName(conn, row.warehouseid);
				row.shipvendname = DbUtil.getPersonNo(conn, row.shipvendid);
				row.termsname = DbUtil.getTermsName(conn, row.termsid);
				row.shipvianame = DbUtil.getShipmentName(conn, row.shipvia);
				row.expensesofbillname = DbUtil.getPersonNo(conn, row.expensesofbillid);
				
				row.detail = EApItmDet.readAll(conn, row.apinvoiceid);
				row.expense = EApInvDet.readAll(conn, row.apinvoiceid);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static String getSqlQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT apinv.* FROM apinv ");
		sb.append("WHERE apinv.invoicedate > ? AND apinv.posted = ? ");
		sb.append("ORDER BY apinv.invoicedate, apinv.apinvoiceid ");

		return sb.toString();
	}

	public static EApInv read(ResultSet rs) throws SQLException {
		EApInv entity = new EApInv();
		entity.apinvoiceid = rs.getInt("apinvoiceid");
		entity.vendorid = rs.getInt("vendorid");
		entity.invoiceno = rs.getString("invoiceno");
		entity.invoiceseqno = rs.getInt("invoiceseqno");
		entity.invoicedate = rs.getDate("invoicedate");
		entity.termsid = rs.getInt("termsid");
		entity.invoiceamount = rs.getBigDecimal("invoiceamount");
		entity.expenseamount = rs.getBigDecimal("expenseamount");
		entity.itemamount = rs.getBigDecimal("itemamount");
		entity.rate = rs.getBigDecimal("rate");
		entity.paidamount = rs.getBigDecimal("paidamount");
		entity.termdiscount = rs.getBigDecimal("termdiscount");
		entity.returnamount = rs.getBigDecimal("returnamount");
		entity.discounttaken = rs.getInt("discounttaken");
		entity.owing = rs.getBigDecimal("owing");
		entity.purchaseorderno = rs.getString("purchaseorderno");
		entity.glperiod = rs.getInt("glperiod");
		entity.glyear = rs.getInt("glyear");
		entity.posted = rs.getInt("posted");
		entity.cashpurchase = rs.getInt("cashpurchase");
		entity.cashdiscount = rs.getBigDecimal("cashdiscount");
		entity.cashdiscpc = rs.getString("cashdiscpc");
		entity.payment = rs.getBigDecimal("payment");
		entity.paidfrom = rs.getString("paidfrom");
		entity.chequedate = rs.getDate("chequedate");
		entity.chequeno = rs.getString("chequeno");
		entity.glhistid = rs.getInt("glhistid");
		entity.warehouseid = rs.getInt("warehouseid");
		entity.templateid = rs.getInt("templateid");
		entity.apaccount = rs.getString("apaccount");
		entity.getfrompo = rs.getInt("getfrompo");
		entity.locked_by = rs.getString("locked_by");
		entity.locked_time = rs.getDate("locked_time");
		entity.sequenceno = rs.getString("sequenceno");
		entity.fiscalrate = rs.getBigDecimal("fiscalrate");
		entity.shipvia = rs.getInt("shipvia");
		entity.shipdate = rs.getDate("shipdate");
		entity.fob = rs.getString("fob");
		entity.freight = rs.getBigDecimal("freight");
		entity.tax1id = rs.getInt("tax1id");
		entity.tax2id = rs.getInt("tax2id");
		entity.tax1code = rs.getString("tax1code");
		entity.tax2code = rs.getString("tax2code");
		entity.tax1rate = Util.avoidNull(rs.getBigDecimal("tax1rate"));
		entity.tax2rate = Util.avoidNull(rs.getBigDecimal("tax2rate"));
		entity.tax1amount = rs.getBigDecimal("tax1amount");
		entity.tax2amount = rs.getBigDecimal("tax2amount");
		entity.freighttocost = rs.getInt("freighttocost");
		entity.bill = rs.getInt("bill");
		entity.freightaccnt = rs.getString("freightaccnt");
		entity.shipvendid = rs.getInt("shipvendid");
		entity.freighttovendor = rs.getInt("freighttovendor");
		entity.owingdc = rs.getBigDecimal("owingdc");
		entity.invtaxno1 = rs.getString("invtaxno1");
		entity.invtaxno2 = rs.getString("invtaxno2");
		entity.reconciled = rs.getInt("reconciled");
		entity.reconcileid = rs.getInt("reconcileid");
		entity.inclusivetax = rs.getInt("inclusivetax");
		entity.invoiceistaxable = rs.getInt("invoiceistaxable");
		entity.invfrompr = rs.getInt("invfrompr");
		entity.taxdate = rs.getDate("taxdate");
		entity.reportedtax1 = rs.getInt("reportedtax1");
		entity.reportedtax2 = rs.getInt("reportedtax2");
		entity.sspdate = rs.getDate("sspdate");
		entity.roundedtax1amount = rs.getBigDecimal("roundedtax1amount");
		entity.roundedtax2amount = rs.getBigDecimal("roundedtax2amount");
		entity.transactionid = rs.getInt("transactionid");
		entity.importedtransactionid = rs.getInt("importedtransactionid");
		entity.branchcodeid = rs.getInt("branchcodeid");
		entity.recurringid = rs.getInt("recurringid");
		entity.taxreturnamount = rs.getBigDecimal("taxreturnamount");
		entity.returnnotax = rs.getBigDecimal("returnnotax");
		entity.invamtbeforetax = rs.getBigDecimal("invamtbeforetax");
		entity.taxdiscpayment = rs.getBigDecimal("taxdiscpayment");
		entity.discpayment = rs.getBigDecimal("discpayment");
		entity.taxpaidamount = rs.getBigDecimal("taxpaidamount");
		entity.basetaxinvamt = rs.getBigDecimal("basetaxinvamt");
		entity.isoutstanding = rs.getInt("isoutstanding");
		entity.outstandingri = rs.getInt("outstandingri");
		entity.maxchequedate = rs.getDate("maxchequedate");
		entity.ratetype = rs.getInt("ratetype");
		entity.dono = rs.getString("dono");
		entity.tax1amountdp = rs.getBigDecimal("tax1amountdp");
		entity.tax2amountdp = rs.getBigDecimal("tax2amountdp");
		entity.roundedtax1dp = rs.getBigDecimal("roundedtax1dp");
		entity.roundedtax2dp = rs.getBigDecimal("roundedtax2dp");
		entity.isdppo = rs.getInt("isdppo");
		entity.expensesjournaldatetype = rs.getInt("expensesjournaldatetype");
		entity.expensesofbillid = rs.getInt("expensesofbillid");
		entity.expensesofbillaccnt = rs.getString("expensesofbillaccnt");
		entity.pph23amount = rs.getBigDecimal("pph23amount");
		entity.updatebybilltype = rs.getInt("updatebybilltype");
		entity.differenceunbilledaccnt = rs.getString("differenceunbilledaccnt");
		entity.dpamount = rs.getBigDecimal("dpamount");
		entity.dptax = rs.getBigDecimal("dptax");
		entity.description = rs.getString("description");

		return entity;
	}
}
