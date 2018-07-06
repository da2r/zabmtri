package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EOwingPi {

	public Integer vendorid;
	public BigDecimal owing;
	public String invoiceno;
	public Date invoicedate;

	public static List<EOwingPi> readAll(Connection conn, LocalDate asOf) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select APINV.VENDORID, OWING_PI.OWING, APINV.INVOICENO, APINV.INVOICEDATE ");
			sql.append("from APINV left join OWING_PI(?,  APINV.APINVOICEID) ON OWING_PI.APINVOICEID=APINV.APINVOICEID ");
			sql.append("where (NOT OWING_PI.OWING IS NULL AND NOT OWING_PI.OWINGDC IS NULL) AND (OWING_PI.OWING<>0 OR OWING_PI.OWINGDC<>0) AND APINV.INVOICEDATE<=?");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setDate(1, Date.valueOf(asOf));
			ps.setDate(2, Date.valueOf(asOf));

			ResultSet rs = ps.executeQuery();

			List<EOwingPi> result = new ArrayList<EOwingPi>();
			while (rs.next()) {
				EOwingPi row = new EOwingPi();
				row.vendorid = rs.getInt(1);
				row.owing = rs.getBigDecimal(2);
				row.invoiceno = rs.getString(3);
				row.invoicedate = rs.getDate(4);
				result.add(row);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

}
