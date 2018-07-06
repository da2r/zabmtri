package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zabmtri.DbUtil;

public class EApInvChq {

	public Integer apinvoiceid;
	public Integer chequeid;
	public Integer seq;
	public BigDecimal paymentamount;
	public BigDecimal discount;
	public String discaccount;
	public Integer deptid;
	public Integer projectid;
	public Integer invchqid;
	public Integer taxpph23;
	public BigDecimal pph23amount;
	public BigDecimal pph23rate;
	public String pph23number;
	public BigDecimal pph23fiscalrate;

	public String apinvoiceno;
	
	public List<EApInvChqDisc> writeoff;

	public static List<EApInvChq> readAll(Connection conn, int chequeid) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM ApInvChq ");
			sql.append("WHERE chequeid = ? ");
			sql.append("ORDER BY seq");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, chequeid);

			ResultSet rs = ps.executeQuery();

			List<EApInvChq> result = new ArrayList<EApInvChq>();
			while (rs.next()) {
				result.add(EApInvChq.read(rs));
			}

			for (EApInvChq row : result) {
				row.apinvoiceno = DbUtil.getApInvoiceNo(conn, row.apinvoiceid);
				row.writeoff = EApInvChqDisc.readAll(conn, row.invchqid);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static EApInvChq read(ResultSet rs) throws SQLException {
		EApInvChq entity = new EApInvChq();
		entity.apinvoiceid = rs.getInt("apinvoiceid");
		entity.chequeid = rs.getInt("chequeid");
		entity.seq = rs.getInt("seq");
		entity.paymentamount = rs.getBigDecimal("paymentamount");
		entity.discount = rs.getBigDecimal("discount");
		entity.discaccount = rs.getString("discaccount");
		entity.deptid = rs.getInt("deptid");
		entity.projectid = rs.getInt("projectid");
		entity.invchqid = rs.getInt("invchqid");
		entity.taxpph23 = rs.getInt("taxpph23");
		entity.pph23amount = rs.getBigDecimal("pph23amount");
		entity.pph23rate = rs.getBigDecimal("pph23rate");
		entity.pph23number = rs.getString("pph23number");
		entity.pph23fiscalrate = rs.getBigDecimal("pph23fiscalrate");

		return entity;
	}
}
