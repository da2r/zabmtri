package zabmtri;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import zabmtri.entity.ECustomer;
import zabmtri.entity.EGlAccount;
import zabmtri.entity.EItem;
import zabmtri.entity.EJv;
import zabmtri.entity.EVendor;
import zabmtri.entity.EWarehs;

public class AppData {

	public static LocalDate dateCutOff = LocalDate.of(2017, 12, 31);

	public static String alphaPath;
	public static String betaPath;

	public static Connection alpha;
	public static Connection beta;

	public static List<EGlAccount> alphaGlAccount;
	public static List<EGlAccount> betaGlAccount;

	public static List<EItem> alphaItem;
	public static List<EItem> betaItem;

	public static List<EWarehs> alphaWarehs;
	public static List<EWarehs> betaWarehs;

	public static List<ECustomer> alphaCustomer;
	public static List<ECustomer> betaCustomer;

	public static List<EVendor> alphaVendor;
	public static List<EVendor> betaVendor;

	public static List<EJv> betaJv;
	public static List<EJv> betaOp;
	public static List<EJv> betaOd;

	public static IMainForm mainForm;

	public static String branchCode = null; // BUMMER!!!

	public static String getBetaCurrencyName(String glaccount) {
		for (EGlAccount account : betaGlAccount) {
			if (account.glaccount.equals(glaccount)) {
				return account.currencyname;
			}
		}

		throw new RuntimeException("Cannot find currency for glaccount " + glaccount);
	}

}
