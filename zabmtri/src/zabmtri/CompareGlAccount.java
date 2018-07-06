package zabmtri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import zabmtri.entity.EGlAccount;

public class CompareGlAccount {

	private List<Pair<EGlAccount>> data;

	public void start() throws IOException {
		loadData();
		generateReport();
	}

	private void generateReport() throws IOException {
		File file = new File(Util.outputFile("report-glaccount.xlsx"));
		OutputStream os = new FileOutputStream(file);
		try {
			Workbook wb = new XSSFWorkbook();

			Sheet sheet = wb.createSheet("Akun");

			// sheet.setColumnWidth(0, 6000);

			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("No. Akun");
			header.createCell(1).setCellValue("Nama Internal");
			header.createCell(2).setCellValue("Nama Eksternal");
			header.createCell(3).setCellValue(CApp.ALPHA);
			header.createCell(4).setCellValue(CApp.BETA);
			header.createCell(5).setCellValue("Nama sama");

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Pair<EGlAccount> p = data.get(i);

				row.createCell(0).setCellValue(p.any().glaccount);
				row.createCell(1).setCellValue(p.alpha == null ? "" : p.alpha.accountname);
				row.createCell(2).setCellValue(p.beta == null ? "" : p.beta.accountname);
				row.createCell(3).setCellValue(Util.booleanText(p.onAlpha()));
				row.createCell(4).setCellValue(Util.booleanText(p.onBeta()));
				if (p.onBoth()) {
					row.createCell(5).setCellValue(Util.booleanText(p.hasEquals("accountname")));
				}
			}

			wb.write(os);

			IOUtils.closeQuietly(wb);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}

	private void loadData() {
		data = new ArrayList<Pair<EGlAccount>>();

		List<EGlAccount> alpha = AppData.alphaGlAccount;
		List<EGlAccount> beta = AppData.betaGlAccount;

		for (EGlAccount a : alpha) {
			Pair<EGlAccount> p = new Pair<EGlAccount>();
			p.alpha = a;

			EGlAccount b = find(beta, a.glaccount);
			if (b != null) {
				p.beta = b;
			}

			data.add(p);
		}

		for (EGlAccount b : beta) {
			if (find(alpha, b.glaccount) == null) {
				Pair<EGlAccount> p = new Pair<EGlAccount>();
				p.beta = b;
				data.add(p);
			}

		}

		AppData.glAccount = Pair.join(data);
		AppData.glAccount.sort(new Comparator<EGlAccount>() {

			public int compare(EGlAccount o1, EGlAccount o2) {
				int comp = Util.blankFirstAsc(o1.parentaccount, o2.parentaccount);
				if (comp != 0) {
					return comp;
				}
				
				return o1.glaccount.compareTo(o2.glaccount);
			}

		});

	}

	private EGlAccount find(List<EGlAccount> list, String value) {
		for (EGlAccount r : list) {
			if (value.equals(r.glaccount)) {
				return r;
			}
		}

		return null;
	}

}
