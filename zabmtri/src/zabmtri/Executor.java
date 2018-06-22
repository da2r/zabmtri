package zabmtri;

import java.sql.SQLException;

import zabmtri.entity.EApCheq;
import zabmtri.entity.EApInv;
import zabmtri.entity.EArInv;
import zabmtri.entity.EArPmt;
import zabmtri.entity.ECustomer;
import zabmtri.entity.EGlAccount;
import zabmtri.entity.EItem;
import zabmtri.entity.EItemCategory;
import zabmtri.entity.EItemSn;
import zabmtri.entity.EJv;
import zabmtri.entity.EVendor;
import zabmtri.entity.EWarehs;
import zabmtri.exporter.CustomerExporter;
import zabmtri.exporter.GlAccountExporter;
import zabmtri.exporter.ItemCategoryExporter;
import zabmtri.exporter.ItemExporter;
import zabmtri.exporter.ItemSnExporter;
import zabmtri.exporter.JvExporter;
import zabmtri.exporter.OdExporter;
import zabmtri.exporter.OpExporter;
import zabmtri.exporter.OpeningJvExporter;
import zabmtri.exporter.PurchaseExporter;
import zabmtri.exporter.SalesExporter;
import zabmtri.exporter.VendorExporter;
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
		new OpeningJvExporter().execute();
		new ItemCategoryExporter().execute();
		new ItemExporter().execute();
		new ItemSnExporter().execute();
		new WarehsExporter().execute();
		new CustomerExporter().execute();
		new VendorExporter().execute();
		new JvExporter().execute();
		new OpExporter().execute();
		new OdExporter().execute();
		new SalesExporter().execute();
		new PurchaseExporter().execute();
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
		AppData.branchCode = getTargetNativeBranchCode();
		AppData.obe = getTargetOBE();
		
		Util.printOutput("Membaca Akun..");
		AppData.alphaGlAccount = EGlAccount.readAll(AppData.alpha, AppData.dateCutOff);
		AppData.betaGlAccount = EGlAccount.readAll(AppData.beta, AppData.dateCutOff);

		Util.printOutput("Membaca Barang..");
		AppData.alphaItem = EItem.readAll(AppData.alpha, AppData.dateCutOff);
		AppData.betaItem = EItem.readAll(AppData.beta, AppData.dateCutOff);
		AppData.itemSn = EItemSn.readAll(AppData.beta, AppData.dateCutOff);
		AppData.alphaItemCategory = EItemCategory.readAll(AppData.alpha);
		AppData.betaItemCategory = EItemCategory.readAll(AppData.beta);

		Util.printOutput("Membaca Gudang..");
		AppData.alphaWarehs = EWarehs.readAll(AppData.alpha);
		AppData.betaWarehs = EWarehs.readAll(AppData.beta);
		
		Util.printOutput("Membaca Pelanggan..");
		AppData.alphaCustomer = ECustomer.readAll(AppData.alpha);
		AppData.betaCustomer = ECustomer.readAll(AppData.beta);
		
		Util.printOutput("Membaca Pemasok..");
		AppData.alphaVendor = EVendor.readAll(AppData.alpha);
		AppData.betaVendor = EVendor.readAll(AppData.beta);

		Util.printOutput("Membaca Biaya..");
		AppData.betaJv = EJv.readAll(AppData.beta, "JV");
		AppData.betaOp = EJv.readAll(AppData.beta, "PMT");
		AppData.betaOd = EJv.readAll(AppData.beta, "DPT");

		Util.printOutput("Membaca Penjualan..");
		// AppData.arDel = EArInv.readAll(AppData.beta, 1);
		AppData.arInv = EArInv.readAll(AppData.beta, 0);
		AppData.arPmt = EArPmt.readAll(AppData.beta);

		Util.printOutput("Membaca Pembelian..");
		// AppData.apRec = EApInv.readAll(AppData.beta, 0);
		AppData.apInv = EApInv.readAll(AppData.beta, 1);
		AppData.apCheq = EApCheq.readAll(AppData.beta);
	}

	private static String getTargetNativeBranchCode() {
		try {
			return DbUtil.getNativeBranchCode(AppData.target);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String getTargetOBE() {
		try {
			String result = DbUtil.getNativeBranchCode(AppData.target);
			if (result == null) {
				result = "310001";
			}
			
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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
