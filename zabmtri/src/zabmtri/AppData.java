package zabmtri;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import zabmtri.entity.ECustomer;
import zabmtri.entity.EGlAccount;
import zabmtri.entity.EItem;
import zabmtri.entity.EVendor;
import zabmtri.entity.EWarehs;

public class AppData {

	public static LocalDate dateCutOff = LocalDate.of(2018, 5, 1);
	
	public static String alphaPath;
	public static String betaPath;
	public static String targetPath;

	public static Connection alpha;
	public static Connection beta;
	public static Connection target;

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
	
	public static IMainForm mainForm;

	public static String branchCode = null;	// BUMMER!!!

}
