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

public class EArInv {
	public String purchaseorderno;
	public Integer arinvoiceid;
	public Integer customerid;
	public Integer salesmanid;
	public String invoiceno;
	public Integer warehouseid;
	public Date invoicedate;
	public BigDecimal rate;
	public BigDecimal invoiceamount;
	public BigDecimal paidamount;
	public BigDecimal termdiscount;
	public BigDecimal returnamount;
	public BigDecimal owing;
	public Integer termsid;
	public Integer glperiod;
	public Integer glyear;
	public Integer printed;
	public Integer posted;
	public Integer tax1id;
	public Integer tax2id;
	public String tax1code;
	public String tax2code;
	public BigDecimal tax1rate;
	public BigDecimal tax2rate;
	public Integer cashsales;
	public String depositto;
	public Date chequedate;
	public String chequeno;
	public Integer shipvia;
	public String fob;
	public Date shipdate;
	public Integer glhistid;
	public BigDecimal payment;
	public BigDecimal cashdiscount;
	public String cashdiscpc;
	public Integer templateid;
	public String araccount;
	public Integer getfromother;
	public Integer parentarinvid;
	public Integer deliveryorder;
	public Integer getfromso;
	public BigDecimal fiscalrate;
	public BigDecimal owingdc;
	public BigDecimal tax1amount;
	public BigDecimal tax2amount;
	public Integer inclusivetax;
	public Integer customeristaxable;
	public Integer getfromdo;
	public String taxformnumber;
	public BigDecimal freight;
	public String freightaccnt;
	public Integer reconciled;
	public Integer reconcileid;
	public Integer invfromsr;
	public Date taxdate;
	public Integer reportedtax1;
	public Integer reportedtax2;
	public BigDecimal roundedtax1amount;
	public BigDecimal roundedtax2amount;
	public Integer istaxpayment;
	public String taxformcode;
	public Integer transactionid;
	public Integer importedtransactionid;
	public String shipto1;
	public String shipto2;
	public String shipto3;
	public String shipto4;
	public String shipto5;
	public Integer branchcodeid;
	public Integer recurringid;
	public Integer getfromquote;
	public BigDecimal taxreturnamount;
	public BigDecimal returnnotax;
	public BigDecimal invamtbeforetax;
	public BigDecimal taxdiscpayment;
	public BigDecimal discpayment;
	public BigDecimal taxpaidamount;
	public BigDecimal basetaxinvamt;
	public Integer isoutstanding;
	public Integer outstandingdo;
	public Date maxchequedate;
	public Integer ratetype;
	public BigDecimal tax1amountdp;
	public BigDecimal tax2amountdp;
	public BigDecimal roundedtax1dp;
	public BigDecimal roundedtax2dp;
	public BigDecimal pph23amount;
	public Integer projectid;
	public Integer projectpmtseq;
	public BigDecimal cogsamount;
	public BigDecimal dpamount;
	public BigDecimal dptax;
	public BigDecimal projectamount;
	public String description;

	public String customerno;
	public String currencyname;
	public String warehousename;
	public String termsname;
	public String shipvianame;

	public List<EArInvDet> detail;

