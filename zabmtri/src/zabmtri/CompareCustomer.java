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

import zabmtri.entity.ECustomer;

public class CompareCustomer {

	private ArrayList<Pair<ECustomer>> data;

	public void start() throws SQLException, IOException {
		loadData();
		generateReport();
	}

	private void generateReport() throws IOException {
		File file = new File(Util.outputFile("report-customer.xlsx"));
		OutputStream os = new FileOutputStream(file);
		try {
			Workbook wb = new XSSFWorkbook();

			Sheet sheet = wb.createSheet("Barang");

			// sheet.setColumnWidth(0, 6000);

			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("No. Barang");
			header.createCell(1).setCellValue("Nama");
			header.createCell(2).setCellValue(CApp.ALPHA);
			header.createCell(3).setCellValue(CApp.BETA);
			header.createCell(4).setCellValue("Nama sama");

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Pair<ECustomer> p = data.get(i);

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
		data = new ArrayList<Pair<ECustomer>>();
		
		List<ECustomer> alpha = new ArrayList<ECustomer>(AppData.alphaCustomer);
		List<ECustomer> beta = new ArrayList<ECustomer>(AppData.betaCustomer);

		for (ECustomer a : alpha) {
			Pair<ECustomer> p = new Pair<ECustomer>();
			p.alpha = a;

			ECustomer b = find(beta, a.personno);
			if (b != null) {
				p.beta = b;
				beta.remove(b);
			}

			data.add(p);
		}

		for (ECustomer b : beta) {
			Pair<ECustomer> p = new Pair<ECustomer>();
			p.beta = b;
			data.add(p);
		}
	}

	private ECustomer find(List<ECustomer> list, String value) {
		for (ECustomer r : list) {
			if (value.equals(r.personno)) {
				return r;
			}
		}

		return null;
	}

}
