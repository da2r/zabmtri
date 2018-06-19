package zabmtri.exporter;

import java.util.List;

import org.w3c.dom.Node;

import zabmtri.AppData;
import zabmtri.BaseExport;
import zabmtri.Util;
import zabmtri.entity.EArInv;
import zabmtri.entity.EArInvDet;
import zabmtri.entity.EArInvPmt;
import zabmtri.entity.EArPmt;
import zabmtri.entity.ESnHistory;

public class SalesExporter extends BaseExport {

	public void execute() {
		Node nmexml = doc.createElement("NMEXML");
		doc.appendChild(nmexml);
		addAttr(nmexml, "EximID", "");
		addAttr(nmexml, "BranchCode", AppData.branchCode);
		addAttr(nmexml, "ACCOUNTANTCOPYID", "");

		Node transaction = doc.createElement("TRANSACTIONS");
		nmexml.appendChild(transaction);
		addAttr(transaction, "OnError", "CONTINUE");

		exportArInv(transaction);
		exportArPmt(transaction);

		saveToFile();
		System.out.println("File saved!");
	}

	private void exportArInv(Node transaction) {
		for (EArInv data : getInvoiceList()) {
			Node trans = doc.createElement("SALESINVOICE");
			addAttr(trans, "operation", "Add");
			addAttr(trans, "REQUESTID", "1");
			transaction.appendChild(trans);

			trans.appendChild(node("TRANSACTIONID", data.transactionid));
			trans.appendChild(node("INVOICENO", data.invoiceno));
			trans.appendChild(node("INVOICEDATE", data.invoicedate));
			trans.appendChild(node("TAX1CODE", data.tax1code));
			trans.appendChild(node("TAX2CODE", data.tax2code));
			trans.appendChild(node("TAX1RATE", data.tax1rate));
			trans.appendChild(node("TAX2RATE", data.tax2rate));
			trans.appendChild(node("RATE", data.rate));
			trans.appendChild(node("INCLUSIVETAX", data.inclusivetax));
			trans.appendChild(node("CUSTOMERISTAXABLE", data.customeristaxable));
			trans.appendChild(node("CASHDISCOUNT", data.cashdiscount));
			trans.appendChild(node("CASHDISCPC", data.cashdiscpc));
			trans.appendChild(node("INVOICEAMOUNT", data.invoiceamount));
			trans.appendChild(node("FREIGHT", data.freight));
			trans.appendChild(node("TERMSID", data.termsname));
			trans.appendChild(node("SHIPVIA", data.shipvianame));
			trans.appendChild(node("FOB", data.fob));
			trans.appendChild(node("PURCHASEORDERNO", data.purchaseorderno));
			trans.appendChild(node("WAREHOUSEID", data.warehousename));
			trans.appendChild(node("DESCRIPTION", data.description));
			trans.appendChild(node("SHIPDATE", data.shipdate));
			trans.appendChild(node("DELIVERYORDER", data.deliveryorder));
			trans.appendChild(node("FISCALRATE", data.fiscalrate));
			trans.appendChild(node("TAXDATE", data.taxdate));
			trans.appendChild(node("CUSTOMERID", data.customerno));
			trans.appendChild(node("PRINTED", data.printed));
			trans.appendChild(node("SHIPTO1", data.shipto1));
			trans.appendChild(node("SHIPTO2", data.shipto2));
			trans.appendChild(node("SHIPTO3", data.shipto3));
			trans.appendChild(node("SHIPTO4", data.shipto4));
			trans.appendChild(node("SHIPTO5", data.shipto5));
			trans.appendChild(node("ARACCOUNT", data.araccount));
			trans.appendChild(node("TAXFORMNUMBER", data.taxformnumber));
			trans.appendChild(node("TAXFORMCODE", data.taxformcode));
			trans.appendChild(node("CURRENCYNAME", data.currencyname));

			for (EArInvDet det : data.detail) {
				Node transDetail = doc.createElement("ITEMLINE");
				addAttr(transDetail, "operation", "Add");
				trans.appendChild(transDetail);

				transDetail.appendChild(node("KeyID", det.seq));
				transDetail.appendChild(node("QUANTITY", det.quantity));
				transDetail.appendChild(node("ITEMUNIT", det.itemunit));
				transDetail.appendChild(node("UNITRATIO", det.unitratio));
				transDetail.appendChild(node("ITEMRESERVED1", det.itemreserved1));
				transDetail.appendChild(node("ITEMRESERVED2", det.itemreserved2));
				transDetail.appendChild(node("ITEMRESERVED3", det.itemreserved3));
				transDetail.appendChild(node("ITEMRESERVED4", det.itemreserved4));
				transDetail.appendChild(node("ITEMRESERVED5", det.itemreserved5));
				transDetail.appendChild(node("ITEMRESERVED6", det.itemreserved6));
				transDetail.appendChild(node("ITEMRESERVED7", det.itemreserved7));
				transDetail.appendChild(node("ITEMRESERVED8", det.itemreserved8));
				transDetail.appendChild(node("ITEMRESERVED9", det.itemreserved9));
				transDetail.appendChild(node("ITEMRESERVED10", det.itemreserved10));
				transDetail.appendChild(node("ITEMOVDESC", det.itemovdesc));
				transDetail.appendChild(node("UNITPRICE", det.unitprice));
				transDetail.appendChild(node("ITEMDISCPC", det.itemdiscpc));
				transDetail.appendChild(node("TAXCODES", det.taxcodes));
				transDetail.appendChild(node("GROUPSEQ", det.groupseq));
				transDetail.appendChild(node("SOSEQ", det.soseq));
				transDetail.appendChild(node("BRUTOUNITPRICE", det.brutounitprice));
				transDetail.appendChild(node("WAREHOUSEID", det.warehousename));
				transDetail.appendChild(node("QTYCONTROL", det.qtycontrol));
				transDetail.appendChild(node("DOSEQ", det.doseq));
				transDetail.appendChild(node("DOID", det.dono));

				for (ESnHistory snhist : det.snhistory) {
					Node transDetailSn = doc.createElement("SNHISTORY");
					addAttr(transDetail, "operation", "Add");
					transDetail.appendChild(transDetailSn);

					transDetailSn.appendChild(node("SERIALNUMBER", snhist.serialnumber));
					transDetailSn.appendChild(node("EXPIREDDATE", snhist.expireddate));
					transDetailSn.appendChild(node("QUANTITY", snhist.quantity));
					transDetailSn.appendChild(node("SNSIGN", snhist.snsign));
				}
			}
		}
	}

