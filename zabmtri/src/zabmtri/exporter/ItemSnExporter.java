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
import zabmtri.entity.EItemSn;

public class ItemSnExporter {

	public void execute() {
		try {
			Path path = Paths.get(getOutputFileName());
			BufferedWriter writer = Files.newBufferedWriter(path);
			CSVPrinter csvPrinter = new CSVPrinter(writer, getHeader());
			try {
				for (EItemSn data : AppData.itemSn) {
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
		header.add("No. Barang");
		header.add("Nomor Seri");
		header.add("Tgl. Expire");
		header.add("Kuantitas");

		String[] arr = header.toArray(new String[header.size()]);
		return CSVFormat.DEFAULT.withHeader(arr);
	}

	private Iterable<?> getRow(EItemSn data) {
		List<Object> result = new ArrayList<Object>();
		result.add(data.itemno);
		result.add(data.serialnumber);
		result.add(data.expireddate);
		result.add(Util.formatNumber(data.quantity));

		return result;
	}
	
	private String getOutputFileName() {
		return Util.itemSnOutputFile();
	}
}
