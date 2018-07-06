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
import zabmtri.entity.ESalesman;

public class SalesmanExporter {

	public void execute() {
		try {
			Path path = Paths.get(getOutputFileName());
			BufferedWriter writer = Files.newBufferedWriter(path);
			CSVPrinter csvPrinter = new CSVPrinter(writer, getHeader());
			try {
				for (ESalesman data : AppData.salesman) {
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
		header.add("Nama Depan");
		header.add("Nama Belakang");
		header.add("Nama Lengkap");
		header.add("Jabatan");
		header.add("Non Aktif");

		String[] arr = header.toArray(new String[header.size()]);
		return CSVFormat.DEFAULT.withHeader(arr);
	}

	private Iterable<?> getRow(ESalesman data) {
		List<Object> result = new ArrayList<Object>();
		result.add(data.firstname);
		result.add(data.lastname);
		result.add(data.salesmanname);
		result.add(data.jobtitle);
		result.add(Util.booleanText(data.suspended));

		return result;
	}

	private String getOutputFileName() {
		return Util.salesmanOutputFile();
	}
}
