package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zabmtri.AppData;
import zabmtri.DbUtil;
import zabmtri.Util;

public class EArInvDet {

	public Integer arinvoiceid;
	public Integer seq;
	public String itemno;
	public BigDecimal quantity;
	public BigDecimal unitprice;
	public String taxcodes;
	public String itemovdesc;
	public Integer itemhistid;
	public String itemdiscpc;
	public Integer soid;
	public String itemunit;
	public BigDecimal unitratio;
	public Integer deptid;
	public Integer projectid;
	public BigDecimal taxableamount1;
	public BigDecimal taxableamount2;
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
	public Integer usedinsiid;
	public Integer soseq;
	public BigDecimal brutounitprice;
	public BigDecimal itemcost;
	public BigDecimal itemcostbase;
	public Integer warehouseid;
	public Integer doid;
	public Integer doseq;
	public BigDecimal sistatqty;
	public BigDecimal qtycontrol;
	public Integer quoteid;
	public Integer quoteseq;
	public Integer groupseq;
	public BigDecimal dpused;
	public Integer rmacomponentseq;
	public Date warrantydate;
	public Integer actionactivityid;
	
	public String dono;
	public String warehousename;

	public List<ESnHistory> snhistory;

	public static List<EArInvDet> readAll(Connection conn, int masterid) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT arinvdet.* FROM arinvdet ");
			sql.append("WHERE arinvdet.arinvoiceid = ? ");
			sql.append("ORDER BY arinvdet.seq");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, masterid);

			ResultSet rs = ps.executeQuery();

			List<EArInvDet> result = new ArrayList<EArInvDet>();
			while (rs.next()) {
				result.add(EArInvDet.read(rs));
			}

			for (EArInvDet row : result) {
				row.dono = DbUtil.getArInvoiceNo(conn, row.doid); 
				row.warehousename = DbUtil.getWarehouseName(conn, row.warehouseid);
				
				row.snhistory = ESnHistory.readAll(conn, row.itemhistid);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static EArInvDet read(ResultSet rs) throws SQLException {
		EArInvDet entity = new EArInvDet();
		entity.arinvoiceid = rs.getInt("arinvoiceid");
		entity.seq = rs.getInt("seq");
		entity.itemno = rs.getString("itemno");
		entity.quantity = rs.getBigDecimal("quantity");
		entity.unitprice = rs.getBigDecimal("unitprice");
		entity.taxcodes = rs.getString("taxcodes");
		entity.itemovdesc = rs.getString("itemovdesc");
		entity.itemhistid = rs.getInt("itemhistid");
		entity.itemdiscpc = rs.getString("itemdiscpc");
		entity.soid = rs.getInt("soid");
		entity.itemunit = rs.getString("itemunit");
		entity.unitratio = rs.getBigDecimal("unitratio");
		entity.deptid = rs.getInt("deptid");
		entity.projectid = rs.getInt("projectid");
		entity.taxableamount1 = rs.getBigDecimal("taxableamount1");
		entity.taxableamount2 = rs.getBigDecimal("taxableamount2");
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
		entity.usedinsiid = rs.getInt("usedinsiid");
		entity.soseq = rs.getInt("soseq");
		entity.brutounitprice = rs.getBigDecimal("brutounitprice");
		entity.itemcost = rs.getBigDecimal("itemcost");
		entity.itemcostbase = rs.getBigDecimal("itemcostbase");
		entity.warehouseid = rs.getInt("warehouseid");
		entity.doid = Util.avoidZero(rs.getInt("doid"));
		entity.doseq = Util.avoidZero(rs.getInt("doseq"));
		entity.sistatqty = rs.getBigDecimal("sistatqty");
		entity.qtycontrol = Util.avoidNull(rs.getBigDecimal("qtycontrol"));
		entity.quoteid = rs.getInt("quoteid");
		entity.quoteseq = rs.getInt("quoteseq");
		entity.groupseq = rs.getInt("groupseq");
		entity.dpused = rs.getBigDecimal("dpused");
		entity.rmacomponentseq = rs.getInt("rmacomponentseq");
		entity.warrantydate = rs.getDate("warrantydate");
		entity.actionactivityid = rs.getInt("actionactivityid");

		return entity;
	}
}
