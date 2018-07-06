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
import zabmtri.entity.EGlAccount;

public class GlAccountExporter {

	public void execute() {
		doExecute(AppData.glAccount, Util.glAccountOutputFile());
		doExecute(AppData.alphaGlAccount, Util.glAccountAlphaOutputFile());
		doExecute(AppData.betaGlAccount, Util.glAccountBetaOutputFile());
	}

	private void doExecute(List<EGlAccount> dataList, String outputFileName) {
		try {
			Path path = Paths.get(outputFileName);
			BufferedWriter writer = Files.newBufferedWriter(path);
			CSVPrinter csvPrinter = new CSVPrinter(writer, getHeader());
			try {
				for (EGlAccount data : dataList) {
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
		header.add("No. Akun");
		header.add("Nama Akun");
		header.add("Tipe Akun");
		header.add("Mata Uang");
		header.add("Saldo");
		header.add("Per Tgl");
		header.add("Catatan");
		header.add("Akun induk");

		String[] arr = header.toArray(new String[header.size()]);
		return CSVFormat.DEFAULT.withHeader(arr);
	}

	private Iterable<?> getRow(EGlAccount data) {
		List<Object> result = new ArrayList<Object>();
		result.add(data.glaccount);
		result.add(data.accountname);
		result.add(asAccountTypeName(data.accounttype));
		result.add(data.currencyname);
		result.add(Util.formatNumber(data.balance));
		result.add(Util.formatDateCsv(AppData.dateCutOff));
		result.add(data.memo);
		result.add(data.parentaccount);

		return result;
	}

	private String asAccountTypeName(Integer accounttype) {
		if (accounttype == null) {
			throw new RuntimeException("Error accounttype is null");
		}

		final String CASHBANK_ACCTYPE = "Kas Bank";
		final String AR_ACCTYPE = "Akun Piutang";
		final String INV_ACCTYPE = "Persediaan";
		final String OTHASS_ACCTYPE = "Aktiva Lancar lainnya";
		final String FIXASS_ACCTYPE = "Aktiva Tetap";
		final String ACCDEP_ACCTYPE = "Akumulasi Penyusutan";
		final String OTHER_ASSET_ACCTYPE = "Aktiva Lainnya";
		final String AP_ACCTYPE = "Akun Hutang";
		final String OTHLIA_ACCTYPE = "Hutang lancar lainnya";
		final String LTERMLIA_ACCTYPE = "Hutang Jangka Panjang";
		final String EQUITY_ACCTYPE = "Ekuitas";
		final String REVENUE_ACCTYPE = "Pendapatan";
		final String COGS_ACCTYPE = "Harga Pokok Penjualan";
		final String EXP_ACCTYPE = "Beban";
		final String OTHEXP_ACCTYPE = "Beban lain-lain";
		final String OTHINC_ACCTYPE = "Pendapatan lain";

		final String[] ACCTYPE = new String[] { OTHER_ASSET_ACCTYPE, CASHBANK_ACCTYPE, AR_ACCTYPE, INV_ACCTYPE, OTHASS_ACCTYPE, FIXASS_ACCTYPE, ACCDEP_ACCTYPE, AP_ACCTYPE, OTHLIA_ACCTYPE,
				LTERMLIA_ACCTYPE, EQUITY_ACCTYPE, REVENUE_ACCTYPE, COGS_ACCTYPE, EXP_ACCTYPE, OTHEXP_ACCTYPE, OTHINC_ACCTYPE };

		return ACCTYPE[accounttype - 6];
	}

}
