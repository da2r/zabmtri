package zabmtri;

public class Dev2Unit {
	public static void main(String[] args) throws Exception {
		AppData.alphaPath = "/Users/herman/Documents/File/temp/MITRASAMAYA15_V5.GDB";
		AppData.betaPath = "/Users/herman/Documents/File/temp/MS 2015_V5 TRI.GDB";
		 AppData.targetPath = "/Users/herman/Documents/File/temp/TRIAL1.GDB";
		// AppData.targetPath = "/Volumes/share/TRIAL2.GDB";
		// AppData.targetPath =
		// "/Users/herman/git/zabmtri/zabmtri/sample/Sample.GDB";
		
//		AppData.targetPath = "192.168.1.77/3051:D:\\share\\MS18.GDB";

		 Executor.executeMerge();
//		 Executor.executeImportWarehs();
//		 Executor.executeImportSalesman();
//		 Executor.executeImportItemSn();

		System.out.println("finished");
	}

}
