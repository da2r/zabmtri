package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ECustomer {
	public Integer id;
	public String personno;
	public Integer persontype;
	public BigDecimal balance;
	public String name;
	public String addressline1;
	public String addressline2;
	public String city;
	public String stateprov;
	public String zipcode;
	public String country;
	public String contact;
	public String phone;
	public String fax;
	public String email;
	public String webpage;
	public Integer suspended;
	public String billtono;
	public Integer tax1id;
	public Integer tax2id;
	public Integer billtoonly;
	public String tax1exemptionno;
	public String tax2exemptionno;
	public Integer currencyid;
	public BigDecimal creditlimit;
	public Integer termsid;
	public Integer printstatement;
	public Integer allowbackorders;
	public Integer salesmanid;
	public Integer customertypeid;
	public String personmessage;
	public String defaultinvdescription;
	public String notes;
	public Integer pricelevel;
	public String defaultdisc;
	public Integer taxtype;
	public String taxaddress1;
	public String taxaddress2;
	public Integer transactionid;
	public Integer importedtransactionid;
	public Integer branchcodeid;
	public String chrreserved1;
	public String chrreserved2;
	public String chrreserved3;
	public String chrreserved4;
	public String chrreserved5;
	public String chrreserved6;
	public String chrreserved7;
	public String chrreserved8;
	public String chrreserved9;
	public String chrreserved10;
	public BigDecimal currreserved1;
	public BigDecimal currreserved2;
	public BigDecimal currreserved3;
	public Date datereserved1;
	public Date datereserved2;
	public Integer creditlimitdays;
	public Integer defaultinclusivetax;

	
	public static List<ECustomer> readAll(ResultSet rs) throws SQLException {
		List<ECustomer> result = new ArrayList<ECustomer>();
		while (rs.next()) {
			result.add(ECustomer.read(rs));
		}
		
		return result;
	}

	public static ECustomer read(ResultSet rs) throws SQLException {
		ECustomer entity = new ECustomer();
		entity.id = rs.getInt("id");
		entity.personno = rs.getString("personno");
		entity.persontype = rs.getInt("persontype");
		entity.balance = rs.getBigDecimal("balance");
		entity.name = rs.getString("name");
		entity.addressline1 = rs.getString("addressline1");
		entity.addressline2 = rs.getString("addressline2");
		entity.city = rs.getString("city");
		entity.stateprov = rs.getString("stateprov");
		entity.zipcode = rs.getString("zipcode");
		entity.country = rs.getString("country");
		entity.contact = rs.getString("contact");
		entity.phone = rs.getString("phone");
		entity.fax = rs.getString("fax");
		entity.email = rs.getString("email");
		entity.webpage = rs.getString("webpage");
		entity.suspended = rs.getInt("suspended");
		entity.billtono = rs.getString("billtono");
		entity.tax1id = rs.getInt("tax1id");
		entity.tax2id = rs.getInt("tax2id");
		entity.billtoonly = rs.getInt("billtoonly");
		entity.tax1exemptionno = rs.getString("tax1exemptionno");
		entity.tax2exemptionno = rs.getString("tax2exemptionno");
		entity.currencyid = rs.getInt("currencyid");
		entity.creditlimit = rs.getBigDecimal("creditlimit");
		entity.termsid = rs.getInt("termsid");
		entity.printstatement = rs.getInt("printstatement");
		entity.allowbackorders = rs.getInt("allowbackorders");
		entity.salesmanid = rs.getInt("salesmanid");
		entity.customertypeid = rs.getInt("customertypeid");
		entity.personmessage = rs.getString("personmessage");
		entity.defaultinvdescription = rs.getString("defaultinvdescription");
		entity.notes = rs.getString("notes");
		entity.pricelevel = rs.getInt("pricelevel");
		entity.defaultdisc = rs.getString("defaultdisc");
		entity.taxtype = rs.getInt("taxtype");
		entity.taxaddress1 = rs.getString("taxaddress1");
		entity.taxaddress2 = rs.getString("taxaddress2");
		entity.transactionid = rs.getInt("transactionid");
		entity.importedtransactionid = rs.getInt("importedtransactionid");
		entity.branchcodeid = rs.getInt("branchcodeid");
		entity.chrreserved1 = rs.getString("chrreserved1");
		entity.chrreserved2 = rs.getString("chrreserved2");
		entity.chrreserved3 = rs.getString("chrreserved3");
		entity.chrreserved4 = rs.getString("chrreserved4");
		entity.chrreserved5 = rs.getString("chrreserved5");
		entity.chrreserved6 = rs.getString("chrreserved6");
		entity.chrreserved7 = rs.getString("chrreserved7");
		entity.chrreserved8 = rs.getString("chrreserved8");
		entity.chrreserved9 = rs.getString("chrreserved9");
		entity.chrreserved10 = rs.getString("chrreserved10");
		entity.currreserved1 = rs.getBigDecimal("currreserved1");
		entity.currreserved2 = rs.getBigDecimal("currreserved2");
		entity.currreserved3 = rs.getBigDecimal("currreserved3");
		entity.datereserved1 = rs.getDate("datereserved1");
		entity.datereserved2 = rs.getDate("datereserved2");
		entity.creditlimitdays = rs.getInt("creditlimitdays");
		entity.defaultinclusivetax = rs.getInt("defaultinclusivetax");

		return entity;
	}
}
