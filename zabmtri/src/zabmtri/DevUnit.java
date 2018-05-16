package zabmtri;

public class DevUnit {

	public static void main(String[] args) {
		long tc = System.currentTimeMillis();

		String alpha = "192.168.1.202/3051:C:\\Documents and Settings\\herman\\Desktop\\TEST.GDB";
		String beta = "192.168.1.202/3051:C:\\Program Files\\CPSSoft\\ACCURATE4 deluxe 1423\\SAMPLE\\SAMPLE.GDB";
		String target = "192.168.1.202/3051:C:\\Documents and Settings\\herman\\Desktop\\TEST2.GDB";

		IMainForm dev = new IMainForm() {

			public void setProgressText(String text) {
				System.out.println(text);
			}
		};
		DbCompare compare = new DbCompare(dev, alpha, beta, target);
		compare.start();
		
		new GlAccountExport().execute();

		tc = System.currentTimeMillis() - tc;
		System.out.println(String.format("finished : %,d ms", tc));
	}
}
