package zabmtri.exporter;

import java.util.List;

import zabmtri.AppData;
import zabmtri.Util;
import zabmtri.entity.EItemSn;

public class ItemSnAllExporter extends ItemSnExporter {

	protected List<EItemSn> getData() {
		return AppData.itemSn;
	}
	
	protected String getOutputFileName() {
		return Util.itemSnAllOutputFile();
	}
}
