package zabmtri;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import zabmtri.entity.EApInv;
import zabmtri.entity.EApItmDet;
import zabmtri.entity.EArInv;
import zabmtri.entity.EArInvDet;

public class Util {

	private static boolean initialized = false;
	private static String outputFolder = null;

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyy-MM-dd");
	private static final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static String booleanText(Integer value) {
		return (value != null && value.equals(1)) ? "Yes" : "No";
	}

	public static String booleanText(boolean value) {
		return value ? "Yes" : "No";
	}

	public static boolean parseBoolean(String value) {
		return (value != null) && (value.equalsIgnoreCase("Yes"));
	}

	public static String formatDate(LocalDate date) {
		return date.format(DATE_FORMAT);
	}

	public static String formatDate(Date date) {
		return date.toLocalDate().format(DATE_FORMAT);
	}

	public static String formatDateCsv(LocalDate date) {
		return date.format(CSV_DATE_FORMAT);
	}

	public static String formatDateCsv(Date date) {
		return formatDateCsv(date.toLocalDate());
	}

	public static String formatNumber(BigDecimal balance) {
		if (balance == null) {
			return null;
		}

		String result = balance.toPlainString();
		// result = result.replace('.', ',');
		
		if (result.endsWith(".0000")) {
			result = result.substring(0, result.length() - 5);
		}

		return result;
	}

	public static BigDecimal parseNumber(String string) {
		// return new BigDecimal(string.replace(',', '.'));
		return new BigDecimal(string);
	}

	public static Date parseDate(String string) {
		LocalDate localDate = LocalDate.parse(string);
		return Date.valueOf(localDate);
	}

	public static int blankFirstAsc(String o1, String o2) {
		if (isBlank(o1)) {
			if (isBlank(o2)) {
				return 0;
			} else {
				return -1;
			}
		} else {
			if (isBlank(o2)) {
				return 1;
			} else {
				return o1.compareTo(o2);
			}
		}
	}

	public static boolean isBlank(String value) {
		return value == null || value.isEmpty();
	}

	public static String asFirebirdPath(String file) {
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("win");
		if (isWindows) {
			return "localhost/3051:" + file;
		} else {
			// Development
			return file;
		}
	}

	public static synchronized void initOutputFile() {
		if (initialized) {
			return;
		}

		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("win");
		if (isWindows) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd-hhmmss");
			String ts = LocalDateTime.now().format(formatter);

			File dir = new File("./" + ts);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			outputFolder = dir.toPath().normalize().toAbsolutePath().toString();
		} else {
			// Development
			File share = new File("/Volumes/share");
			if (share.exists() && share.isDirectory()) {
				outputFolder = share.toPath().normalize().toAbsolutePath().toString();
			} else {
				File dir = new File("./output/20180618-091035");
				outputFolder = dir.toPath().normalize().toAbsolutePath().toString();
			}
		}

		initialized = true;

	}

	public static String outputFile(String string) {
		if (!initialized) {
			initOutputFile();
		}

		return outputFolder + "/" + string;
	}

	public static String getOutputFolder() {
		if (!initialized) {
			initOutputFile();
		}

		return outputFolder;

	}

	public static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	public static void printOutput(String message) {
		System.out.println(message);

		if (AppData.mainForm != null) {
			AppData.mainForm.printOutput(message);
		}
	}

	public static Integer avoidZero(int value) {
		if (value == 0) {
			return null;
		}

		return value;
	}

	public static BigDecimal avoidNull(BigDecimal value) {
		if (value == null) {
			return BigDecimal.ZERO;
		}

		return value;
	}
	
	public static String escapeNewLine(String value) {
		if (value == null) {
			return null;
		}
		
		// String c = System.getProperty("line.separator");
		return value.replaceAll("\\r\\n", " ");
	}
	
	public static String removeNewLine(String value) {
		if (value == null) {
			return null;
		}
		
		// String c = System.getProperty("line.separator");
		return value.replaceAll("\\r\\n", "");
	}

	public static String glAccountOutputFile() {
		return outputFile("master-01-glaccount.csv");
	}

	public static String glAccountAlphaOutputFile() {
		return outputFile("master-01-glaccount-internal.csv");
	}

	public static String glAccountBetaOutputFile() {
		return outputFile("master-01-glaccount-external.csv");
	}

	public static String openingJvOutputFile() {
		return outputFile("master-01-glopenbalance.xml");
	}

	public static String warehsOutputFile() {
		return outputFile("master-02-warehouse.csv");
	}

	public static String salesmanOutputFile() {
		return outputFile("master-02-salesman.csv");
	}

	public static String vendorOutputFile() {
		return outputFile("master-03-vendor.csv");
	}

	public static String customerOutputFile() {
		return outputFile("master-04-customer.csv");
	}

	public static String itemCategoryOutputFile() {
		return outputFile("master-05-item-category.csv");
	}

	public static String itemOutputFile() {
		return outputFile("master-05-item.csv");
	}
	
	public static String itemSnAllOutputFile() {
		return outputFile("master-06-serial-number-item-all.csv");
	}

	public static String itemSnOutputFile() {
		return outputFile("master-06-serial-number-item.csv");
	}
	
	public static String itemSnVacantOutputFile() {
		return outputFile("master-06-serial-number-item-not-used.csv");
	}
	
	public static String itemWithoutSnOutputFile() {
		return outputFile("master-06-item-without-serial-number.csv");
	}

	public static String jvOutputFile() {
		return outputFile("master-07-journal-voucher.xml");
	}

	public static String opOutputFile() {
		return outputFile("master-08-other-payment.xml");
	}

	public static String odOutputFile() {
		return outputFile("master-09-other-deposit.xml");
	}

	public static String purchaseOutputFile() {
		return outputFile("master-10-purchase.xml");
	}

	public static String salesOutputFile() {
		return outputFile("master-11-sales.xml");
	}

	public static String faFiscalOutputFile() {
		return outputFile("master-12-fa-fiscal.csv");
	}

	public static String faSetTypOutputFile() {
		return outputFile("master-12-fa-type.csv");
	}

	public static String fixassetOutputFile() {
		return outputFile("master-12-fixed-asset.csv");
	}

	public static EArInv findArInv(List<EArInv> list, String invoiceno, int deliveryorder) {
		for (EArInv i : list) {
			if (i.invoiceno.equals(invoiceno) && i.deliveryorder == deliveryorder) {
				return i;
			}
		}
		return null;
	}

	public static EArInvDet findArInvDet(List<EArInvDet> list, EArInvDet compare) {
		int seq = compare.seq;

		EArInvDet result = list.get(seq);
		if (result.itemno.equals(compare.itemno) && result.quantity.equals(compare.quantity)) {
			return result;
		}

		for (EArInvDet i : list) {
			result = i;
			if (result.itemno.equals(compare.itemno) && result.quantity.equals(compare.quantity)) {
				return result;
			}
		}

		return null;
	}

	public static EApInv findApInv(List<EApInv> list, String invoiceno, int posted) {
		for (EApInv i : list) {
			if (i.invoiceno.equals(invoiceno) && i.posted == posted) {
				return i;
			}
		}
		return null;
	}

	public static EApItmDet findApInvDet(List<EApItmDet> list, EApItmDet compare) {
		int seq = compare.seq;

		EApItmDet result = list.get(seq);
		if (result.itemno.equals(compare.itemno) && result.quantity.equals(compare.quantity)) {
			return result;
		}

		for (EApItmDet i : list) {
			result = i;
			if (result.itemno.equals(compare.itemno) && result.quantity.equals(compare.quantity)) {
				return result;
			}
		}

		return null;
	}

}
