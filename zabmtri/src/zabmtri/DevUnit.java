package zabmtri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import zabmtri.entity.EGlAccount;
import zabmtri.entity.EItem;
import zabmtri.entity.EWarehs;
import zabmtri.exporter.GlAccountExporter;

public class DevUnit {

	public static void main(String[] args) {
		long tc = System.currentTimeMillis();

		prepareData();
//		compareData();
		exportData();

		tc = System.currentTimeMillis() - tc;
		System.out.println(String.format("finished : %,d ms", tc));
	}

	private static void exportData() {
		new GlAccountExporter().execute();
	}

	private static void compareData() {
		try {
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
		String alpha = "192.168.1.202/3051:C:\\Documents and Settings\\herman\\Desktop\\TEST.GDB";
		String beta = "192.168.1.202/3051:C:\\Program Files\\CPSSoft\\ACCURATE4 deluxe 1423\\SAMPLE\\SAMPLE.GDB";
		String target = "192.168.1.202/3051:C:\\Documents and Settings\\herman\\Desktop\\TEST2.GDB";

		AppData.alpha = createConnection(alpha);
		AppData.beta = createConnection(beta);
		AppData.target = createConnection(target);

		AppData.alphaGlAccount = EGlAccount.readAll(AppData.alpha, AppData.dateCutOff);
		AppData.betaGlAccount = EGlAccount.readAll(AppData.beta, AppData.dateCutOff);

		AppData.alphaItem = EItem.readAll(AppData.alpha, AppData.dateCutOff);
		AppData.betaItem = EItem.readAll(AppData.beta, AppData.dateCutOff);

		AppData.alphaWarehs = EWarehs.readAll(AppData.alpha);
		AppData.betaWarehs = EWarehs.readAll(AppData.beta);

	}

	private static Connection createConnection(String path) {
		try {
			System.out.println("Connecting ...");

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
