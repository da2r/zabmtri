package zabmtri.importer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import zabmtri.AppData;
import zabmtri.Util;
import zabmtri.entity.ESalesman;

public class SalesmanImporter {

	public void execute() {
		try {
			File file = new File(Util.salesmanOutputFile());
			CSVParser csv = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT.withFirstRecordAsHeader());

			for (CSVRecord rec : csv) {
				ESalesman sl = new ESalesman();
				sl.firstname = rec.get(0);
				sl.lastname = rec.get(1);
				sl.salesmanname = rec.get(2);
				sl.jobtitle = rec.get(3);
				sl.suspended = Util.parseBoolean(rec.get(4));

				try {
					if (ESalesman.isExists(AppData.target, sl.salesmanname)) {
						Util.printOutput(String.format("Penjual \"%s\" sudah ada, data diabaikan.", sl.salesmanname));
						continue;
					}

					Util.printOutput(String.format("Memasukan Penjual \"%s\"..", sl.salesmanname));
					ESalesman.write(AppData.target, sl);
				} catch (Exception t) {
					Util.printOutput("Proses tidak berhasil!!");
					Util.printOutput(Util.getStackTrace(t));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
