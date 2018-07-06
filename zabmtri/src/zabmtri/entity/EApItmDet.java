package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zabmtri.DbUtil;

public class EApItmDet {

	public Integer apinvoiceid;
	public Integer seq;
	public String itemno;
	public BigDecimal quantity;
	public BigDecimal unitprice;
	public String taxcodes;
	public String itemovdesc;
	public Integer itemhistid;
	public Integer poid;
	public String itemunit;
	public BigDecimal unitratio;
	public Integer poseq;
	public BigDecimal taxableamount1;
	public BigDecimal taxableamount2;
	public BigDecimal brutounitprice;
	public String itemdiscpc;
	public Integer billid;
	public String itemreserved1;
	public String itemreserved2;
	public String itemreserved3;
	public String itemreserved4;
	public String itemreserved5;
	public String itemreserved6;
	public String itemreserved7;
	public String itemreserved8;
	public String itemreserved9;
	public String itemreserved10;
	public Integer deptid;
	public Integer projectid;
	public BigDecimal itemcost;
	public BigDecimal itemcostbase;
	public Integer warehouseid;
	public Integer riid;
	public Integer riseq;
	public BigDecimal qtyused;
	public BigDecimal qtycontrol;
	public Integer groupseq;
	public BigDecimal dpused;
	public Integer jobid;
	
	public String warehousename;
	public String rino;

	public List<ESnHistory> snhistory;

	public static List<EApItmDet> readAll(Connection conn, int masterid) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ApItmDet.* FROM ApItmDet ");
			sql.append("WHERE ApItmDet.apinvoiceid = ? ");
			sql.append("ORDER BY ApItmDet.seq");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, masterid);

			ResultSet rs = ps.executeQuery();

			List<EApItmDet> result = new ArrayList<EApItmDet>();
			while (rs.next()) {
				result.add(EApItmDet.read(rs));
			}

			for (EApItmDet row : result) {
				row.warehousename = DbUtil.getWarehouseName(conn, row.warehouseid);
				row.snhistory = ESnHistory.readAll(conn, row.itemhistid);
				row.rino = DbUtil.getApInvoiceNo(conn, row.riid);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static EApItmDet read(ResultSet rs) throws SQLException {
		EApItmDet entity = new EApItmDet();
		entity.apinvoiceid = rs.getInt("apinvoiceid");
		entity.seq = rs.getInt("seq");
		entity.itemno = rs.getString("itemno");
		entity.quantity = rs.getBigDecimal("quantity");
		entity.unitprice = rs.getBigDecimal("unitprice");
		entity.taxcodes = rs.getString("taxcodes");
		entity.itemovdesc = rs.getString("itemovdesc");
		entity.itemhistid = rs.getInt("itemhistid");
		entity.poid = rs.getInt("poid");
		entity.itemunit = rs.getString("itemunit");
		entity.unitratio = rs.getBigDecimal("unitratio");
		entity.poseq = rs.getInt("poseq");
		entity.taxableamount1 = rs.getBigDecimal("taxableamount1");
		entity.taxableamount2 = rs.getBigDecimal("taxableamount2");
		entity.brutounitprice = rs.getBigDecimal("brutounitprice");
		entity.itemdiscpc = rs.getString("itemdiscpc");
		entity.billid = rs.getInt("billid");
		entity.itemreserved1 = rs.getString("itemreserved1");
		entity.itemreserved2 = rs.getString("itemreserved2");
		entity.itemreserved3 = rs.getString("itemreserved3");
		entity.itemreserved4 = rs.getString("itemreserved4");
		entity.itemreserved5 = rs.getString("itemreserved5");
		entity.itemreserved6 = rs.getString("itemreserved6");
		entity.itemreserved7 = rs.getString("itemreserved7");
		entity.itemreserved8 = rs.getString("itemreserved8");
		entity.itemreserved9 = rs.getString("itemreserved9");
		entity.itemreserved10 = rs.getString("itemreserved10");
		entity.deptid = rs.getInt("deptid");
		entity.projectid = rs.getInt("projectid");
		entity.itemcost = rs.getBigDecimal("itemcost");
		entity.itemcostbase = rs.getBigDecimal("itemcostbase");
		entity.warehouseid = rs.getInt("warehouseid");
		entity.riid = rs.getInt("riid");
		entity.riseq = rs.getInt("riseq");
		entity.qtyused = rs.getBigDecimal("qtyused");
		entity.qtycontrol = rs.getBigDecimal("qtycontrol");
		entity.groupseq = rs.getInt("groupseq");
		entity.dpused = rs.getBigDecimal("dpused");
		entity.jobid = rs.getInt("jobid");

		return entity;
	}
}
