package zabmtri;

import java.sql.SQLException;

import zabmtri.entity.EApCheq;
import zabmtri.entity.EApInv;
import zabmtri.entity.EArInv;
import zabmtri.entity.EArPmt;
import zabmtri.entity.ECustomer;
import zabmtri.entity.EFaFiscal;
import zabmtri.entity.EFaSetTyp;
import zabmtri.entity.EFixAsset;
import zabmtri.entity.EGlAccount;
import zabmtri.entity.EItem;
import zabmtri.entity.EItemCategory;
import zabmtri.entity.EItemSn;
import zabmtri.entity.EJv;
import zabmtri.entity.EOwingPi;
import zabmtri.entity.EOwingSi;
import zabmtri.entity.ESalesman;
import zabmtri.entity.EVendor;
import zabmtri.entity.EWarehs;
import zabmtri.exporter.CustomerExporter;
import zabmtri.exporter.FaFiscalExporter;
import zabmtri.exporter.FaSetTypExporter;
import zabmtri.exporter.FixAssetExporter;
import zabmtri.exporter.GlAccountExporter;
import zabmtri.exporter.ItemCategoryExporter;
import zabmtri.exporter.ItemExporter;
import zabmtri.exporter.ItemSnAllExporter;
import zabmtri.exporter.ItemSnUsedExporter;
import zabmtri.exporter.ItemSnVacantExporter;
import zabmtri.exporter.ItemWithoutSnExporter;
import zabmtri.exporter.JvExporter;
import zabmtri.exporter.OdExporter;
import zabmtri.exporter.OpExporter;
import zabmtri.exporter.PurchaseExporter;
import zabmtri.exporter.SalesExporter;
import zabmtri.exporter.SalesmanExporter;
import zabmtri.exporter.VendorExporter;
import zabmtri.exporter.WarehsExporter;
import zabmtri.importer.ItemSnImporter;
import zabmtri.importer.SalesmanImporter;
import zabmtri.importer.WarehsImporter;

public class Executor {

	public static boolean skipItem = false;
	public static boolean skipExpense = true;
	public static boolean skipSales = true;
	public static boolean skipPurchase = true;

	public static boolean skipFixedAsset = true;

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
		// new OpeningJvExporter().execute();

		if (skipItem == false) {
			new ItemCategoryExporter().execute();
			new ItemExporter().execute();
			new ItemSnAllExporter().execute();
			new ItemSnUsedExporter().execute();
			new ItemSnVacantExporter().execute();
			new ItemWithoutSnExporter().execute();
			new WarehsExporter().execute();
		}
		new SalesmanExporter().execute();
		new CustomerExporter().execute();
		new VendorExporter().execute();
		
		if (skipExpense == false) {
			new JvExporter().execute();
			new OpExporter().execute();
			new OdExporter().execute();
		}

		if (skipSales == false) {
			new SalesExporter().execute();
		}

		if (skipPurchase == false) {
			new PurchaseExporter().execute();
		}