	public static List<EArInv> readAll(Connection conn) {
		try {
			PreparedStatement ps = conn.prepareStatement(getSqlQuery());
			ps.setDate(1, Date.valueOf(AppData.dateCutOff));

			ResultSet rs = ps.executeQuery();

			List<EArInv> result = new ArrayList<EArInv>();
			while (rs.next()) {
				EArInv row = EArInv.read(rs);
				result.add(row);
			}

			for (EArInv row : result) {
				row.customerno = DbUtil.getPersonNo(conn, row.customerid);
				row.currencyname = DbUtil.getPersonCurrencyName(conn, row.customerid);
				row.warehousename = DbUtil.getWarehouseName(conn, row.warehouseid);
				row.termsname = DbUtil.getTermsName(conn, row.termsid);
				row.shipvianame = DbUtil.getShipmentName(conn, row.shipvia);

				row.detail = EArInvDet.readAll(conn, row.arinvoiceid);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static String getSqlQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT arinv.* FROM arinv ");
		sb.append("WHERE arinv.invoicedate > ? ");
		sb.append("ORDER BY arinv.invoicedate, arinv.arinvoiceid ");

		return sb.toString();
	}

	public static EArInv read(ResultSet rs) throws SQLException {
		EArInv entity = new EArInv();
		entity.purchaseorderno = rs.getString("purchaseorderno");
		entity.arinvoiceid = rs.getInt("arinvoiceid");
		entity.customerid = rs.getInt("customerid");
		entity.salesmanid = rs.getInt("salesmanid");
		entity.invoiceno = rs.getString("invoiceno");
		entity.warehouseid = rs.getInt("warehouseid");
		entity.invoicedate = rs.getDate("invoicedate");
		entity.rate = rs.getBigDecimal("rate");
		entity.invoiceamount = rs.getBigDecimal("invoiceamount");
		entity.paidamount = rs.getBigDecimal("paidamount");
		entity.termdiscount = rs.getBigDecimal("termdiscount");
		entity.returnamount = rs.getBigDecimal("returnamount");
		entity.owing = rs.getBigDecimal("owing");
		entity.termsid = rs.getInt("termsid");
		entity.glperiod = rs.getInt("glperiod");
		entity.glyear = rs.getInt("glyear");
		entity.printed = rs.getInt("printed");
		entity.posted = rs.getInt("posted");
		entity.tax1id = rs.getInt("tax1id");
		entity.tax2id = rs.getInt("tax2id");
		entity.tax1code = rs.getString("tax1code");
		entity.tax2code = rs.getString("tax2code");
		entity.tax1rate = Util.avoidNull(rs.getBigDecimal("tax1rate"));
		entity.tax2rate = Util.avoidNull(rs.getBigDecimal("tax2rate"));
		entity.cashsales = rs.getInt("cashsales");
		entity.depositto = rs.getString("depositto");
		entity.chequedate = rs.getDate("chequedate");
		entity.chequeno = rs.getString("chequeno");
		entity.shipvia = rs.getInt("shipvia");
		entity.fob = rs.getString("fob");
		entity.shipdate = rs.getDate("shipdate");
		entity.glhistid = rs.getInt("glhistid");
		entity.payment = rs.getBigDecimal("payment");
		entity.cashdiscount = rs.getBigDecimal("cashdiscount");
		entity.cashdiscpc = rs.getString("cashdiscpc");
		entity.templateid = rs.getInt("templateid");
		entity.araccount = rs.getString("araccount");
		entity.getfromother = rs.getInt("getfromother");
		entity.parentarinvid = rs.getInt("parentarinvid");
		entity.deliveryorder = rs.getInt("deliveryorder");
		entity.getfromso = rs.getInt("getfromso");
		entity.fiscalrate = rs.getBigDecimal("fiscalrate");
		entity.owingdc = rs.getBigDecimal("owingdc");
		entity.tax1amount = rs.getBigDecimal("tax1amount");
		entity.tax2amount = rs.getBigDecimal("tax2amount");
		entity.inclusivetax = rs.getInt("inclusivetax");
		entity.customeristaxable = rs.getInt("customeristaxable");
		entity.getfromdo = rs.getInt("getfromdo");
		entity.taxformnumber = rs.getString("taxformnumber");
		entity.freight = rs.getBigDecimal("freight");
		entity.freightaccnt = rs.getString("freightaccnt");
		entity.reconciled = rs.getInt("reconciled");
		entity.reconcileid = rs.getInt("reconcileid");
		entity.invfromsr = rs.getInt("invfromsr");
		entity.taxdate = rs.getDate("taxdate");
		entity.reportedtax1 = rs.getInt("reportedtax1");
		entity.reportedtax2 = rs.getInt("reportedtax2");
		entity.roundedtax1amount = rs.getBigDecimal("roundedtax1amount");
		entity.roundedtax2amount = rs.getBigDecimal("roundedtax2amount");
		entity.istaxpayment = rs.getInt("istaxpayment");
		entity.taxformcode = rs.getString("taxformcode");
		entity.transactionid = rs.getInt("transactionid");
		entity.importedtransactionid = rs.getInt("importedtransactionid");
		entity.shipto1 = rs.getString("shipto1");
		entity.shipto2 = rs.getString("shipto2");
		entity.shipto3 = rs.getString("shipto3");
		entity.shipto4 = rs.getString("shipto4");
		entity.shipto5 = rs.getString("shipto5");
		entity.branchcodeid = rs.getInt("branchcodeid");
		entity.recurringid = rs.getInt("recurringid");
		entity.getfromquote = rs.getInt("getfromquote");
		entity.taxreturnamount = rs.getBigDecimal("taxreturnamount");
		entity.returnnotax = rs.getBigDecimal("returnnotax");
		entity.invamtbeforetax = rs.getBigDecimal("invamtbeforetax");
		entity.taxdiscpayment = rs.getBigDecimal("taxdiscpayment");
		entity.discpayment = rs.getBigDecimal("discpayment");
		entity.taxpaidamount = rs.getBigDecimal("taxpaidamount");
		entity.basetaxinvamt = rs.getBigDecimal("basetaxinvamt");
		entity.isoutstanding = rs.getInt("isoutstanding");
		entity.outstandingdo = rs.getInt("outstandingdo");
		entity.maxchequedate = rs.getDate("maxchequedate");
		entity.ratetype = rs.getInt("ratetype");
		entity.tax1amountdp = rs.getBigDecimal("tax1amountdp");
		entity.tax2amountdp = rs.getBigDecimal("tax2amountdp");
		entity.roundedtax1dp = rs.getBigDecimal("roundedtax1dp");
		entity.roundedtax2dp = rs.getBigDecimal("roundedtax2dp");
		entity.pph23amount = rs.getBigDecimal("pph23amount");
		entity.projectid = rs.getInt("projectid");
		entity.projectpmtseq = rs.getInt("projectpmtseq");
		entity.cogsamount = rs.getBigDecimal("cogsamount");
		entity.dpamount = rs.getBigDecimal("dpamount");
		entity.dptax = rs.getBigDecimal("dptax");
		entity.projectamount = rs.getBigDecimal("projectamount");
		entity.description = rs.getString("description");

		return entity;
	}
}
