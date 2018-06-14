package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ESnHistory {
	public String serialnumber;
	public Date expireddate;
	public BigDecimal quantity;
	public Integer snsign;
	
	public static List<ESnHistory> readAll(Connection conn, int itemHistId) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT serialnumbers.serialnumber, serialnumbers.expireddate, snhistory.* FROM snhistory ");
			sql.append("LEFT JOIN serialnumbers ON snhistory.snid = serialnumbers.snid ");
			sql.append("WHERE snhistory.itemhistid = ? ");
			sql.append("ORDER BY serialnumbers.serialnumber ");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, itemHistId);

			ResultSet rs = ps.executeQuery();

			List<ESnHistory> result = new ArrayList<ESnHistory>();
			while (rs.next()) {
				ESnHistory row = new ESnHistory();
				row.serialnumber = rs.getString("serialnumber");
				row.expireddate = rs.getDate("expireddate");
				row.snsign = rs.getInt("snsign");
				row.quantity = rs.getBigDecimal("quantity");
				
				result.add(row);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

}
