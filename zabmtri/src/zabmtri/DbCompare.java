package zabmtri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbCompare {

	public IMainForm form;

	public Connection alpha;
	public Connection beta;
	public Connection target;

	public CompareGlAccount compareGlAccount;
	public CompareItem compareItem;

	public DbCompare(IMainForm form, String alpha, String beta, String target) {
		this.form = form;
		this.alpha = createConnection(alpha);
		this.beta = createConnection(beta);
		this.target = createConnection(target);

		this.compareGlAccount = new CompareGlAccount(this);
		this.compareItem = new CompareItem(this);
	}

	public void start() {
		try {
			compareGlAccount.start();
			compareItem.start();
			
			form.setProgressText("Proses selesai");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Connection createConnection(String path) {
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
