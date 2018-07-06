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
import zabmtri.entity.EFaFiscal;

public class FaFiscalExporter {

	public void execute() {
		try {
			Path path = Paths.get(getOutputFileName());
			BufferedWriter writer = Files.newBufferedWriter(path);
			CSVPrinter csvPrinter = new CSVPrinter(writer, getHeader());
			try {
				for (EFaFiscal data : AppData.faFiscal) {
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
		header.add("Gol. Aktiva Tetap Pajak");
		header.add("Metode Penyusutan Pajak");
		header.add("Umur Ekonomis Pajak");

		String[] arr = header.toArray(new String[header.size()]);
		return CSVFormat.DEFAULT.withHeader(arr);
	}

	private Iterable<?> getRow(EFaFiscal data) {
		List<Object> result = new ArrayList<Object>();

		result.add(data.fafisdesc);
		result.add(asDeprMethodDesc(data.deprmethod));
		result.add(Util.formatNumber(data.fisestlife));
		return result;
	}

	private String asDeprMethodDesc(int value) {
		if (value == 10) {
			return "Tidak terdepresiasi";
		} else if (value == 11) {
			return "Metode Garis Lurus";
		} else if (value == 12) {
			return "Metode Angka Tahun";
		} else if (value == 13) {
			return "Metode Saldo Menurun";
		}

		return "" + value;
	}

	private String getOutputFileName() {
		return Util.faFiscalOutputFile();
	}
}
