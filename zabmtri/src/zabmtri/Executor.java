package zabmtri;

import zabmtri.entity.EApCheq;
import zabmtri.entity.EApInv;
import zabmtri.entity.EArInv;
import zabmtri.entity.EArPmt;
import zabmtri.entity.EGlAccount;
import zabmtri.entity.EItem;
import zabmtri.entity.EJv;
import zabmtri.entity.EWarehs;
import zabmtri.exporter.GlAccountExporter;
import zabmtri.exporter.ItemExporter;
import zabmtri.exporter.JvExporter;
import zabmtri.exporter.OdExporter;
import zabmtri.exporter.OpExporter;

public class Executor {

	public static void execute() {
		prepareData();
		compareData();
		exportData();
	}

	private static void exportData() {
		Util.printOutput("Exporting..");
		new GlAccountExporter().execute();
		new ItemExporter().execute();
		new JvExporter().execute();
		new OpExporter().execute();
		new OdExporter().execute();
	}

	private static void compareData() {
		try {
			Util.printOutput("Comparing..");
			new CompareGlAccount().start();
			new CompareItem().start();
			new CompareWarehs().start();
			new CompareCustomer().start();
			new CompareVendor().start();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static void prepareData() {
		Util.printOutput("Preparing Data");
		
		AppData.alpha = DbUtil.createConnection(AppData.alphaPath);
		AppData.beta = DbUtil.createConnection(AppData.betaPath);

		System.out.println("WARN AppData.target is not connected");

		Util.printOutput("Reading Account..");
		AppData.alphaGlAccount = EGlAccount.readAll(AppData.alpha, AppData.dateCutOff);
		AppData.betaGlAccount = EGlAccount.readAll(AppData.beta, AppData.dateCutOff);

		Util.printOutput("Reading Item..");
		AppData.alphaItem = EItem.readAll(AppData.alpha, AppData.dateCutOff);
		AppData.betaItem = EItem.readAll(AppData.beta, AppData.dateCutOff);

		Util.printOutput("Reading Warehouse..");
		AppData.alphaWarehs = EWarehs.readAll(AppData.alpha);
		AppData.betaWarehs = EWarehs.readAll(AppData.beta);
		
		Util.printOutput("Reading Journal History..");
		AppData.betaJv = EJv.readAll(AppData.beta, "JV");
		AppData.betaOp = EJv.readAll(AppData.beta, "PMT");
		AppData.betaOd = EJv.readAll(AppData.beta, "DPT");
		
		Util.printOutput("Reading Sales..");
		AppData.arInv = EArInv.readAll(AppData.beta);
		AppData.arPmt = EArPmt.readAll(AppData.beta);
		
		Util.printOutput("Reading Purchases..");
		AppData.apInv = EApInv.readAll(AppData.beta);
		AppData.apCheq = EApCheq.readAll(AppData.beta);
		
	}

	
}
