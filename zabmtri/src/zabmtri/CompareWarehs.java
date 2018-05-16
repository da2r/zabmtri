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

import zabmtri.entity.EWarehs;

public class CompareWarehs {

	private DbCompare owner;
	private ArrayList<Pair<EWarehs>> data;

	public CompareWarehs(DbCompare owner) {
		this.owner = owner;
		this.data = new ArrayList<Pair<EWarehs>>();
	}

	public void start() throws SQLException, IOException {
		loadData();
		generateReport();
	}

	private void generateReport() throws IOException {
		File file = new File(Util.outputFile("report-warehouse.xlsx"));
		OutputStream os = new FileOutputStream(file);
		try {
			Workbook wb = new XSSFWorkbook();

			Sheet sheet = wb.createSheet("Gudang");

			// sheet.setColumnWidth(0, 6000);

			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Nama Gudang");
			header.createCell(1).setCellValue("Keterangan");
			header.createCell(2).setCellValue(CApp.ALPHA);
			header.createCell(3).setCellValue(CApp.BETA);

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Pair<EWarehs> p = data.get(i);

				row.createCell(0).setCellValue(p.any().name);
				row.createCell(1).setCellValue(p.any().description);
				row.createCell(2).setCellValue(Util.booleanText(p.onAlpha()));
				row.createCell(3).setCellValue(Util.booleanText(p.onBeta()));
			}

			wb.write(os);

			IOUtils.closeQuietly(wb);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}

	private void loadData() throws SQLException {
		ResultSet rs;
		String sql = "SELECT * FROM warehs ORDER BY name";
		
		rs = owner.alpha.createStatement().executeQuery(sql);
		List<EWarehs> alpha = EWarehs.readAll(rs);

		rs = owner.beta.createStatement().executeQuery(sql);
		List<EWarehs> beta = EWarehs.readAll(rs);

		for (EWarehs a : alpha) {
			Pair<EWarehs> p = new Pair<EWarehs>();
			p.alpha = a;

			EWarehs b = find(beta, a.name);
			if (b != null) {
				p.beta = b;
				beta.remove(b);
			}

			data.add(p);
		}

		for (EWarehs b : beta) {
			Pair<EWarehs> p = new Pair<EWarehs>();
			p.beta = b;
			data.add(p);
		}
	}

	private EWarehs find(List<EWarehs> list, String value) {
		for (EWarehs r : list) {
			if (value.equals(r.name)) {
				return r;
			}
		}

		return null;
	}

}
