package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EItemSn {

	public String itemno;
	public String serialnumber;
	public Date expireddate;
	public BigDecimal quantity;

	public static List<EItemSn> readAll(Connection conn, LocalDate asOf) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select ITEMHIST.ITEMNO, SERIALNUMBERS.SERIALNUMBER, SERIALNUMBERS.EXPIREDDATE, SUM(SNHISTORY.QUANTITY * SNHISTORY.SNSIGN) as QUANTITY from SNHISTORY ");
			sql.append("inner join ITEMHIST on SNHISTORY.ITEMHISTID = ITEMHIST.ITEMHISTID ");
			sql.append("inner join SERIALNUMBERS on SNHISTORY.SNID = SERIALNUMBERS.SNID ");
			sql.append("where ITEMHIST.TXDATE <= ? ");
			sql.append("group by  ITEMHIST.ITEMNO, SERIALNUMBERS.SERIALNUMBER, SERIALNUMBERS.EXPIREDDATE ");
			sql.append("having SUM(SNHISTORY.QUANTITY * SNHISTORY.SNSIGN) > 0 ");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setDate(1, Date.valueOf(asOf));

			ResultSet rs = ps.executeQuery();

			List<EItemSn> result = new ArrayList<EItemSn>();
			while (rs.next()) {
				EItemSn row = new EItemSn();
				row.itemno = rs.getString("itemno");
				row.serialnumber = rs.getString("serialnumber");
				row.expireddate = rs.getDate("expireddate");
				row.quantity = rs.getBigDecimal("quantity");
				result.add(row);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static EItemSn clone(EItemSn arg) {
		EItemSn result = new EItemSn();
		result.itemno = arg.itemno;
		result.serialnumber = arg.serialnumber;
		result.expireddate = arg.expireddate;
		result.quantity = arg.quantity;

		return result;
	}

}
