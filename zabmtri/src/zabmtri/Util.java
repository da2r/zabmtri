package zabmtri;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

	private static boolean initialized = false;
	private static String outputFolder = null;

	public static String booleanText(boolean hasEquals) {
		return hasEquals ? "Yes" : "No";
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
			File dir = new File("./output");
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
}
