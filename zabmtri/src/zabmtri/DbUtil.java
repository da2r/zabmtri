package zabmtri;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

	public static Connection createConnection(String path) {
		try {
			System.out.println("Connecting ... ");

			if (path == null || path.isEmpty()) {
				throw new RuntimeException("File belum dipilih");
			}

			Properties props = new Properties();
			props.setProperty("user", "cps#1");
			props.setProperty("password", "cps#2001");
			props.setProperty("encoding", "NONE");

			return DriverManager.getConnection("jdbc:firebirdsql:" + path, props);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getNativeBranchCode(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT branchcode FROM branchcodes where isnative = 1");
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getString(1);
		}

		throw new RuntimeException("Cannot get native branch");
	}

	public static String getOBE(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("select GLACCOUNT from DEFACCNT where name = 'OPENING BALANCE EQUITY'");
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getString(1);
		} else {
			return null;
		}
	}

	public static String getPersonNo(Connection conn, Integer personid) throws SQLException {
		if (personid == null || personid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "personno", personid);
		if (cached != null) {
			return cached;
		}
		
		PreparedStatement ps = conn.prepareStatement("SELECT personno FROM persondata where id = ?");
		ps.setInt(1, personid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "personno", personid, result);
			return result;
			
		}

		throw new RuntimeException("Cannot get personno for id " + personid);
	}
	
	public static String getPersonName(Connection conn, Integer personid) throws SQLException {
		if (personid == null || personid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "personname", personid);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT name FROM persondata where id = ?");
		ps.setInt(1, personid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "personname", personid, result);
			return result;
		}

		throw new RuntimeException("Cannot get personname for id " + personid);
	}

	public static String getPersonCurrencyName(Connection conn, Integer personid) throws SQLException {
		if (personid == null || personid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "personcurrencyname", personid);
		if (cached != null) {
			return cached;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT currency.currencyname FROM persondata ");
		sb.append("LEFT JOIN currency ON persondata.currencyid = currency.currencyid ");
		sb.append("WHERE persondata.id = ? ");

		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setInt(1, personid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "personcurrencyname", personid, result);
			return result;
		}

		throw new RuntimeException("Cannot get personno for id " + personid);
	}

	public static Integer getCurrencyIdByName(Connection conn, String currencyname) throws SQLException {
		if (currencyname == null || currencyname.isEmpty()) {
			return null;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT currencyid FROM currency where currencyname = ?");
		ps.setString(1, currencyname);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		}

		return null;
	}
	
	public static String getCurrencyName(Connection conn, int currencyid) throws SQLException {
		String cached = DbCache.getString(conn, "currency", currencyid);
		if (cached != null) {
			return cached;
		}
		
		PreparedStatement ps = conn.prepareStatement("SELECT currencyname FROM currency where currencyid = ?");
		ps.setInt(1, currencyid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "currency", currencyid, result);
			return result;
		}

		throw new RuntimeException("Cannot get currency name for id " + currencyid);
	}

	public static BigDecimal getCurrencyRate(Connection conn, int currencyid) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT exchangerate FROM currency where currencyid = ?");
		ps.setInt(1, currencyid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getBigDecimal(1);
		}

		throw new RuntimeException("Cannot get currency rate for id " + currencyid);
	}

	public static String getArInvoiceNo(Connection conn, Integer arinvoiceid) throws SQLException {
		if (arinvoiceid == null || arinvoiceid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "arinvoiceno", arinvoiceid);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT invoiceno FROM arinv where arinvoiceid = ?");
		ps.setInt(1, arinvoiceid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "arinvoiceno", arinvoiceid, result);
			return result;
		}

		throw new RuntimeException("Cannot get arinv for arinvoiceid " + arinvoiceid);
	}

	public static String getApInvoiceNo(Connection conn, Integer apinvoiceid) throws SQLException {
		if (apinvoiceid == null || apinvoiceid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "apinvoiceno", apinvoiceid);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT invoiceno FROM apinv where apinvoiceid = ?");
		ps.setInt(1, apinvoiceid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "apinvoiceno", apinvoiceid, result);
			return result;
		}

		throw new RuntimeException("Cannot get apinv for apinvoiceid " + apinvoiceid);
	}

	public static String getWarehouseName(Connection conn, Integer warehouseid) throws SQLException {
		if (warehouseid == null) {
			// Warehouse with ID == 0 is valid
			return "";
		}
		
		String cached = DbCache.getString(conn, "warehouse", warehouseid);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT name FROM warehs where warehouseid = ?");
		ps.setInt(1, warehouseid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "warehouse", warehouseid, result);
			return result;
		}

		throw new RuntimeException("Cannot get warehs for warehouseid " + warehouseid);
	}
	
	public static String getTaxCode(Connection conn, Integer taxid) throws SQLException {
		if (taxid == null || taxid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "taxcode", taxid);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT taxcode FROM tax where taxid = ?");
		ps.setInt(1, taxid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "taxcode", taxid, result);
			return result;
		}

		throw new RuntimeException("Cannot get tax for taxid " + taxid);
	}
	
	public static String getTaxName(Connection conn, Integer taxid) throws SQLException {
		if (taxid == null || taxid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "tax", taxid);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT taxname FROM tax where taxid = ?");
		ps.setInt(1, taxid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "tax", taxid, result);
			
			return result;
		}

		throw new RuntimeException("Cannot get tax for taxid " + taxid);
	}
	
	public static String getTaxTypeInName(Connection conn, Integer id) throws SQLException {
		if (id == null || id.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "taxtypein", id);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT taxname FROM taxtype_in where id = ?");
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "taxtypein", id, result);
			return result;
		}

		throw new RuntimeException("Cannot get taxtype_in for id " + id);
	}
	
	public static String getTaxTypeOutName(Connection conn, Integer id) throws SQLException {
		if (id == null || id.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "taxtypeout", id);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT taxname FROM taxtype_out where id = ?");
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "taxtypeout", id, result);
			return result;
		}

		throw new RuntimeException("Cannot get taxtype_out for id " + id);
	}

	public static String getTermsName(Connection conn, Integer termid) throws SQLException {
		if (termid == null || termid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "term", termid);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT termname FROM termopmt where termid = ?");
		ps.setInt(1, termid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "term", termid, result);
			return result;
		}

		throw new RuntimeException("Cannot get termopmt for termid " + termid);
	}

	public static String getShipmentName(Connection conn, Integer shipid) throws SQLException {
		if (shipid == null || shipid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "shipment", shipid);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT name FROM shipment where shipid = ?");
		ps.setInt(1, shipid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "shipment", shipid, result);
			return result;
		}

		throw new RuntimeException("Cannot get shipment for shipid " + shipid);
	}
	
	public static String getSalesmanName(Connection conn, Integer salesmanid) throws SQLException {
		if (salesmanid == null || salesmanid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "salesman", salesmanid);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT salesmanname FROM salesman WHERE salesmanid = ?");
		ps.setInt(1, salesmanid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "salesman", salesmanid, result);
			return result;
		}

		throw new RuntimeException("Cannot get salesman name for salesmanid " + salesmanid);
	}
	
	public static String getCustomerTypeName(Connection conn, Integer customertypeid) throws SQLException {
		if (customertypeid == null || customertypeid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "customertype", customertypeid);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT typename FROM custtype WHERE customertypeid = ?");
		ps.setInt(1, customertypeid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "customertype", customertypeid, result);
			return result;
		}

		throw new RuntimeException("Cannot get custtype typename for customertypeid " + customertypeid);
	}
	
	public static String getItemCategoryName(Connection conn, Integer categoryid) throws SQLException {
		if (categoryid == null || categoryid.equals(0)) {
			return "";
		}
		
		String cached = DbCache.getString(conn, "itemcategory", categoryid);
		if (cached != null) {
			return cached;
		}

		PreparedStatement ps = conn.prepareStatement("SELECT name FROM itemcategory where categoryid = ?");
		ps.setInt(1, categoryid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String result = rs.getString(1);
			DbCache.put(conn, "itemcategory", categoryid, result);
			return result;
		}

		throw new RuntimeException("Cannot get item category name for categoryid " + categoryid);
	}
	
	public static BigDecimal getItemTotalCost(Connection conn, String itemno) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select TOTCOST from GET_COST_BY_COSTINGMETHOD(?, ?, 1)");

		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setString(1, itemno);
		ps.setDate(2, Date.valueOf(AppData.dateCutOff));

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getBigDecimal(1);
		}
		
		throw new RuntimeException("Cannot get item total cost for " + itemno);
	}

	public static void closeQuietly(Connection closeable) {
		if (closeable == null) {
			return;
		}

		try {
			closeable.close();
		} catch (Exception e) {
			// silent error;
			e.printStackTrace();
		}
	}


}
