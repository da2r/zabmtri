package zabmtri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import zabmtri.entity.ESalesman;

public class CompareSalesman {

	private ArrayList<Pair<ESalesman>> data;

	public void start() throws IOException {
		loadData();
		generateReport();
	}

	private void generateReport() throws IOException {
		File file = new File(Util.outputFile("report-salesman.xlsx"));
		OutputStream os = new FileOutputStream(file);
		try {
			Workbook wb = new XSSFWorkbook();

			Sheet sheet = wb.createSheet("Penjual");

			// sheet.setColumnWidth(0, 6000);

			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Nama");
			header.createCell(1).setCellValue("Jabatan");
			header.createCell(2).setCellValue(CApp.ALPHA);
			header.createCell(3).setCellValue(CApp.BETA);

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Pair<ESalesman> p = data.get(i);

				row.createCell(0).setCellValue(p.any().salesmanname);
				row.createCell(1).setCellValue(p.any().jobtitle);
				row.createCell(2).setCellValue(Util.booleanText(p.onAlpha()));
				row.createCell(3).setCellValue(Util.booleanText(p.onBeta()));
			}

			wb.write(os);

			IOUtils.closeQuietly(wb);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}

	private void loadData() {
		data = new ArrayList<Pair<ESalesman>>();

		List<ESalesman> alpha = AppData.alphaSalesman;
		List<ESalesman> beta = AppData.betaSalesman;

		for (ESalesman a : alpha) {
			Pair<ESalesman> p = new Pair<ESalesman>();
			p.alpha = a;

			ESalesman b = find(beta, a.salesmanname);
			if (b != null) {
				p.beta = b;
			}

			data.add(p);
		}

		for (ESalesman b : beta) {
			if (find(alpha, b.salesmanname) == null) {
				Pair<ESalesman> p = new Pair<ESalesman>();
				p.beta = b;
				data.add(p);
			}
		}

		AppData.salesman = Pair.join(data);
	}

	private ESalesman find(List<ESalesman> list, String value) {
		for (ESalesman r : list) {
			if (value.equals(r.salesmanname)) {
				return r;
			}
		}

		return null;
	}

}
