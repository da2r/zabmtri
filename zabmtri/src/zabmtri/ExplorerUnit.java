package zabmtri;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExplorerUnit {

	// private static Connection conn =
	// DbUtil.createConnection("/Users/herman/Documents/File/temp/ABHIMATA15NEW_V5.GDB");
	// private static Connection conn =
	// DbUtil.createConnection("/Users/herman/Documents/File/temp/AMA2015_V5
	// TRI.GDB");

	private static Connection conn = DbUtil.createConnection("/Users/herman/Documents/File/temp/MITRASAMAYA15_V5.GDB");
//	private static Connection conn = DbUtil.createConnection("/Users/herman/Documents/File/temp/MS 2015_V5 TRI.GDB");

//	 private static Connection conn = DbUtil.createConnection("/Users/herman/git/zabmtri/zabmtri/sample/Sample.GDB");
	
//	private static Connection conn = DbUtil.createConnection("192.168.1.77/3051:D:\\share\\TRIAL2.GDB");
//	private static Connection conn = DbUtil.createConnection("192.168.1.77/3051:C:\\Program Files\\CPSSoft\\ACCURATE5 Standard\\Sample\\Sample.GDB");
	private static List<Integer> fmt = new ArrayList<Integer>();

	public static void main(String[] args) throws Exception {
		
		System.out.println(Paths.get("").toAbsolutePath().toString());

		StringBuilder sql = new StringBuilder();
		// sql.append("select * from apinvdet");
//		 sql.append(showTableSQL());
		// sql.append(showSprocSQL());
		// sql.append("select * from apinv where posted = 0");
		// sql.append("select * from apinvchq");
//		sql.append("select * from SALESMAN");
//		 sql.append("select * from itemadj order by ADJDATE");
//		 sql.append("select * from persondata where id = 1579");
//		sql.append("select CHRRESERVED1 from persondata");
//		sql.append("select * from item");
//		sql.append("select count(*) from snhistory");
//		 sql.append("Select * from WTRAN");
		// sql.append("select * from arrefund");
//		sql.append("select * from tax");
		sql.append("select * from serialnumbers");
		
//		sql.append("select * from DEFACCNT order by 1");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());

		ResultSet rs = ps.executeQuery();
		
		printAll(rs);
//		 printFirst(rs);

//		 System.out.println(conn.createStatement().executeUpdate("update users set userpassword = 'F225CF7D8A68182C5032CE392ADC3233' where userid = 0"));

		// System.out.println(conn.createStatement().executeUpdate("insert into
		// serialnumbers(serialnumber, itemno, expireddate, sntype) values
		// ('xxx', '1001', '2018-06-19', 1)"));

	}

	public static void printFirst(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int count = meta.getColumnCount();
		rs.next();
		for (int i = 0; i < count; i++) {
			String label = meta.getColumnLabel(i + 1);
			String value = rs.getString(i + 1);

			System.out.println(label + " = " + value);
		}
	}

	public static void printAll(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int count = meta.getColumnCount();
		for (int i = 0; i < count; i++) {
			String label = meta.getColumnLabel(i + 1);
			
			// Skip
//			if (label.contains("RESERVED") == false) {
//				setFmt(0);
//				continue;
//			}
			
			setFmt(label.length() + 20);
			print(label, i);
		}
		newLine();

		while (rs.next()) {
			for (int i = 0; i < count; i++) {
				print(rs.getString(i + 1), i);
			}
			newLine();
		}
	}

	public static String showTableSQL() {
		// return "SELECT a.RDB$RELATION_NAME FROM RDB$RELATIONS a WHERE
		// RDB$SYSTEM_FLAG = 0 AND RDB$RELATION_TYPE = 0";
		return "SELECT a.RDB$RELATION_NAME FROM RDB$RELATIONS a";
	}

	public static String showSprocSQL() {
		return "SELECT RDB$PROCEDURE_NAME FROM rdb$procedures";
	}

	private static void newLine() {
		System.out.println("");
	}

	private static void setFmt(int len) {
		if (len > 0 && len < 8) {
			len = 8;
		}
		fmt.add(len);
	}

	private static void print(String msg, int idx) {
		Integer len = fmt.get(idx);
		if (len == 0) {
			return;
		}
		
		System.out.print(String.format("%" + len + "s", msg));
	}
}
