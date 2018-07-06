package zabmtri.exporter;

import java.util.List;

import zabmtri.AppData;
import zabmtri.Util;
import zabmtri.entity.EItemSn;

public class ItemSnVacantExporter extends ItemSnExporter {

	protected List<EItemSn> getData() {
		return AppData.itemSnVacant;
	}
	
	protected String getOutputFileName() {
		return Util.itemSnVacantOutputFile();
	}
}
