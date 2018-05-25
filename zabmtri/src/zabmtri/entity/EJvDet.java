package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EJvDet {
	public Integer jvid;
	public Integer seq;
	public String glaccount;
	public BigDecimal glamount;
	public Integer subsidiary;
	public String description;
	public BigDecimal rate;
	public BigDecimal primeamount;
	public Integer deptid;
	public Integer projectid;
	public Date txdate;
	public Integer reconcileid;
	public Integer posted;
	public Integer fixassetid;

	public static List<EJvDet> readAll(Connection conn, int jvid) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT jvdet.* FROM jvdet ");
			sql.append("WHERE jvdet.jvid = ? ");
			sql.append("ORDER BY jvdet.seq");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, jvid);

			ResultSet rs = ps.executeQuery();

			List<EJvDet> result = new ArrayList<EJvDet>();
			while (rs.next()) {
				result.add(EJvDet.read(rs));
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static EJvDet read(ResultSet rs) throws SQLException {
		EJvDet entity = new EJvDet();
		entity.jvid = rs.getInt("jvid");
		entity.seq = rs.getInt("seq");
		entity.glaccount = rs.getString("glaccount");
		entity.glamount = rs.getBigDecimal("glamount");
		entity.subsidiary = rs.getInt("subsidiary");
		entity.description = rs.getString("description");
		entity.rate = rs.getBigDecimal("rate");
		entity.primeamount = rs.getBigDecimal("primeamount");
		entity.deptid = rs.getInt("deptid");
		entity.projectid = rs.getInt("projectid");
		entity.txdate = rs.getDate("txdate");
		entity.reconcileid = rs.getInt("reconcileid");
		entity.posted = rs.getInt("posted");
		entity.fixassetid = rs.getInt("fixassetid");

		return entity;
	}
}
