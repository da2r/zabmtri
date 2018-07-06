package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import zabmtri.DbUtil;

public class EItem {
	public String itemno;
	public String itemdescription;
	public Integer itemtype;
	public Integer subitem;
	public String parentitem;
	public BigDecimal quantity;
	public BigDecimal onorder;
	public BigDecimal onsales;
	public String taxcodes;
	public BigDecimal unitprice;
	public BigDecimal unitprice2;
	public BigDecimal unitprice3;
	public BigDecimal unitprice4;
	public BigDecimal unitprice5;
	public BigDecimal cost;
	public Integer suspended;
	public BigDecimal minimumqty;
	public String unit1;
	public String unit2;
	public String unit3;
	public BigDecimal ratio2;
	public BigDecimal ratio3;
	public String discpc;
	public Integer preferedvendor;
	public String reserved1;
	public String reserved2;
	public String reserved3;
	public String reserved4;
	public String reserved5;
	public String inventoryglaccnt;
	public String cogsglaccnt;
	public String purchaseretglaccnt;
	public String salesglaccnt;
	public String salesretglaccnt;
	public String notes;
	public String costmethod;
	public String locked_by;
	public Date locked_time;
	public String ptaxcodes;
	public String reserved6;
	public String reserved7;
	public String reserved8;
	public String reserved9;
	public String reserved10;
	public String salesdiscountaccnt;
	public String goodstransitaccnt;
	public String firstparentitem;
	public Integer indentlevel;
	public Integer warehouseid;
	public Integer projectid;
	public Integer deptid;
	public byte[] logo;
	public String format_logo;
	public BigDecimal weight;
	public BigDecimal deliveryleadtime;
	public BigDecimal dimheight;
	public BigDecimal dimwidth;
	public BigDecimal dimdepth;
	public Integer inventorygroup;
	public String finishedmtrlglaccnt;
	public Integer categoryid;
	public BigDecimal defstandardcost;
	public Integer transactionid;
	public Integer importedtransactionid;
	public Integer branchcodeid;
	public String unitcontrol;
	public String unbilledaccount;
	public BigDecimal qtycontrol;
	public Integer lft;
	public Integer rgt;
	public Integer isroot;
	public Integer serialnumbertype;
	public Integer forcesn;
	public Integer manageexpired;
	public Integer managesn;
	public String hscode;
	public BigDecimal importduty_rate;
	public Integer importduty_type;
	public BigDecimal cukai_rate;
	public Integer delivernostocksn;
	
	public String preferedvendorno;
	public String preferedvendorname;
	public String categoryname;
	
	public List<EItemWhQuantity> whQuantity;

