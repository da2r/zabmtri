package zabmtri;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExplorerUnit {

	private static Connection conn = DbUtil.createConnection("/Users/herman/git/zabmtri/zabmtri/sample/ABM_V5.GDB");
	private static List<Integer> fmt = new ArrayList<Integer>();

	public static void main(String[] args) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from apinvdet");
//		 sql.append(showTableSQL());
		 
//		 APCHEQ                         
//		 APDPDET                        
//		 APINV                          
//		 APINVCHQ                       
//		 APINVCHQ_DISC                  
//		 APINVDET                       
//		 APITMDET                       

		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();

		 printAll(rs);
//		printFirst(rs);

	}

	public static void printFirst(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int count = meta.getColumnCount();
		rs.next();
		for (int i = 0; i < count; i++) {
			String label = meta.getColumnName(i + 1);
			String value = rs.getString(i + 1);

			System.out.println(label + " = " + value);
		}
	}

	public static void printAll(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int count = meta.getColumnCount();
		for (int i = 0; i < count; i++) {
			String label = meta.getColumnName(i + 1);
			setFmt(label.length() + 2);
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
		return "SELECT a.RDB$RELATION_NAME FROM RDB$RELATIONS a WHERE RDB$SYSTEM_FLAG = 0 AND RDB$RELATION_TYPE = 0";
	}

	private static void newLine() {
		System.out.println("");
	}

	private static void setFmt(int len) {
		if (len < 8) {
			len = 8;
		}
		fmt.add(len);
	}

	private static void print(String msg, int idx) {
		Integer len = fmt.get(idx);
		System.out.print(String.format("%" + (len + 4) + "s", msg));
	}
}
