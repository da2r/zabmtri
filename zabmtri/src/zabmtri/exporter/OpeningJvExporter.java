package zabmtri.exporter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import zabmtri.AppData;
import zabmtri.BaseExport;
import zabmtri.DbUtil;
import zabmtri.Util;
import zabmtri.entity.EGlAccount;

public class OpeningJvExporter extends BaseExport {

	public static class Entry {
		public String glaccount;
		public BigDecimal glamount;
		public String currencyname;
		public BigDecimal rate = BigDecimal.ONE;

		public BigDecimal primeamount() {
			return glamount.divide(rate, 4, RoundingMode.HALF_UP);
		}
	}

	public void execute() {
		Connection conn = AppData.beta;
		List<EGlAccount> list = AppData.betaGlAccount;

		List<Entry> data = new ArrayList<Entry>();
		for (EGlAccount account : list) {
			Entry e = new Entry();
			e.glaccount = account.glaccount;
			e.glamount = account.balance;
			if (account.currencyname != null && !account.currencyname.isEmpty()) {
				e.currencyname = account.currencyname;
				e.rate = getCurrencyRate(conn, account.currencyname);
			}

			data.add(e);
		}
		execute(data);
	}

	private BigDecimal getCurrencyRate(Connection conn, String currencyname) {
		try {
			Integer currencyid;
			currencyid = DbUtil.getCurrencyIdByName(conn, currencyname);
			if (currencyid == null) {
				throw new RuntimeException("Mata uang tidak ditemukan " + currencyname);
			}

			return DbUtil.getCurrencyRate(conn, currencyid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void execute(List<Entry> data) {
		Node nmexml = doc.createElement("NMEXML");
		doc.appendChild(nmexml);
		addAttr(nmexml, "EximID", "");
		addAttr(nmexml, "BranchCode", AppData.branchCode);
		addAttr(nmexml, "ACCOUNTANTCOPYID", "");

		Node transaction = doc.createElement("TRANSACTIONS");
		nmexml.appendChild(transaction);
		addAttr(transaction, "OnError", "CONTINUE");

		Node trans = doc.createElement("JV");
		addAttr(trans, "operation", "Add");
		addAttr(trans, "REQUESTID", "1");
		transaction.appendChild(trans);

		trans.appendChild(node("TRANSACTIONID", getTransactionId()));
		trans.appendChild(node("JVNUMBER", ""));
		trans.appendChild(node("TRANSDATE", AppData.dateCutOff));
		trans.appendChild(node("SOURCE", "GL"));
		trans.appendChild(node("TRANSTYPE", "journal voucher"));
		trans.appendChild(node("TRANSDESCRIPTION", "Saldo Awal Akun Perkiraan"));

		BigDecimal totalPrimeAmount = getTotalPrimeAmount(data);
		trans.appendChild(node("JVAMOUNT", totalPrimeAmount));

		for (int i = 0; i < data.size(); i++) {
			Entry entry = data.get(i);

			Node detailA = doc.createElement("ACCOUNTLINE");
			addAttr(detailA, "operation", "Add");
			trans.appendChild(detailA);

			detailA.appendChild(node("KeyID", i + 1));
			detailA.appendChild(node("GLACCOUNT", entry.glaccount));
			detailA.appendChild(node("GLAMOUNT", entry.glamount));
			detailA.appendChild(node("DESCRIPTION", ""));
			detailA.appendChild(node("RATE", entry.rate));
			detailA.appendChild(node("PRIMEAMOUNT", entry.primeamount()));
			detailA.appendChild(node("TXDATE", ""));
			detailA.appendChild(node("POSTED", ""));
			detailA.appendChild(node("CURRENCYNAME", entry.currencyname));
		}

		Node obe = doc.createElement("ACCOUNTLINE");
		addAttr(obe, "operation", "Add");
		trans.appendChild(obe);

		obe.appendChild(node("KeyID", 1));
		obe.appendChild(node("GLACCOUNT", AppData.obe));
		obe.appendChild(node("GLAMOUNT", totalPrimeAmount));
		obe.appendChild(node("DESCRIPTION", ""));
		obe.appendChild(node("RATE", BigDecimal.ONE));
		obe.appendChild(node("PRIMEAMOUNT", totalPrimeAmount));
		obe.appendChild(node("TXDATE", ""));
		obe.appendChild(node("POSTED", ""));
		obe.appendChild(node("CURRENCYNAME", ""));
		
		saveToFile();
	}

	private BigDecimal getTotalPrimeAmount(List<Entry> data) {
		BigDecimal result = BigDecimal.ZERO;
		for (Entry e : data) {
			result = result.add(e.glamount);
		}
		return result;
	}

	private int getTransactionId() {
		return 1;
	}

	@Override
	protected String getOutputFileName() {
		return Util.openingJvOutputFile();
	}
}
