package zabmtri.exporter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import zabmtri.AppData;
import zabmtri.Util;
import zabmtri.entity.EGlAccount;

public class GlAccountExporter {

	public void execute() {
		try {
			Path path = Paths.get(getOutputFileName());
			BufferedWriter writer = Files.newBufferedWriter(path);
			CSVPrinter csvPrinter = new CSVPrinter(writer, getHeader());
			try {
				for (EGlAccount data : AppData.alphaGlAccount) {
					csvPrinter.printRecord(getRow(data));
				}

				csvPrinter.flush();
			} finally {
				csvPrinter.close();
				writer.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private CSVFormat getHeader() {
		ArrayList<String> header = new ArrayList<String>();
		header.add("No. Akun");
		header.add("Nama Akun");
		header.add("Tipe Akun");
		header.add("Catatan");
		header.add("Saldo");

		String[] arr = header.toArray(new String[header.size()]);
		return CSVFormat.DEFAULT.withHeader(arr);
	}

	private Iterable<?> getRow(EGlAccount data) {
		List<Object> result = new ArrayList<Object>();
		result.add(data.glaccount);
		result.add(data.accountname);
		result.add(data.accounttype);
		result.add(data.memo);
		result.add(data.balance);

		return result;
	}

	private String getOutputFileName() {
		return Util.outputFile("master-1-glaccount.csv");
	}
}
