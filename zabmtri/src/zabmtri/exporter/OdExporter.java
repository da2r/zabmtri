package zabmtri.exporter;

import java.util.List;

import zabmtri.AppData;
import zabmtri.entity.EJv;

public class OdExporter extends JvExporter {
	
	@Override
	protected List<EJv> getDataList() {
		return AppData.betaOd;
	}

	@Override
	protected String getTransKey() {
		return "OTHERDEPOSIT";
	}

	@Override
	protected String getOutputFileName() {
		return "master-08-other-payment.xml";
	}
}
