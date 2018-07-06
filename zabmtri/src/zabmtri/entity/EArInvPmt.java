package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zabmtri.DbUtil;

public class EArInvPmt {

	public Integer arinvoiceid;
	public Integer paymentid;
	public Integer seq;
	public BigDecimal paymentamount;
	public BigDecimal disctakenamount;
	public String discaccount;
	public Integer invpmtid;
	public Integer taxpph23;
	public BigDecimal pph23amount;
	public BigDecimal pph23rate;
	public String pph23number;
	public BigDecimal pph23fiscalrate;

	public String arinvoiceno;
	
	public List<EArInvPmtDisc> writeoff;

	public static List<EArInvPmt> readAll(Connection conn, int paymentid) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM arinvpmt ");
			sql.append("WHERE paymentid = ? ");
			sql.append("ORDER BY seq");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, paymentid);

			ResultSet rs = ps.executeQuery();

			List<EArInvPmt> result = new ArrayList<EArInvPmt>();
			while (rs.next()) {
				result.add(EArInvPmt.read(rs));
			}

			for (EArInvPmt row : result) {
				row.arinvoiceno = DbUtil.getArInvoiceNo(conn, row.arinvoiceid);
				row.writeoff = EArInvPmtDisc.readAll(conn, row.invpmtid);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static EArInvPmt read(ResultSet rs) throws SQLException {
		EArInvPmt entity = new EArInvPmt();
		entity.arinvoiceid = rs.getInt("arinvoiceid");
		entity.paymentid = rs.getInt("paymentid");
		entity.seq = rs.getInt("seq");
		entity.paymentamount = rs.getBigDecimal("paymentamount");
		entity.disctakenamount = rs.getBigDecimal("disctakenamount");
		entity.discaccount = rs.getString("discaccount");
		entity.invpmtid = rs.getInt("invpmtid");
		entity.taxpph23 = rs.getInt("taxpph23");
		entity.pph23amount = rs.getBigDecimal("pph23amount");
		entity.pph23rate = rs.getBigDecimal("pph23rate");
		entity.pph23number = rs.getString("pph23number");
		entity.pph23fiscalrate = rs.getBigDecimal("pph23fiscalrate");

		return entity;
	}
}
