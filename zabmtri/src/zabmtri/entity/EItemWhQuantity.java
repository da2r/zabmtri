package zabmtri.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import zabmtri.AppData;
import zabmtri.DbUtil;

public class EItemWhQuantity {

	public int warehouseid;
	public String warehousename;
	public BigDecimal quantity;
	public BigDecimal cost;

	public static List<EItemWhQuantity> readAll(Connection conn, String itemno) throws SQLException {
		List<EWarehs> warehouseList = EWarehs.readAll(conn);
		return readAll(conn, itemno, warehouseList);
	}

	public static List<EItemWhQuantity> readAll(Connection conn, String itemno, List<EWarehs> warehouseList) throws SQLException {
		List<EItemWhQuantity> result = new ArrayList<EItemWhQuantity>();

		for (EWarehs warehouse : warehouseList) {
			StringBuilder sql = new StringBuilder();
			sql.append("select QUANTITY from GET_ITEMBALANCEWH(?, ?, ?)");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setDate(1, Date.valueOf(AppData.dateCutOff));
			ps.setString(2, itemno);
			ps.setInt(3, warehouse.warehouseid);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				EItemWhQuantity entry = new EItemWhQuantity();
				entry.warehouseid = warehouse.warehouseid;
				entry.warehousename = warehouse.name;
				entry.quantity = rs.getBigDecimal(1);
				entry.cost = BigDecimal.ZERO;
				result.add(entry);
			}
		}

		BigDecimal totalquantity = calculateTotalQuantity(result);
		if (totalquantity.compareTo(BigDecimal.ZERO) == 0) {
			for (EItemWhQuantity e : result) {
				e.quantity = BigDecimal.ZERO;
			}
		} else {
			BigDecimal totalcost = DbUtil.getItemTotalCost(conn, itemno);
			BigDecimal unitcost = totalcost.divide(totalquantity, 8, RoundingMode.HALF_UP);

			// Distribute cost
			BigDecimal distributed = BigDecimal.ZERO;
			for (EItemWhQuantity e : result) {
				e.cost = e.quantity.multiply(unitcost).setScale(4, RoundingMode.HALF_UP);

				distributed = distributed.add(e.cost);
			}

			// handle remaining value due to rounding
			if (totalcost.compareTo(distributed) > 0) {
				BigDecimal remaining = totalcost.subtract(distributed);
				EItemWhQuantity last = result.get(result.size() - 1);
				last.cost = last.cost.add(remaining);
			}
		}

		return result;
	}

	private static BigDecimal calculateTotalQuantity(List<EItemWhQuantity> list) {
		BigDecimal result = BigDecimal.ZERO;
		for (EItemWhQuantity e : list) {
			result = result.add(e.quantity);
		}

		return result;
	}

	public static List<EItemWhQuantity> padZero(List<EWarehs> warehouseList) {
		List<EItemWhQuantity> result = new ArrayList<EItemWhQuantity>();
		for (EWarehs warehouse : warehouseList) {
			EItemWhQuantity e = new EItemWhQuantity();
			e.warehouseid = warehouse.warehouseid;
			e.warehousename = warehouse.name;
			e.quantity = BigDecimal.ZERO;
			e.cost = BigDecimal.ZERO;
			result.add(e);
		}

		return result;
	}

	public static void moveWarehouseToTop(List<EItemWhQuantity> list, int whid) {
		int pos = -1;
		for (int i = 0; i < list.size(); i++) {
			EItemWhQuantity el = list.get(i);
			if (el.warehouseid == whid) {
				pos = i;
				break;
			}
		}

		if (pos > -1) {
			EItemWhQuantity el = list.remove(pos);
			list.add(0, el);
		}
	}

	public static void removeZeroExceptTop(List<EItemWhQuantity> list) {
		Iterator<EItemWhQuantity> it = list.iterator();

		if (it.hasNext()) {
			// skip top element
			it.next();
		}

		while (it.hasNext()) {
			EItemWhQuantity el = it.next();

			if (el.quantity.compareTo(BigDecimal.ZERO) == 0) {
				it.remove();
			}
		}
	}

}
