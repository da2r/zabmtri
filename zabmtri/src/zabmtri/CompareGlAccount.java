package zabmtri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import zabmtri.entity.EGlAccount;

public class CompareGlAccount {

	private DbCompare owner;
	private ArrayList<Pair<EGlAccount>> data;

	public CompareGlAccount(DbCompare owner) {
		this.owner = owner;
		this.data = new ArrayList<Pair<EGlAccount>>();
	}

	public void start() throws SQLException, IOException {
		loadData();
		generateReport();
	}

	private void generateReport() throws IOException {
		File file = new File(Util.outputFile("glaccount-report.xlsx"));
		OutputStream os = new FileOutputStream(file);
		try {
			Workbook wb = new XSSFWorkbook();

			Sheet sheet = wb.createSheet("Akun");

			// sheet.setColumnWidth(0, 6000);

			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("No. Akun");
			header.createCell(1).setCellValue("Nama");
			header.createCell(2).setCellValue(CApp.ALPHA);
			header.createCell(3).setCellValue(CApp.BETA);
			header.createCell(4).setCellValue("Nama sama");

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Pair<EGlAccount> p = data.get(i);

				row.createCell(0).setCellValue(p.any().glaccount);
				row.createCell(1).setCellValue(p.any().accountname);
				row.createCell(2).setCellValue(Util.booleanText(p.onAlpha()));
				row.createCell(3).setCellValue(Util.booleanText(p.onBeta()));
				if (p.onBoth()) {
					row.createCell(4).setCellValue(Util.booleanText(p.hasEquals("accountname")));
				}
			}

			wb.write(os);

			IOUtils.closeQuietly(wb);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}

	private void loadData() throws SQLException {
		ResultSet rs;

		String sql = "SELECT * FROM glaccnt ORDER BY glaccount";
		
		rs = owner.alpha.createStatement().executeQuery(sql);
		List<EGlAccount> alpha = EGlAccount.readAll(rs);

		rs = owner.beta.createStatement().executeQuery(sql);
		List<EGlAccount> beta = EGlAccount.readAll(rs);

		for (EGlAccount a : alpha) {
			Pair<EGlAccount> p = new Pair<EGlAccount>();
			p.alpha = a;

			EGlAccount b = find(beta, a.glaccount);
			if (b != null) {
				p.beta = b;
				beta.remove(b);
			}

			data.add(p);
		}

		for (EGlAccount b : beta) {
			Pair<EGlAccount> p = new Pair<EGlAccount>();
			p.beta = b;
			data.add(p);
		}
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
