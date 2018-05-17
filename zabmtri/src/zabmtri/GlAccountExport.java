package zabmtri;

import org.w3c.dom.Node;

import zabmtri.entity.EGlAccount;

@Deprecated
public class GlAccountExport extends BaseExport {

	public void execute() {
		Node nmexml = doc.createElement("NMEXML");
		doc.appendChild(nmexml);
		addAttr(nmexml, "EximID", "");
		addAttr(nmexml, "BranchCode", AppData.branchCode);
		addAttr(nmexml, "ACCOUNTANTCOPYID", "");

		Node transaction = doc.createElement("TRANSACTIONS");
		nmexml.appendChild(transaction);
		addAttr(transaction, "OnError", "CONTINUE");

		for (EGlAccount data : AppData.alphaGlAccount) {
			Node account = doc.createElement("ACCOUNT");
			addAttr(account, "operation", "Add");
			addAttr(account, "REQUESTID", "1");
			transaction.appendChild(account);

			account.appendChild(node("TRANSACTIONID", data.transactionid));
			account.appendChild(node("GLACCOUNT", data.glaccount));
			account.appendChild(node("ACCOUNTNAME", data.accountname));
			account.appendChild(node("ACCOUNTTYPE", asAccountTypeName(data.accounttype)));
			account.appendChild(node("SUBACCOUNT", data.subaccount));
			account.appendChild(node("PARENTACCOUNTID", data.parentaccount));
			account.appendChild(node("SUSPENDED", data.suspended));
			account.appendChild(node("MEMO", data.memo));
			account.appendChild(node("FIRSTPARENTACCOUNTID", data.firstparentaccount));
			account.appendChild(node("ISFISCAL", data.isfiscal));
		}

		// <TRANSACTIONS OnError="CONTINUE">
		// <ACCOUNT operation="Add" REQUESTID="1">
		// <TRANSACTIONID>822</TRANSACTIONID>
		// <GLACCOUNT>2105-007</GLACCOUNT>
		// <ACCOUNTNAME>job costing account</ACCOUNTNAME>
		// <ACCOUNTTYPE>Inventory</ACCOUNTTYPE>
		// <SUBACCOUNT>0</SUBACCOUNT>
		// <PARENTACCOUNTID/>
		// <SUSPENDED>0</SUSPENDED>
		// <MEMO/>
		// <FIRSTPARENTACCOUNTID>2105-007</FIRSTPARENTACCOUNTID>
		// <ISFISCAL>1</ISFISCAL>
		// </ACCOUNT>

		saveToFile();
		System.out.println("File saved!");
	}

	private String asAccountTypeName(Integer accounttype) {
		if (accounttype == null) {
			throw new RuntimeException("Error accounttype is null");
		}
		
		// AccountTypeStr : array [6..21] of String =
		// (OTHER_ASSET_ACCTYPE, CASHBANK_ACCTYPE, AR_ACCTYPE, INV_ACCTYPE,
		// OTHASS_ACCTYPE,
		// FIXASS_ACCTYPE, ACCDEP_ACCTYPE, AP_ACCTYPE, OTHLIA_ACCTYPE,
		// LTERMLIA_ACCTYPE, EQUITY_ACCTYPE, REVENUE_ACCTYPE,
		// COGS_ACCTYPE, EXP_ACCTYPE, OTHEXP_ACCTYPE, OTHINC_ACCTYPE);

		String CASHBANK_ACCTYPE = "Cash/Bank";
		String AR_ACCTYPE = "Account Receivable";
		String INV_ACCTYPE = "Inventory";
		String OTHASS_ACCTYPE = "Other Current Asset";
		String FIXASS_ACCTYPE = "Fixed Asset";
		String ACCDEP_ACCTYPE = "Accumulated Depreciation";
		String OTHER_ASSET_ACCTYPE = "Other Asset";
		String AP_ACCTYPE = "Account Payable";
		String OTHLIA_ACCTYPE = "Other Current Liability";
		String LTERMLIA_ACCTYPE = "Long Term Liability";
		String EQUITY_ACCTYPE = "Equity";
		String REVENUE_ACCTYPE = "Revenue";
		String COGS_ACCTYPE = "COGS";
		String EXP_ACCTYPE = "Expense";
		String OTHEXP_ACCTYPE = "Other Expense";
		String OTHINC_ACCTYPE = "Other Income";

		final String[] ACCTYPE = new String[] { OTHER_ASSET_ACCTYPE, CASHBANK_ACCTYPE, AR_ACCTYPE, INV_ACCTYPE, OTHASS_ACCTYPE, FIXASS_ACCTYPE, ACCDEP_ACCTYPE, AP_ACCTYPE, OTHLIA_ACCTYPE,
				LTERMLIA_ACCTYPE, EQUITY_ACCTYPE, REVENUE_ACCTYPE, COGS_ACCTYPE, EXP_ACCTYPE, OTHEXP_ACCTYPE, OTHINC_ACCTYPE };

		return ACCTYPE[accounttype - 6];
	}

	@Override
	protected String getOutputFileName() {
		return "output-glaccount.xml";
	}

}