	private void exportArPmt(Node transaction) {
		for (EArPmt data : getPaymentList()) {
			Node trans = doc.createElement("CUSTOMERRECEIPT");
			addAttr(trans, "operation", "Add");
			addAttr(trans, "REQUESTID", "1");
			transaction.appendChild(trans);

			trans.appendChild(node("TRANSACTIONID", data.transactionid));
			trans.appendChild(node("IMPORTEDTRANSACTIONID", data.importedtransactionid));
			trans.appendChild(node("SEQUENCENO", data.sequenceno));
			trans.appendChild(node("PAYMENTDATE", data.paymentdate));
			trans.appendChild(node("CHEQUENO", data.chequeno));
			trans.appendChild(node("BANKACCOUNT", data.bankaccount));
			trans.appendChild(node("CHEQUEDATE", data.chequedate));
			trans.appendChild(node("CHEQUEAMOUNT", data.chequeamount));
			trans.appendChild(node("RATE", data.rate));
			trans.appendChild(node("DESCRIPTION", data.description));
			trans.appendChild(node("FISCALPMT", data.fiscalpmt));
			trans.appendChild(node("VOID", data.isVoid));
			trans.appendChild(node("BILLTOID", data.billtoid));
			trans.appendChild(node("OVERPAYUSED", data.overpayused));
			trans.appendChild(node("APPLYFROMCREDIT", data.applyfromcredit));
			trans.appendChild(node("CURRENCYNAME", data.currencyname));
			trans.appendChild(node("RETURNCREDIT", data.returncredit));

			for (EArInvPmt det : data.detail) {
				Node transDetail = doc.createElement("InvoiceLine");
				addAttr(transDetail, "operation", "Add");
				trans.appendChild(transDetail);

				// <KeyID>1</KeyID>
				// <PAYMENTAMOUNT>690000</PAYMENTAMOUNT>
				// <PPH23AMOUNT>0</PPH23AMOUNT>
				// <PPH23RATE>0</PPH23RATE>
				// <PPH23FISCALRATE>1</PPH23FISCALRATE>
				// <PPH23NUMBER/>
				// <DISCTAKENAMOUNT>0</DISCTAKENAMOUNT>
				// <ARINVOICEID>1000</ARINVOICEID>

				transDetail.appendChild(node("KeyID", det.seq));
				transDetail.appendChild(node("PAYMENTAMOUNT", det.paymentamount));
				transDetail.appendChild(node("PPH23AMOUNT", det.pph23amount));
				transDetail.appendChild(node("PPH23RATE", det.pph23rate));
				transDetail.appendChild(node("PPH23FISCALRATE", det.pph23fiscalrate));
				transDetail.appendChild(node("PPH23NUMBER", det.pph23number));
				transDetail.appendChild(node("DISCTAKENAMOUNT", det.disctakenamount));
				transDetail.appendChild(node("ARINVOICEID", det.arinvoiceno));
			}
		}
	}

	protected List<EArInv> getInvoiceList() {
		return AppData.arInv;
	}

	protected List<EArPmt> getPaymentList() {
		return AppData.arPmt;
	}

	@Override
	protected String getOutputFileName() {
		return Util.salesOutputFile();
	}
}
