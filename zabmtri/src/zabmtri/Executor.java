package zabmtri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import zabmtri.entity.EGlAccount;
import zabmtri.entity.EItem;
import zabmtri.entity.EWarehs;
import zabmtri.exporter.GlAccountExporter;
import zabmtri.exporter.ItemExporter;

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

	private static void prepareData() {
		Util.printOutput("Preparing Data");
		
		AppData.alpha = createConnection(AppData.alphaPath);
		AppData.beta = createConnection(AppData.betaPath);

		// AppData.target = createConnection(AppData.targetPath);
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
	}

	private static Connection createConnection(String path) {
		try {
			System.out.println("Connecting ... ");

			if (path == null || path.isEmpty()) {
				throw new RuntimeException("File belum dipilih");
			}

			Properties props = new Properties();
			props.setProperty("user", "guest");
			props.setProperty("password", "guest");
			props.setProperty("encoding", "NONE");

			return DriverManager.getConnection("jdbc:firebirdsql:" + path, props);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
