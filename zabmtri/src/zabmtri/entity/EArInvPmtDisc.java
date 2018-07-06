package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EArInvPmtDisc {

	public Integer seq;
	public BigDecimal discount;
	public String discaccount;

	public static List<EArInvPmtDisc> readAll(Connection conn, int invpmtid) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM arinvpmt_disc ");
			sql.append("WHERE invpmtid = ? ");
			sql.append("ORDER BY seq");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, invpmtid);

			ResultSet rs = ps.executeQuery();

			List<EArInvPmtDisc> result = new ArrayList<EArInvPmtDisc>();
			while (rs.next()) {
				EArInvPmtDisc row = new EArInvPmtDisc();
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
