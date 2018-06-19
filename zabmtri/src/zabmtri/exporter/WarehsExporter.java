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
import zabmtri.entity.EWarehs;

public class WarehsExporter {

	public void execute() {
		try {
			Path path = Paths.get(getOutputFileName());
			BufferedWriter writer = Files.newBufferedWriter(path);
			CSVPrinter csvPrinter = new CSVPrinter(writer, getHeader());
			try {
				for (EWarehs data : AppData.betaWarehs) {
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
		header.add("Nama");
		header.add("Keterangan");
		header.add("Alamat 1");
		header.add("Alamat 2");
		header.add("Alamat 3");
		header.add("PIC");
		header.add("Non Aktif");

		String[] arr = header.toArray(new String[header.size()]);
		return CSVFormat.DEFAULT.withHeader(arr);
	}

	private Iterable<?> getRow(EWarehs data) {
		List<Object> result = new ArrayList<Object>();
		result.add(data.name);
		result.add(data.description);
		result.add(data.address1);
		result.add(data.address2);
		result.add(data.address3);
		result.add(data.pic);
		result.add(Util.booleanText(data.suspended));

		return result;
	}
	
	private String getOutputFileName() {
		return Util.warehsOutputFile();
	}
}
