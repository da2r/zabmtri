package zabmtri.exporter;

import java.util.List;

import org.w3c.dom.Node;

import zabmtri.AppData;
import zabmtri.BaseExport;
import zabmtri.Util;
import zabmtri.entity.EJv;
import zabmtri.entity.EJvDet;

public class JvExporter extends BaseExport {

	public void execute() {
		Node nmexml = doc.createElement("NMEXML");
		doc.appendChild(nmexml);
		addAttr(nmexml, "EximID", "");
		addAttr(nmexml, "BranchCode", AppData.branchCode);
		addAttr(nmexml, "ACCOUNTANTCOPYID", "");

		Node transaction = doc.createElement("TRANSACTIONS");
		nmexml.appendChild(transaction);
		addAttr(transaction, "OnError", "CONTINUE");

		for (EJv data : getDataList()) {
			Node trans = doc.createElement(getTransKey());
			addAttr(trans, "operation", "Add");
			addAttr(trans, "REQUESTID", "1");
			transaction.appendChild(trans);

			trans.appendChild(node("TRANSACTIONID", data.transactionid));
			trans.appendChild(node("JVNUMBER", data.jvnumber));
			trans.appendChild(node("TRANSDATE", data.transdate));
			trans.appendChild(node("SOURCE", data.source));
			trans.appendChild(node("TRANSTYPE", getTransType(data.transtype)));
			trans.appendChild(node("TRANSDESCRIPTION", data.transdescription));
			trans.appendChild(node("JVAMOUNT", data.jvamount));

			for (EJvDet det : data.detail) {
				Node transDetail = doc.createElement("ACCOUNTLINE");
				addAttr(transDetail, "operation", "Add");
				trans.appendChild(transDetail);

				transDetail.appendChild(node("KeyID", det.seq));
				transDetail.appendChild(node("GLACCOUNT", det.glaccount));
				transDetail.appendChild(node("GLAMOUNT", det.glamount));
				transDetail.appendChild(node("DESCRIPTION", det.description));
				transDetail.appendChild(node("RATE", det.rate));
				transDetail.appendChild(node("PRIMEAMOUNT", det.primeamount));
				transDetail.appendChild(node("TXDATE", det.txdate));
				transDetail.appendChild(node("POSTED", det.posted));
				transDetail.appendChild(node("CURRENCYNAME", AppData.getBetaCurrencyName(det.glaccount)));
			}
		}

		saveToFile();
		System.out.println("File saved!");
	}

	private String getTransType(String transtype) {
		final String tx_OtherPayment = "PMT";
		final String tx_OtherDeposit = "DPT";
		final String tx_PeriodEnd = "UGL";
		final String tx_Depreciation = "DPR";
		final String tx_RollOver = "RO";
		final String tx_PeriodEndMfg = "MFG";

		final String c_sJournalVoucher = "journal voucher";
		final String c_sOtherPayment = "other payment";
		final String c_sOtherDeposit = "other deposit";
		final String c_sPeriodEnd = "period end";
		final String c_sDepreciation = "depreciation";
		final String c_sRollOver = "rollover";
		final String c_sPeriodEndMfg = "period end manufacture";

		if (transtype.equals(tx_OtherPayment)) {
			return c_sOtherPayment;
		} else if (transtype.equals(tx_OtherDeposit)) {
			return c_sOtherDeposit;
		} else if (transtype.equals(tx_PeriodEnd)) {
			return c_sPeriodEnd;
		} else if (transtype.equals(tx_Depreciation)) {
			return c_sDepreciation;
		} else if (transtype.equals(tx_RollOver)) {
			return c_sRollOver;
		} else if (transtype.equals(tx_PeriodEndMfg)) {
			return c_sPeriodEndMfg;
		}

		return c_sJournalVoucher;
	}
	
	protected List<EJv> getDataList() {
		return AppData.betaJv;
	}

	protected String getTransKey() {
		return "JV";
	}

	@Override
	protected String getOutputFileName() {
		return Util.jvOutputFile();
	}
}
