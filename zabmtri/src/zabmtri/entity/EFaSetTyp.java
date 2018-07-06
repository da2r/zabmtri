package zabmtri.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import zabmtri.AppData;

public class EFaSetTyp {

	public Integer assettype;	// ID
	public String fatypedesc;
	public Integer afistypeid;	// FK FaFiscal
	
	public String afistypedesc;

	public static List<EFaSetTyp> readAll(Connection conn) {
		try {
			List<EFaSetTyp> result = new ArrayList<EFaSetTyp>();

			StringBuilder sql = new StringBuilder();
			sql.append("select * from FASETTYP order by FATYPEDESC ");

			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EFaSetTyp entity = new EFaSetTyp();
				entity.assettype = rs.getInt("assettype");
				entity.fatypedesc = rs.getString("fatypedesc");
				entity.afistypeid = rs.getInt("afistypeid");

				result.add(entity);
			}
			
			for (EFaSetTyp entity : result) {
				entity.afistypedesc = AppData.getFaFiscalDesc(entity.afistypeid);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