	public static List<EItem> readAll(Connection conn, LocalDate asOf) {
		try {
			List<EItem> result = new ArrayList<EItem>();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT item.* FROM item ");
			sql.append("WHERE item.itemtype IS NOT NULL AND item.itemno <> '0' AND item.itemno <> '-1'");
			sql.append("ORDER BY item.lft");

			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.add(EItem.read(rs));
			}
			
			List<EWarehs> warehouseList = EWarehs.readAll(conn);
			for (EItem item: result) {
				item.preferedvendorno = DbUtil.getPersonNo(conn, item.preferedvendor);
				item.preferedvendorname = DbUtil.getPersonName(conn, item.preferedvendor);
				item.categoryname = DbUtil.getItemCategoryName(conn, item.categoryid); 
				
				boolean isLeaf = (item.rgt - item.lft) == 1;
				if (isLeaf) {
					item.whQuantity = EItemWhQuantity.readAll(conn, item.itemno, warehouseList);
				} else {
					item.whQuantity = EItemWhQuantity.padZero(warehouseList);
				}
				
				EItemWhQuantity.moveWarehouseToTop(item.whQuantity, item.warehouseid);
				EItemWhQuantity.removeZeroExceptTop(item.whQuantity);
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static EItem read(ResultSet rs) throws SQLException {
		EItem entity = new EItem();
		entity.itemno = rs.getString("itemno");
		entity.itemdescription = rs.getString("itemdescription");
		entity.itemtype = rs.getInt("itemtype");
		entity.subitem = rs.getInt("subitem");
		entity.parentitem = rs.getString("parentitem");
		entity.quantity = rs.getBigDecimal("quantity");
		entity.onorder = rs.getBigDecimal("onorder");
		entity.onsales = rs.getBigDecimal("onsales");
		entity.taxcodes = rs.getString("taxcodes");
		entity.unitprice = rs.getBigDecimal("unitprice");
		entity.unitprice2 = rs.getBigDecimal("unitprice2");
		entity.unitprice3 = rs.getBigDecimal("unitprice3");
		entity.unitprice4 = rs.getBigDecimal("unitprice4");
		entity.unitprice5 = rs.getBigDecimal("unitprice5");
		entity.cost = rs.getBigDecimal("cost");
		entity.suspended = rs.getInt("suspended");
		entity.minimumqty = rs.getBigDecimal("minimumqty");
		entity.unit1 = rs.getString("unit1");
		entity.unit2 = rs.getString("unit2");
		entity.unit3 = rs.getString("unit3");
		entity.ratio2 = rs.getBigDecimal("ratio2");
		entity.ratio3 = rs.getBigDecimal("ratio3");
		entity.discpc = rs.getString("discpc");
		entity.preferedvendor = rs.getInt("preferedvendor");
		entity.reserved1 = rs.getString("reserved1");
		entity.reserved2 = rs.getString("reserved2");
		entity.reserved3 = rs.getString("reserved3");
		entity.reserved4 = rs.getString("reserved4");
		entity.reserved5 = rs.getString("reserved5");
		entity.inventoryglaccnt = rs.getString("inventoryglaccnt");
		entity.cogsglaccnt = rs.getString("cogsglaccnt");
		entity.purchaseretglaccnt = rs.getString("purchaseretglaccnt");
		entity.salesglaccnt = rs.getString("salesglaccnt");
		entity.salesretglaccnt = rs.getString("salesretglaccnt");
		entity.notes = rs.getString("notes");
		entity.costmethod = rs.getString("costmethod");
		entity.locked_by = rs.getString("locked_by");
		entity.locked_time = rs.getDate("locked_time");
		entity.ptaxcodes = rs.getString("ptaxcodes");
		entity.reserved6 = rs.getString("reserved6");
		entity.reserved7 = rs.getString("reserved7");
		entity.reserved8 = rs.getString("reserved8");
		entity.reserved9 = rs.getString("reserved9");
		entity.reserved10 = rs.getString("reserved10");
		entity.salesdiscountaccnt = rs.getString("salesdiscountaccnt");
		entity.goodstransitaccnt = rs.getString("goodstransitaccnt");
		entity.firstparentitem = rs.getString("firstparentitem");
		entity.indentlevel = rs.getInt("indentlevel");
		entity.warehouseid = rs.getInt("warehouseid");
		entity.projectid = rs.getInt("projectid");
		entity.deptid = rs.getInt("deptid");
		// entity.logo = rs.getObject("logo");
		entity.format_logo = rs.getString("format_logo");
		entity.weight = rs.getBigDecimal("weight");
		entity.deliveryleadtime = rs.getBigDecimal("deliveryleadtime");
		entity.dimheight = rs.getBigDecimal("dimheight");
		entity.dimwidth = rs.getBigDecimal("dimwidth");
		entity.dimdepth = rs.getBigDecimal("dimdepth");
		entity.inventorygroup = rs.getInt("inventorygroup");
		entity.finishedmtrlglaccnt = rs.getString("finishedmtrlglaccnt");
		entity.categoryid = rs.getInt("categoryid");
		entity.defstandardcost = rs.getBigDecimal("defstandardcost");
		entity.transactionid = rs.getInt("transactionid");
		entity.importedtransactionid = rs.getInt("importedtransactionid");
		entity.branchcodeid = rs.getInt("branchcodeid");
		entity.unitcontrol = rs.getString("unitcontrol");
		entity.unbilledaccount = rs.getString("unbilledaccount");
		entity.qtycontrol = rs.getBigDecimal("qtycontrol");
		entity.lft = rs.getInt("lft");
		entity.rgt = rs.getInt("rgt");
		entity.isroot = rs.getInt("isroot");
		entity.serialnumbertype = rs.getInt("serialnumbertype");
		entity.forcesn = rs.getInt("forcesn");
		entity.manageexpired = rs.getInt("manageexpired");
		entity.managesn = rs.getInt("managesn");
		entity.hscode = rs.getString("hscode");
		entity.importduty_rate = rs.getBigDecimal("importduty_rate");
		entity.importduty_type = rs.getInt("importduty_type");
		entity.cukai_rate = rs.getBigDecimal("cukai_rate");
		entity.delivernostocksn = rs.getInt("delivernostocksn");

		return entity;
	}
}
