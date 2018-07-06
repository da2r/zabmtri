package zabmtri.exporter;

import java.util.List;

import zabmtri.AppData;
import zabmtri.Util;
import zabmtri.entity.EJv;

public class OpExporter extends JvExporter {
	
	@Override
	protected List<EJv> getDataList() {
		return AppData.alphaOp;
	}

	@Override
	protected String getTransKey() {
		return "OTHERPAYMENT";
	}

	@Override
	protected String getOutputFileName() {
		return Util.opOutputFile();
	}
}
