package zabmtri;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class DbCache {

	private static Map<Connection, Map<String, Map<Integer, Object>>> map = new HashMap<Connection, Map<String, Map<Integer, Object>>>();

	public static void put(Connection conn, String group, Integer id, Object value) {
		Map<String, Map<Integer, Object>> a = map.get(conn);
		if (a == null) {
			a = new HashMap<String, Map<Integer, Object>>();
			map.put(conn, a);
		}

		Map<Integer, Object> b = a.get(group);
		if (b == null) {
			b = new HashMap<Integer, Object>();
			a.put(group, b);
		}

		b.put(id, value);
	}

	public static String getString(Connection conn, String group, Integer id) {
		Map<String, Map<Integer, Object>> a = map.get(conn);
		if (a == null) {
			return null;
		}

		Map<Integer, Object> b = a.get(group);
		if (b == null) {
			return null;
		}
		
		return (String) b.get(id);
	}
}
