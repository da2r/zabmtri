package zabmtri.importer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import zabmtri.AppData;
import zabmtri.Util;
import zabmtri.entity.EWarehs;

public class WarehsImporter {

	public void execute() {
		try {
			File file = new File(Util.warehsOutputFile());
			CSVParser csv = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT.withFirstRecordAsHeader());

			for (CSVRecord rec : csv) {
				EWarehs wh = new EWarehs();
				wh.name = rec.get(0);
				wh.description = rec.get(1);
				wh.address1 = rec.get(2);
				wh.address2 = rec.get(3);
				wh.address3 = rec.get(4);
				wh.pic = rec.get(5);
				wh.suspended = Util.parseBoolean(rec.get(6));

				try {
					if (EWarehs.isExists(AppData.target, wh.name)) {
						Util.printOutput(String.format("Gudang \"%s\" sudah ada, data diabaikan.", wh.name));
						continue;
					}

					Util.printOutput(String.format("Memasukan Gudang \"%s\"..", wh.name));
					EWarehs.write(AppData.target, wh);
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
