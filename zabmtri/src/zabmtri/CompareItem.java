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

import zabmtri.entity.EItem;

public class CompareItem {

	private ArrayList<Pair<EItem>> data;

	public void start() throws IOException {
		loadData();
		generateReport();
	}

	private void generateReport() throws IOException {
		File file = new File(Util.outputFile("report-item.xlsx"));
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
				Pair<EItem> p = data.get(i);

				row.createCell(0).setCellValue(p.any().itemno);
				row.createCell(1).setCellValue(p.any().itemdescription);
				row.createCell(2).setCellValue(Util.booleanText(p.onAlpha()));
				row.createCell(3).setCellValue(Util.booleanText(p.onBeta()));
				if (p.onBoth()) {
					row.createCell(4).setCellValue(Util.booleanText(p.hasEquals("itemdescription")));
				}
			}

			wb.write(os);

			IOUtils.closeQuietly(wb);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}

	private void loadData() {
		data = new ArrayList<Pair<EItem>>();

		List<EItem> alpha = AppData.alphaItem;
		List<EItem> beta = AppData.betaItem;

		for (EItem a : alpha) {
			Pair<EItem> p = new Pair<EItem>();
			p.alpha = a;

			EItem b = find(beta, a.itemno);
			if (b != null) {
				p.beta = b;
				beta.remove(b);
			}

			data.add(p);
		}

		for (EItem b : beta) {
			Pair<EItem> p = new Pair<EItem>();
			p.beta = b;
			data.add(p);
		}
	}

	private EItem find(List<EItem> list, String value) {
		for (EItem r : list) {
			if (value.equals(r.itemno)) {
				return r;
			}
		}

		return null;
	}

}
