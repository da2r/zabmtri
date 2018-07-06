package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EOwingSi {
	
	public Integer customerid;
	public BigDecimal owing;
	public String invoiceno;
	public Date invoicedate;
	
	public static List<EOwingSi> readAll(Connection conn, LocalDate asOf) {
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("select ARINV.CUSTOMERID, OWING_SI.OWING, ARINV.INVOICENO, ARINV.INVOICEDATE ");
			sql.append("from ARINV left join OWING_SI(?,  ARINV.ARINVOICEID) ON OWING_SI.ARINVOICEID=ARINV.ARINVOICEID ");
			sql.append("where (NOT OWING_SI.OWING IS NULL AND NOT OWING_SI.OWINGDC IS NULL) AND (OWING_SI.OWING<>0 OR OWING_SI.OWINGDC<>0) AND ARINV.INVOICEDATE<=?");

//			sql.append("select APINV.VENDORID, OWING_PI.OWING, APINV.INVOICENO, APINV.INVOICEDATE ");
//			sql.append("from APINV left join OWING_PI(?,  APINV.APINVOICEID) ON OWING_PI.APINVOICEID=APINV.APINVOICEID ");
//			sql.append("where (NOT OWING_PI.OWING IS NULL AND NOT OWING_PI.OWINGDC IS NULL) AND (OWING_PI.OWING<>0 OR OWING_PI.OWINGDC<>0) AND APINV.INVOICEDATE<=?");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setDate(1, Date.valueOf(asOf));
			ps.setDate(2, Date.valueOf(asOf));

			ResultSet rs = ps.executeQuery();

			List<EOwingSi> result = new ArrayList<EOwingSi>();
			while (rs.next()) {
				EOwingSi row = new EOwingSi();
				row.customerid = rs.getInt(1);
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
