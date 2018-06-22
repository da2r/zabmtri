package zabmtri.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EItemCategory {

	public Integer categoryid;
	public String name;

	public static List<EItemCategory> readAll(Connection conn) {
		try {
			List<EItemCategory> result = new ArrayList<EItemCategory>();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM itemcategory ORDER BY name ");

			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EItemCategory row = new EItemCategory();
				row.categoryid = rs.getInt(1);
				row.name = rs.getString(2);

				result.add(row);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
