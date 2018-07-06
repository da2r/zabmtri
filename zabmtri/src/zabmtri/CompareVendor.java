package zabmtri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import zabmtri.entity.EVendor;

public class CompareVendor {

	private ArrayList<Pair<EVendor>> data;

	public void start() throws SQLException, IOException {
		loadData();
		generateReport();
	}

	private void generateReport() throws IOException {
		File file = new File(Util.outputFile("report-vendor.xlsx"));
		OutputStream os = new FileOutputStream(file);
		try {
			Workbook wb = new XSSFWorkbook();

			Sheet sheet = wb.createSheet("Pemasok");

			// sheet.setColumnWidth(0, 6000);

			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("No. Barang");
			header.createCell(1).setCellValue("Nama");
			header.createCell(2).setCellValue(CApp.ALPHA);
			header.createCell(3).setCellValue(CApp.BETA);
			header.createCell(4).setCellValue("Nama sama");

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Pair<EVendor> p = data.get(i);

				row.createCell(0).setCellValue(p.any().personno);
				row.createCell(1).setCellValue(p.any().name);
				row.createCell(2).setCellValue(Util.booleanText(p.onAlpha()));
				row.createCell(3).setCellValue(Util.booleanText(p.onBeta()));
				if (p.onBoth()) {
					row.createCell(4).setCellValue(Util.booleanText(p.hasEquals("name")));
				}
			}

			wb.write(os);

			IOUtils.closeQuietly(wb);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}

	private void loadData() throws SQLException {
		data = new ArrayList<Pair<EVendor>>();

		List<EVendor> alpha = new ArrayList<EVendor>(AppData.alphaVendor);
		List<EVendor> beta = new ArrayList<EVendor>(AppData.betaVendor);

		for (EVendor a : alpha) {
			Pair<EVendor> p = new Pair<EVendor>();
			p.alpha = a;

			EVendor b = find(beta, a.personno);
			if (b != null) {
				p.beta = b;
				beta.remove(b);
			}

			data.add(p);
		}

		for (EVendor b : beta) {
			if (find(alpha, b.personno) == null) {
				Pair<EVendor> p = new Pair<EVendor>();
				p.beta = b;
				data.add(p);
			}
		}

		AppData.vendor = Pair.join(data);
	}

	private EVendor find(List<EVendor> list, String value) {
		for (EVendor r : list) {
			if (value.equals(r.personno)) {
				return r;
			}
		}

		return null;
	}

}
