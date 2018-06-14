package zabmtri;

import zabmtri.exporter.PurchaseExporter;
import zabmtri.exporter.SalesExporter;

public class Dev2Unit {
	public static void main(String[] args) throws Exception {
		AppData.alphaPath = "/Users/herman/git/zabmtri/zabmtri/sample/ABM_V5.GDB";
		AppData.betaPath = "/Users/herman/git/zabmtri/zabmtri/sample/ABM_V5.GDB";
		Executor.prepareData();

		SalesExporter se = new SalesExporter();
		se.execute();

		PurchaseExporter pe = new PurchaseExporter();
		pe.execute();
	}

}
