package zabmtri.exporter;

import java.util.List;

import zabmtri.AppData;
import zabmtri.Util;
import zabmtri.entity.EItemSn;

public class ItemSnUsedExporter extends ItemSnExporter {

	protected List<EItemSn> getData() {
		return AppData.itemSnUsed;
	}
	
	protected String getOutputFileName() {
		return Util.itemSnOutputFile();
	}
}
