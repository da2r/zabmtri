package zabmtri;

import java.sql.Connection;
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
			props.setProperty("user", "guest");
			props.setProperty("password", "guest");
			props.setProperty("encoding", "NONE");

			return DriverManager.getConnection("jdbc:firebirdsql:" + path, props);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getPersonNo(Connection conn, Integer personid) throws SQLException {
		if (personid == null || personid.equals(0)) {
			return "";
		}

		PreparedStatement ps = conn.prepareStatement("SELECT personno FROM persondata where id = ?");
		ps.setInt(1, personid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getString(1);
		}

		throw new RuntimeException("Cannot get personno for id " + personid);
	}

	public static String getPersonCurrencyName(Connection conn, Integer personid) throws SQLException {
		if (personid == null || personid.equals(0)) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT currency.currencyname FROM persondata ");
		sb.append("LEFT JOIN currency ON persondata.currencyid = currency.currencyid ");
		sb.append("WHERE persondata.id = ? ");

		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setInt(1, personid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getString(1);
		}

		throw new RuntimeException("Cannot get personno for id " + personid);
	}

	public static String getArInvoiceNo(Connection conn, Integer arinvoiceid) throws SQLException {
		if (arinvoiceid == null || arinvoiceid.equals(0)) {
			return "";
		}

		PreparedStatement ps = conn.prepareStatement("SELECT invoiceno FROM arinv where arinvoiceid = ?");
		ps.setInt(1, arinvoiceid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getString(1);
		}

		throw new RuntimeException("Cannot get arinv for arinvoiceid " + arinvoiceid);
	}

	public static String getApInvoiceNo(Connection conn, Integer apinvoiceid) throws SQLException {
		if (apinvoiceid == null || apinvoiceid.equals(0)) {
			return "";
		}

		PreparedStatement ps = conn.prepareStatement("SELECT invoiceno FROM apinv where apinvoiceid = ?");
		ps.setInt(1, apinvoiceid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getString(1);
		}

		throw new RuntimeException("Cannot get apinv for apinvoiceid " + apinvoiceid);
	}

	public static String getWarehouseName(Connection conn, Integer warehouseid) throws SQLException {
		if (warehouseid == null) {
			// Warehouse with ID == 0 is valid
			return "";
		}

		PreparedStatement ps = conn.prepareStatement("SELECT name FROM warehs where warehouseid = ?");
		ps.setInt(1, warehouseid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getString(1);
		}

		throw new RuntimeException("Cannot get warehs for warehouseid " + warehouseid);
	}

	public static String getTermsName(Connection conn, Integer termid) throws SQLException {
		if (termid == null || termid.equals(0)) {
			return "";
		}

		PreparedStatement ps = conn.prepareStatement("SELECT termname FROM termopmt where termid = ?");
		ps.setInt(1, termid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getString(1);
		}

		throw new RuntimeException("Cannot get termopmt for termid " + termid);
	}

	public static String getShipmentName(Connection conn, Integer shipid) throws SQLException {
		if (shipid == null || shipid.equals(0)) {
			return "";
		}

		PreparedStatement ps = conn.prepareStatement("SELECT name FROM shipment where shipid = ?");
		ps.setInt(1, shipid);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getString(1);
		}

		throw new RuntimeException("Cannot get shipment for shipid " + shipid);
	}

}
