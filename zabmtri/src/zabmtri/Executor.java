package zabmtri;

import zabmtri.entity.EApCheq;
import zabmtri.entity.EApInv;
import zabmtri.entity.EArInv;
import zabmtri.entity.EArPmt;
import zabmtri.entity.EGlAccount;
import zabmtri.entity.EItem;
import zabmtri.entity.EItemSn;
import zabmtri.entity.EJv;
import zabmtri.entity.EWarehs;
import zabmtri.exporter.GlAccountExporter;
import zabmtri.exporter.ItemExporter;
import zabmtri.exporter.ItemSnExporter;
import zabmtri.exporter.JvExporter;
import zabmtri.exporter.OdExporter;
import zabmtri.exporter.OpExporter;
import zabmtri.exporter.PurchaseExporter;
import zabmtri.exporter.SalesExporter;
import zabmtri.exporter.WarehsExporter;
import zabmtri.importer.ItemSnImporter;
import zabmtri.importer.WarehsImporter;

public class Executor {

	public static void executeMerge() {
		Util.printOutput("Koneksi ke Database..");
		AppData.alpha = DbUtil.createConnection(AppData.alphaPath);
		try {
			AppData.beta = DbUtil.createConnection(AppData.betaPath);
			try {
				AppData.target = DbUtil.createConnection(AppData.targetPath);
				try {
					prepareData();
					compareData();
					exportData();
				} finally {
					DbUtil.closeQuietly(AppData.target);
				}
			} finally {
				DbUtil.closeQuietly(AppData.beta);
			}
		} finally {
			DbUtil.closeQuietly(AppData.alpha);
		}
	}

	private static void exportData() {
		Util.printOutput("Exporting..");
		new GlAccountExporter().execute();
		new ItemExporter().execute();
		new JvExporter().execute();
		new OpExporter().execute();
		new OdExporter().execute();
		new SalesExporter().execute();
		new PurchaseExporter().execute();
		new ItemSnExporter().execute();
		new WarehsExporter().execute();
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
		Util.printOutput("Membaca Akun..");
		AppData.alphaGlAccount = EGlAccount.readAll(AppData.alpha, AppData.dateCutOff);
		AppData.betaGlAccount = EGlAccount.readAll(AppData.beta, AppData.dateCutOff);

		Util.printOutput("Membaca Barang..");
		AppData.alphaItem = EItem.readAll(AppData.alpha, AppData.dateCutOff);
		AppData.betaItem = EItem.readAll(AppData.beta, AppData.dateCutOff);
		AppData.itemSn = EItemSn.readAll(AppData.beta, AppData.dateCutOff);

		Util.printOutput("Membaca Gudang..");
		AppData.alphaWarehs = EWarehs.readAll(AppData.alpha);
		AppData.betaWarehs = EWarehs.readAll(AppData.beta);

		Util.printOutput("Membaca Biaya..");
		AppData.betaJv = EJv.readAll(AppData.beta, "JV");
		AppData.betaOp = EJv.readAll(AppData.beta, "PMT");
		AppData.betaOd = EJv.readAll(AppData.beta, "DPT");

		Util.printOutput("Membaca Penjualan..");
		AppData.arInv = EArInv.readAll(AppData.beta);
		AppData.arPmt = EArPmt.readAll(AppData.beta);

		Util.printOutput("Membaca Pembelian..");
		AppData.apInv = EApInv.readAll(AppData.beta);
		AppData.apCheq = EApCheq.readAll(AppData.beta);
	}

	public static void executeImportWarehs() {
		AppData.target = DbUtil.createConnection(AppData.targetPath);
		try {
			WarehsImporter importer = new WarehsImporter();
			importer.execute();
		} finally {
			DbUtil.closeQuietly(AppData.target);
		}
	}

	public static void executeImportItemSn() {
		AppData.target = DbUtil.createConnection(AppData.targetPath);
		try {
			ItemSnImporter importer = new ItemSnImporter();
			importer.execute();
		} finally {
			DbUtil.closeQuietly(AppData.target);
		}
	}
}
