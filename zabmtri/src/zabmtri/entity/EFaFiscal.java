package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EFaFiscal {

	public Integer assetfistype;	// ID
	public String fafisdesc;	
	public BigDecimal fisrate;	// Computed
	public BigDecimal fisestlife;
	public Integer deprmethod;

	public static List<EFaFiscal> readAll(Connection conn) {
		try {
			List<EFaFiscal> result = new ArrayList<EFaFiscal>();

			StringBuilder sql = new StringBuilder();
			sql.append("select * from FAFISCAL order by fafisdesc ");

			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EFaFiscal entity = new EFaFiscal();
				entity.assetfistype = rs.getInt("assetfistype");
				entity.fafisdesc = rs.getString("fafisdesc");
				entity.fisrate = rs.getBigDecimal("fisrate");
				entity.fisestlife = rs.getBigDecimal("fisestlife");
				entity.deprmethod = rs.getInt("deprmethod");

				result.add(entity);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
