package zabmtri;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

	private static boolean initialized = false;
	private static String outputFolder = null;

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyy-MM-dd");
	private static final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

	public static String formatNumber(BigDecimal balance) {
		String result = balance.toPlainString();
		result = result.replace('.', ',');

		return result;
	}

	public static BigDecimal parseNumber(String string) {
		return new BigDecimal(string.replace(',', '.'));
	}

	public static Date parseDate(String string) {
		LocalDate localDate = LocalDate.parse(string);
		return Date.valueOf(localDate);
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
			File dir = new File("./output/20180618-091035");
			outputFolder = dir.toPath().normalize().toAbsolutePath().toString();
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

	public static String glAccountOutputFile() {
		return outputFile("master-01-glaccount.csv");
	}

	public static String warehsOutputFile() {
		return outputFile("master-02-warehouse.csv");
	}

	public static String vendorOutputFile() {
		return outputFile("master-03-vendor.csv");
	}

	public static String customerOutputFile() {
		return outputFile("master-04-customer.csv");
	}

	public static String itemOutputFile() {
		return outputFile("master-05-item.csv");
	}

	public static String itemSnOutputFile() {
		return outputFile("master-06-serial-number-item.csv");
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

}