		if (skipFixedAsset == false) {
			new FaFiscalExporter().execute();
			new FaSetTypExporter().execute();
			new FixAssetExporter().execute();
		}
	}

	private static void compareData() {
		try {
			Util.printOutput("Comparing..");
			new CompareGlAccount().start();

			if (skipItem == false) {
				new CompareItem().start();
				new CompareWarehs().start();
				
				new TruncateItemSn().start();
				new ListoutItemWithoutSn().start();
			}

			new CompareSalesman().start();
			new CompareCustomer().start();
			new CompareVendor().start();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static void prepareData() {
		// TODO data WTRAN
		// TODO data Sales Return
		// TODO data Purchase Return
		// TODO data Item Adjustment
		// TODO data Job costing

		AppData.branchCode = getTargetNativeBranchCode();
		AppData.obe = getTargetOBE();

		Util.printOutput("Membaca Akun..");
		AppData.alphaGlAccount = EGlAccount.readAll(AppData.alpha, AppData.dateCutOff);
		AppData.betaGlAccount = EGlAccount.readAll(AppData.beta, AppData.dateCutOff);

		if (skipItem) {
			Util.printOutput("Data Barang tidak dibaca..");
		} else {
			Util.printOutput("Membaca Barang..");
			AppData.alphaItem = EItem.readAll(AppData.alpha, AppData.dateCutOff);
			AppData.betaItem = EItem.readAll(AppData.beta, AppData.dateCutOff);
			AppData.itemSn = EItemSn.readAll(AppData.alpha, AppData.dateCutOff);
			AppData.alphaItemCategory = EItemCategory.readAll(AppData.alpha);
			AppData.betaItemCategory = EItemCategory.readAll(AppData.beta);
		}

		Util.printOutput("Membaca Gudang..");
		AppData.alphaWarehs = EWarehs.readAll(AppData.alpha);
		AppData.betaWarehs = EWarehs.readAll(AppData.beta);

		Util.printOutput("Membaca Penjual..");
		AppData.alphaSalesman = ESalesman.readAll(AppData.alpha);
		AppData.betaSalesman = ESalesman.readAll(AppData.beta);

		Util.printOutput("Membaca Pelanggan..");
		AppData.alphaCustomer = ECustomer.readAll(AppData.alpha);
		AppData.betaCustomer = ECustomer.readAll(AppData.beta);
		AppData.owingSi = EOwingSi.readAll(AppData.alpha, AppData.dateCutOff);

		Util.printOutput("Membaca Pemasok..");
		AppData.alphaVendor = EVendor.readAll(AppData.alpha);
		AppData.betaVendor = EVendor.readAll(AppData.beta);
		AppData.owingPi = EOwingPi.readAll(AppData.alpha, AppData.dateCutOff);

		if (skipExpense) {
			Util.printOutput("Data Jurnal Umum tidak dibaca..");
			Util.printOutput("Data Pembayaran Lain tidak dibaca..");
			Util.printOutput("Data Penerimaan Lain tidak dibaca..");
		} else {
			Util.printOutput("Membaca Biaya..");
			AppData.alphaJv = EJv.readAll(AppData.alpha, "JV");
			AppData.alphaOp = EJv.readAll(AppData.alpha, "PMT");
			AppData.alphaOd = EJv.readAll(AppData.alpha, "DPT");
		}

		if (skipSales) {
			Util.printOutput("Data Penjualan tidak dibaca..");
		} else {
			Util.printOutput("Membaca Penjualan..");
			AppData.arDel = EArInv.readAll(AppData.alpha, 1);
			AppData.arInv = EArInv.readAll(AppData.alpha, 0);
			AppData.arPmt = EArPmt.readAll(AppData.alpha);
		}

		// List<EArInv> betaArDel = EArInv.readAll(AppData.alpha, 1);
		// for (EArInv e : AppData.arDel) {
		// EArInv c = Util.findArInv(betaArDel, e.invoiceno, 1);
		// for (EArInvDet det : e.detail) {
		// EArInvDet cd = Util.findArInvDet(c.detail, det);
		// det.snhistory = cd.snhistory;
		// }
		// }
		//
		// List<EArInv> betaArInv = EArInv.readAll(AppData.alpha, 0);
		// for (EArInv e : AppData.arDel) {
		// EArInv c = Util.findArInv(betaArInv, e.invoiceno, 0);
		// for (EArInvDet det : e.detail) {
		// EArInvDet cd = Util.findArInvDet(c.detail, det);
		// det.snhistory = cd.snhistory;
		// }
		// }

		if (skipPurchase) {
			Util.printOutput("Data Pembelian tidak dibaca..");
		} else {
			Util.printOutput("Membaca Pembelian..");
			AppData.apRec = EApInv.readAll(AppData.alpha, 0);
			AppData.apInv = EApInv.readAll(AppData.alpha, 1);
			AppData.apCheq = EApCheq.readAll(AppData.alpha);

			// List<EApInv> betaApRec = EApInv.readAll(AppData.alpha, 0);
			// for (EApInv e : AppData.apRec) {
			// EApInv c = Util.findApInv(betaApRec, e.invoiceno, 0);
			// for (EApItmDet det : e.detail) {
			// EApItmDet cd = Util.findApInvDet(c.detail, det);
			// det.snhistory = cd.snhistory;
			// }
			// }
			//
			// List<EApInv> betaApInv = EApInv.readAll(AppData.alpha, 1);
			// for (EApInv e : AppData.arDel) {
			// EApInv c = Util.findApInv(betaApInv, e.invoiceno, 1);
			// for (EApInvDet det : e.detail) {
			// EApInvDet cd = Util.findApInvDet(c.detail, det);
			// det.snhistory = cd.snhistory;
			// }
			// }
		}

		if (skipFixedAsset) {
			Util.printOutput("Data Aktiva Tetap tidak dibaca..");
		} else {
			Util.printOutput("Membaca Aktiva Tetap..");
			AppData.faFiscal = EFaFiscal.readAll(AppData.alpha);
			AppData.faSetTyp = EFaSetTyp.readAll(AppData.alpha);
			AppData.fixAsset = EFixAsset.readAll(AppData.alpha);
		}
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

	public static void executeImportSalesman() {
		AppData.target = DbUtil.createConnection(AppData.targetPath);
		try {
			SalesmanImporter importer = new SalesmanImporter();
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
