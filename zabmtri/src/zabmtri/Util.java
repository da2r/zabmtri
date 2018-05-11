package zabmtri;

public class Util {

	public static String booleanText(boolean hasEquals) {
		return hasEquals ? "Yes" : "No";
	}

	public static String outputFile(String string) {
		return "./output/" + string;
	}
}
