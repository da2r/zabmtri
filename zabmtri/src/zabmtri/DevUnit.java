package zabmtri;

public class DevUnit {

	public static void main(String[] args) {
		long tc = System.currentTimeMillis();

//		AppData.alphaPath = "192.168.1.202/3051:C:\\Documents and Settings\\herman\\Desktop\\ABM.GDB";
//		AppData.betaPath = "192.168.1.202/3051:C:\\Documents and Settings\\herman\\Desktop\\TRI.GDB";
		// AppData.targetPath = "192.168.1.202/3051:C:\\Documents and Settings\\herman\\Desktop\\TESTING.GDB";
		
		AppData.alphaPath = "/Users/herman/git/zabmtri/zabmtri/sample/ABM_V5.GDB";
		AppData.betaPath = "/Users/herman/git/zabmtri/zabmtri/sample/ABM_V5.GDB";
		
		Executor.executeMerge();

		tc = System.currentTimeMillis() - tc;
		System.out.println(String.format("finished : %,d ms", tc));
	}

}
