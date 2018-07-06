package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import zabmtri.AppData;

public class EFixAsset {

	public Integer assetid;
	public String assetcode;
	public String assetname;
	public Date purchasedate;
	public Integer depmethod;
	public BigDecimal depreciationrate;
	public BigDecimal assetcost;
	public BigDecimal residu;
	public String notes;
	public Date usagedate;
	public String assetacc;
	public String acmdepracc;
	public String deprexpacc;
	public Integer assettype;	// FK
	public Integer assetfistype;	// FK
	public Integer fisasset;
	public String locked_by;
	public Date locked_time;
	public Integer disposed;
	public Date lastjournaldate;
	public Integer acquisitionhistid;
	public Integer disposedhistid;
	public Integer estimatedlife;
	public Integer personid;
	public Integer intangible;
	public Integer deptid;
	public BigDecimal correctionpercentage;
	public Integer reconciled;
	public Integer reconcileid;
	public BigDecimal quantity;
	public Integer withfirstyear;

	public String assettypedesc;
	public String assetfistypedesc;

	public static List<EFixAsset> readAll(Connection conn) {
		try {
			List<EFixAsset> result = new ArrayList<EFixAsset>();

			StringBuilder sql = new StringBuilder();
			sql.append("select * from FIXASSET order by ASSETCODE ");

			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EFixAsset entity = new EFixAsset();
				entity.assetid = rs.getInt("assetid");
				entity.assetcode = rs.getString("assetcode");
				entity.assetname = rs.getString("assetname");
				entity.purchasedate = rs.getDate("purchasedate");
				entity.depmethod = rs.getInt("depmethod");
				entity.depreciationrate = rs.getBigDecimal("depreciationrate");
				entity.assetcost = rs.getBigDecimal("assetcost");
				entity.residu = rs.getBigDecimal("residu");
				entity.notes = rs.getString("notes");
				entity.usagedate = rs.getDate("usagedate");
				entity.assetacc = rs.getString("assetacc");
				entity.acmdepracc = rs.getString("acmdepracc");
				entity.deprexpacc = rs.getString("deprexpacc");
				entity.assettype = rs.getInt("assettype");
				entity.assetfistype = rs.getInt("assetfistype");
				entity.fisasset = rs.getInt("fisasset");
				entity.locked_by = rs.getString("locked_by");
				entity.locked_time = rs.getDate("locked_time");
				entity.disposed = rs.getInt("disposed");
				entity.lastjournaldate = rs.getDate("lastjournaldate");
				entity.acquisitionhistid = rs.getInt("acquisitionhistid");
				entity.disposedhistid = rs.getInt("disposedhistid");
				entity.estimatedlife = rs.getInt("estimatedlife");
				entity.personid = rs.getInt("personid");
				entity.intangible = rs.getInt("intangible");
				entity.deptid = rs.getInt("deptid");
				entity.correctionpercentage = rs.getBigDecimal("correctionpercentage");
				entity.reconciled = rs.getInt("reconciled");
				entity.reconcileid = rs.getInt("reconcileid");
				entity.quantity = rs.getBigDecimal("quantity");
				entity.withfirstyear = rs.getInt("withfirstyear");

				result.add(entity);
			}
			
			for (EFixAsset entity : result) {
				entity.assetfistypedesc = AppData.getFaFiscalDesc(entity.assetfistype);
				entity.assettypedesc = AppData.getFaSetTypDesc(entity.assettype);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
