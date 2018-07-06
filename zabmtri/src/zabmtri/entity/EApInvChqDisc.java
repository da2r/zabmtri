package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EApInvChqDisc {

	public Integer seq;
	public BigDecimal discount;
	public String discaccount;

	public static List<EApInvChqDisc> readAll(Connection conn, int invchqid) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM apinvchq_disc ");
			sql.append("WHERE invchqid = ? ");
			sql.append("ORDER BY seq");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, invchqid);

			ResultSet rs = ps.executeQuery();

			List<EApInvChqDisc> result = new ArrayList<EApInvChqDisc>();
			while (rs.next()) {
				EApInvChqDisc row = new EApInvChqDisc();
				row.seq = rs.getInt("seq");
				row.discaccount = rs.getString("discaccount");
				row.discount = rs.getBigDecimal("discount");
				result.add(row);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

}
