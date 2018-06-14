package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EApInvDet {

	public Integer apinvoiceid;
	public Integer seq;
	public String glaccount;
	public BigDecimal glamount;
	public Integer jobid;
	public Integer deptid;
	public Integer projectid;
	public String description;
	public Integer alloctoitemcost;
	public Integer chargetovendor;
	

	public static List<EApInvDet> readAll(Connection conn, int masterid) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ApInvDet.* FROM ApInvDet ");
			sql.append("WHERE ApInvDet.apinvoiceid = ? ");
			sql.append("ORDER BY ApInvDet.seq");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, masterid);

			ResultSet rs = ps.executeQuery();

			List<EApInvDet> result = new ArrayList<EApInvDet>();
			while (rs.next()) {
				result.add(EApInvDet.read(rs));
			}

//			for (EApInvDet row : result) {
//				row.warehousename = DbUtil.getWarehouseName(conn, row.warehouseid);
//				row.snhistory = ESnHistory.readAll(conn, row.itemhistid);
//			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static EApInvDet read(ResultSet rs) throws SQLException {
		EApInvDet entity = new EApInvDet();
		entity.apinvoiceid = rs.getInt("apinvoiceid");
		entity.seq = rs.getInt("seq");
		entity.glaccount = rs.getString("glaccount");
		entity.glamount = rs.getBigDecimal("glamount");
		entity.jobid = rs.getInt("jobid");
		entity.deptid = rs.getInt("deptid");
		entity.projectid = rs.getInt("projectid");
		entity.description = rs.getString("description");
		entity.alloctoitemcost = rs.getInt("alloctoitemcost");
		entity.chargetovendor = rs.getInt("chargetovendor");

		return entity;
	}
}
