package zabmtri.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EWarehs {
	public Integer warehouseid;
	public String name;
	public String description;
	public String address1;
	public String address2;
	public String address3;
	public String pic;
	public boolean suspended;

	public static List<EWarehs> readAll(Connection conn) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT warehs.* FROM warehs ");
			sql.append("ORDER BY warehs.name");

			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			List<EWarehs> result = new ArrayList<EWarehs>();
			while (rs.next()) {
				result.add(EWarehs.read(rs));
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static EWarehs read(ResultSet rs) throws SQLException {
		EWarehs entity = new EWarehs();
		entity.warehouseid = rs.getInt("warehouseid");
		entity.name = rs.getString("name");
		entity.description = rs.getString("description");
		entity.address1 = rs.getString("address1");
		entity.address2 = rs.getString("address2");
		entity.address3 = rs.getString("address3");
		entity.pic = rs.getString("pic");
		entity.suspended = rs.getInt("suspended") == 1;

		return entity;
	}

	public static int write(Connection conn, EWarehs entity) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("insert into WAREHS(NAME, DESCRIPTION, ADDRESS1, ADDRESS2, ADDRESS3, PIC, SUSPENDED) values(?, ?, ?, ?, ?, ?, ?)");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, entity.name);
			ps.setString(2, entity.description);
			ps.setString(3, entity.address1);
			ps.setString(4, entity.address2);
			ps.setString(5, entity.address3);
			ps.setString(6, entity.pic);
			ps.setInt(7, entity.suspended ? 1 : 0);

			return ps.executeUpdate();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	public static boolean isExists(Connection conn, String warehouseName) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT warehs.name FROM warehs ");
			sql.append("WHERE warehs.name = ?");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, warehouseName);

			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
