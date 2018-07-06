package zabmtri;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import zabmtri.entity.EItem;
import zabmtri.entity.EItemSn;
import zabmtri.entity.EItemWhQuantity;

public class TruncateItemSn {
	
	public List<EItemSn> used = new ArrayList<EItemSn>();
	public List<EItemSn> vacant = new ArrayList<EItemSn>();

	public void start() {
		used.clear();
		vacant.clear();
		
		List<EItemSn> running = new ArrayList<EItemSn>();
		String last = "";
		for (EItemSn sn : AppData.itemSn) {
			if (last.equals(sn.itemno) == false) {
				doTrunc(running);
				last = sn.itemno;
			}
			running.add(sn);
		}
		
		doTrunc(running);
		
		AppData.itemSnUsed = used;
		AppData.itemSnVacant = vacant;
	}

	private void doTrunc(List<EItemSn> running) {
		if (running == null || running.size() == 0) {
			return;
		}
		
		String itemno = running.get(0).itemno;

		BigDecimal qty = BigDecimal.ZERO;
		EItem item = AppData.getItem(itemno);
		for (EItemWhQuantity whq : item.whQuantity) {
			qty = qty.add(whq.quantity);
		}
		
		while (running.size() > 0) {
			EItemSn sn = running.remove(0);
			qty = qty.subtract(sn.quantity);
			
			if (qty.compareTo(BigDecimal.ZERO) < 0) {
				EItemSn a = EItemSn.clone(sn);
				a.quantity = sn.quantity.add(qty);
				if (a.quantity.compareTo(BigDecimal.ZERO) != 0) {
					used.add(a);
				}
				
				EItemSn b = EItemSn.clone(sn);
				b.quantity = qty.abs();
				vacant.add(b);
				
				while (running.size() > 0) {
					vacant.add(running.remove(0));
				}
			} else {
				used.add(sn);
			}
		}
		
	}
	
}
