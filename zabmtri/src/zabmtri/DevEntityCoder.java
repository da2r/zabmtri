package zabmtri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DevEntityCoder {
	public static void main(String[] args) throws SQLException {

		String path = "jdbc:firebirdsql:192.168.1.202/3051:C:\\Program Files\\CPSSoft\\ACCURATE4 deluxe 1423\\SAMPLE\\SAMPLE.GDB";
		Connection conn = DriverManager.getConnection(path, "guest", "guest");

		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery("SELECT First 1 * FROM persondata WHERE persontype = 0 ");

		ResultSetMetaData metadata = rs.getMetaData();
		int count = metadata.getColumnCount();
		for (int i = 1; i <= count; i++) {
			String name = metadata.getColumnName(i).toLowerCase();
			String className = metadata.getColumnClassName(i);

//			System.out.println("public " + getSimpleClassName(className) + " " + name + ";");
			System.out.println(
					String.format("entity.%s = rs.%s(\"%s\");", name, getRsGetter(className), name));
		}
	}

	public static String getSimpleClassName(String value) {
		if (value.contains("String")) {
			return "String";
		}
		if (value.contains("BigDecimal")) {
			return "BigDecimal";
		}
		if (value.contains("Integer")) {
			return "Integer";
		}
		if (value.contains("Date")) {
			return "Date";
		}
		return value;
	}

	public static String getRsGetter(String value) {
		if (value.contains("String")) {
			return "getString";
		}
		if (value.contains("BigDecimal")) {
			return "getBigDecimal";
		}
		if (value.contains("Integer")) {
			return "getInt";
		}
		if (value.contains("Date")) {
			return "getDate";
		}
		return "getObject";
	}
}
