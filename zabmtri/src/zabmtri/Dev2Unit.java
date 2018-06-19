package zabmtri;

public class Dev2Unit {
	public static void main(String[] args) throws Exception {
		AppData.alphaPath = "/Users/herman/git/zabmtri/zabmtri/sample/ABM_V5.GDB";
		AppData.betaPath = "/Users/herman/git/zabmtri/zabmtri/sample/ABM_V5.GDB";
		AppData.targetPath = "/Users/herman/git/zabmtri/zabmtri/sample/ABM_V5.GDB";
		// AppData.targetPath =
		// "/Users/herman/git/zabmtri/zabmtri/sample/Sample.GDB";

		// Executor.executeMerge();
		// Executor.executeImportWarehs();
		Executor.executeImportItemSn();

		System.out.println("finished");
	}

}
