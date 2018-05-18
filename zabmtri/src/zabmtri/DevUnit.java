package zabmtri;

public class DevUnit {

	public static void main(String[] args) {
		long tc = System.currentTimeMillis();

		AppData.alphaPath = "192.168.1.202/3051:C:\\Documents and Settings\\herman\\Desktop\\TEST.GDB";
		AppData.betaPath = "192.168.1.202/3051:C:\\Program Files\\CPSSoft\\ACCURATE4 deluxe 1423\\SAMPLE\\SAMPLE.GDB";
		AppData.targetPath = "192.168.1.202/3051:C:\\Documents and Settings\\herman\\Desktop\\TEST2.GDB";
		
		Executor.execute();

		tc = System.currentTimeMillis() - tc;
		System.out.println(String.format("finished : %,d ms", tc));
	}

}
