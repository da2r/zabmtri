package zabmtri;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zabmtri.entity.EItem;
import zabmtri.entity.EItemSn;
import zabmtri.entity.EItemWhQuantity;

public class ListoutItemWithoutSn {

	private Map<String, BigDecimal> snQuantityCache = new HashMap<String, BigDecimal>();

	public void start() {
		List<ItemQty> result = new ArrayList<ItemQty>();
		
		for (EItem item : AppData.item) {
			if (item.managesn.equals(1)) {
				BigDecimal itemQuantity = getItemQuantity(item);
				BigDecimal snQuantity = getSnQuantity(item.itemno);

				BigDecimal diff = itemQuantity.subtract(snQuantity);
				if (diff.compareTo(BigDecimal.ZERO) > 0) {
					ItemQty e = new ItemQty();
					e.itemno = item.itemno;
					
					e.quantity = diff;
					result.add(e);
				}
			}
		}
		
		AppData.itemWithoutSn = result;
	}

	private BigDecimal getItemQuantity(EItem item) {
		BigDecimal result = BigDecimal.ZERO;
		for (EItemWhQuantity whq : item.whQuantity) {
			result = result.add(whq.quantity);
		}

		return result;
	}

	private BigDecimal getSnQuantity(String itemno) {
		if (snQuantityCache.size() == 0) {
			generateSnQuantityCache();
		}

		BigDecimal result = snQuantityCache.get(itemno);
		if (result == null) {
			result = BigDecimal.ZERO;
		}
		return result;
	}

	private void generateSnQuantityCache() {
		for (EItemSn sn : AppData.itemSnUsed) {
			BigDecimal qty = snQuantityCache.get(sn.itemno);
			if (qty == null) {
				qty = BigDecimal.ZERO;
			}

			qty = qty.add(sn.quantity);
			snQuantityCache.put(sn.itemno, qty);
		}
	}
}
