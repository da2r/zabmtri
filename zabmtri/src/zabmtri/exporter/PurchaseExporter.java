package zabmtri.exporter;

import java.util.List;

import org.w3c.dom.Node;

import zabmtri.AppData;
import zabmtri.BaseExport;
import zabmtri.Util;
import zabmtri.entity.EApCheq;
import zabmtri.entity.EApInv;
import zabmtri.entity.EApInvChq;
import zabmtri.entity.EApInvChqDisc;
import zabmtri.entity.EApInvDet;
import zabmtri.entity.EApItmDet;
import zabmtri.entity.ESnHistory;

public class PurchaseExporter extends BaseExport {

	public void execute() {
		Node nmexml = doc.createElement("NMEXML");
		doc.appendChild(nmexml);
		addAttr(nmexml, "EximID", "1");
		addAttr(nmexml, "BranchCode", AppData.branchCode);
		addAttr(nmexml, "ACCOUNTANTCOPYID", "");

		Node transaction = doc.createElement("TRANSACTIONS");
		nmexml.appendChild(transaction);
		addAttr(transaction, "OnError", "CONTINUE");

		exportPreInvoice(transaction);
		exportInvoice(transaction);
		exportPayment(transaction);

		saveToFile();
		System.out.println("File saved!");
	}

	private void exportPreInvoice(Node transaction) {
		for (EApInv data : getPreInvoiceList()) {
			Node trans = doc.createElement("RECIEVEITEM");
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
			trans.appendChild(node("INVOICEISTAXABLE", data.invoiceistaxable));
			trans.appendChild(node("CASHDISCOUNT", data.cashdiscount));
			trans.appendChild(node("CASHDISCPC", data.cashdiscpc));
			trans.appendChild(node("INVOICEAMOUNT", data.invoiceamount));
			trans.appendChild(node("TERMSID", data.termsname));
			trans.appendChild(node("SHIPVIA", data.shipvianame));
			trans.appendChild(node("FOB", data.fob));
			trans.appendChild(node("PURCHASEORDERNO", data.purchaseorderno));
			trans.appendChild(node("WAREHOUSEID", data.warehousename));
			trans.appendChild(node("DESCRIPTION", data.description));
			trans.appendChild(node("SHIPDATE", data.shipdate));
			trans.appendChild(node("POSTED", data.posted));
			trans.appendChild(node("FISCALRATE", data.fiscalrate));
			trans.appendChild(node("INVFROMPR", data.invfrompr));
			trans.appendChild(node("TAXDATE", data.taxdate));
			trans.appendChild(node("VENDORID", data.vendorno));
			trans.appendChild(node("SEQUENCENO", data.sequenceno));
			trans.appendChild(node("APACCOUNT", data.apaccount));
			trans.appendChild(node("SHIPVENDID", data.shipvendname));
			trans.appendChild(node("INVTAXNO2", data.invtaxno2));
			trans.appendChild(node("INVTAXNO1", data.invtaxno1));
			trans.appendChild(node("SSPDATE", data.sspdate));
			trans.appendChild(node("EXPENSESOFBILLID", "")); // data.expensesofbillname
			trans.appendChild(node("EXPENSESJOURNALDATETYPE", "")); // data.expensesjournaldatetype
			trans.appendChild(node("LOCKED_BY", data.locked_by));
			trans.appendChild(node("LOCKED_TIME", data.locked_time));

			for (EApItmDet det : data.detail) {
				Node transDetail = doc.createElement("ITEMLINE");
				addAttr(transDetail, "operation", "Add");
				trans.appendChild(transDetail);

				transDetail.appendChild(node("KeyID", det.seq));
				transDetail.appendChild(node("ITEMNO", det.itemno));
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
				transDetail.appendChild(node("UNITPRICE", "")); // det.unitprice
				transDetail.appendChild(node("ITEMDISCPC", det.itemdiscpc));
				transDetail.appendChild(node("TAXCODES", det.taxcodes));
				transDetail.appendChild(node("GROUPSEQ", Util.avoidZero(det.groupseq)));
				transDetail.appendChild(node("POSEQ", Util.avoidZero(det.poseq)));
				transDetail.appendChild(node("BRUTOUNITPRICE", det.brutounitprice));
				transDetail.appendChild(node("WAREHOUSEID", det.warehousename));
				transDetail.appendChild(node("QTYCONTROL", det.qtycontrol));

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

			for (EApInvDet det : data.expense) {
				Node transDetail = doc.createElement("APINVDET");
				addAttr(transDetail, "operation", "Add");
				trans.appendChild(transDetail);

				transDetail.appendChild(node("KeyID", det.seq));
				transDetail.appendChild(node("GLACCOUNT", det.glaccount));
				transDetail.appendChild(node("GLAMOUNT", det.glamount));
				transDetail.appendChild(node("DESCRIPTION", det.description));
				transDetail.appendChild(node("APPLYTOITEM", det.alloctoitemcost));
				transDetail.appendChild(node("CHARGETOVENDOR", det.chargetovendor));
			}
		}
	}

	private void exportInvoice(Node transaction) {
		for (EApInv data : getInvoiceList()) {
			Node trans = doc.createElement("PURCHASEINVOICE");
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
			trans.appendChild(node("INVOICEISTAXABLE", data.invoiceistaxable));
			trans.appendChild(node("CASHDISCOUNT", data.cashdiscount));
			trans.appendChild(node("CASHDISCPC", data.cashdiscpc));
			trans.appendChild(node("INVOICEAMOUNT", data.invoiceamount));
			trans.appendChild(node("TERMSID", data.termsname));
			trans.appendChild(node("SHIPVIA", data.shipvianame));
			trans.appendChild(node("FOB", data.fob));
			trans.appendChild(node("PURCHASEORDERNO", data.purchaseorderno));
			trans.appendChild(node("WAREHOUSEID", data.warehousename));
			trans.appendChild(node("DESCRIPTION", data.description));
			trans.appendChild(node("SHIPDATE", data.shipdate));
			trans.appendChild(node("POSTED", data.posted));
			trans.appendChild(node("FISCALRATE", data.fiscalrate));
			trans.appendChild(node("INVFROMPR", data.invfrompr));
			trans.appendChild(node("TAXDATE", data.taxdate));
			trans.appendChild(node("VENDORID", data.vendorno));
			trans.appendChild(node("SEQUENCENO", data.sequenceno));
			trans.appendChild(node("APACCOUNT", data.apaccount));
			trans.appendChild(node("SHIPVENDID", data.shipvendname));
			trans.appendChild(node("INVTAXNO2", data.invtaxno2));
			trans.appendChild(node("INVTAXNO1", data.invtaxno1));
			trans.appendChild(node("SSPDATE", data.sspdate));
			trans.appendChild(node("EXPENSESOFBILLID", "")); // data.expensesofbillname
			trans.appendChild(node("EXPENSESJOURNALDATETYPE", "")); // data.expensesjournaldatetype
			trans.appendChild(node("LOCKED_BY", data.locked_by));
			trans.appendChild(node("LOCKED_TIME", data.locked_time));

			for (EApItmDet det : data.detail) {
				Node transDetail = doc.createElement("ITEMLINE");
				addAttr(transDetail, "operation", "Add");
				trans.appendChild(transDetail);

				transDetail.appendChild(node("KeyID", det.seq));
				transDetail.appendChild(node("ITEMNO", det.itemno));
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
				transDetail.appendChild(node("GROUPSEQ", Util.avoidZero(det.groupseq)));
				transDetail.appendChild(node("POSEQ", Util.avoidZero(det.poseq)));
				transDetail.appendChild(node("BRUTOUNITPRICE", det.brutounitprice));
				transDetail.appendChild(node("WAREHOUSEID", det.warehousename));
				transDetail.appendChild(node("QTYCONTROL", det.qtycontrol));
				transDetail.appendChild(node("RISEQ", det.riseq));
				transDetail.appendChild(node("RIID", det.rino));

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

			for (EApInvDet det : data.expense) {
				Node transDetail = doc.createElement("APINVDET");
				addAttr(transDetail, "operation", "Add");
				trans.appendChild(transDetail);

				transDetail.appendChild(node("KeyID", det.seq));
				transDetail.appendChild(node("GLACCOUNT", det.glaccount));
				transDetail.appendChild(node("GLAMOUNT", det.glamount));
				transDetail.appendChild(node("DESCRIPTION", det.description));
				transDetail.appendChild(node("APPLYTOITEM", det.alloctoitemcost));
				transDetail.appendChild(node("CHARGETOVENDOR", det.chargetovendor));
			}
		}
	}

	private void exportPayment(Node transaction) {
		for (EApCheq data : getPaymentList()) {
			Node trans = doc.createElement("VENDORPAYMENT");
			addAttr(trans, "operation", "Add");
			addAttr(trans, "REQUESTID", "1");
			transaction.appendChild(trans);

			trans.appendChild(node("TRANSACTIONID", data.transactionid));
			trans.appendChild(node("IMPORTEDTRANSACTIONID", data.importedtransactionid));
			trans.appendChild(node("SEQUENCENO", data.sequenceno));
			trans.appendChild(node("PAYMENTDATE", data.paymentdate));
			trans.appendChild(node("CHEQUENO", data.chequeno));
			trans.appendChild(node("BANKACCNT", data.bankaccnt));
			trans.appendChild(node("CHEQUEDATE", data.chequedate));
			trans.appendChild(node("RATE", data.rate));
			trans.appendChild(node("DESCRIPTION", data.description));
			trans.appendChild(node("FISCALPMT", data.fiscalpmt));
			trans.appendChild(node("VOID", data.isVoid));
			trans.appendChild(node("VENDORID", data.vendorno));
			trans.appendChild(node("PAYEE", data.payee));

			for (EApInvChq det : data.detail) {
				Node transDetail = doc.createElement("InvoiceLine");
				addAttr(transDetail, "operation", "Add");
				trans.appendChild(transDetail);

				transDetail.appendChild(node("KeyID", det.seq));
				transDetail.appendChild(node("PAYMENTAMOUNT", det.paymentamount));
				transDetail.appendChild(node("PPH23AMOUNT", det.pph23amount));
				transDetail.appendChild(node("PPH23RATE", det.pph23rate));
				transDetail.appendChild(node("PPH23FISCALRATE", det.pph23fiscalrate));
				transDetail.appendChild(node("PPH23NUMBER", det.pph23number));
				transDetail.appendChild(node("DISCOUNT", det.discount));
				transDetail.appendChild(node("APINVOICEID", det.apinvoiceno));
				transDetail.appendChild(node("APINVOICESEQUENCE", null));
				
				for (EApInvChqDisc disc : det.writeoff) {
					Node transDetailDiscount = doc.createElement("DiscountLine");
					addAttr(transDetailDiscount, "operation", "Ret");
					transDetail.appendChild(transDetailDiscount);
					
					transDetailDiscount.appendChild(node("KeyID", disc.seq));
					transDetailDiscount.appendChild(node("DISCOUNT", disc.discount));
					transDetailDiscount.appendChild(node("DISCACCOUNT", disc.discaccount));
				}
			}
		}
	}

	protected List<EApInv> getPreInvoiceList() {
		return AppData.apRec;
	}

	protected List<EApInv> getInvoiceList() {
		return AppData.apInv;
	}

	protected List<EApCheq> getPaymentList() {
		return AppData.apCheq;
	}

	@Override
	protected String getOutputFileName() {
		return Util.purchaseOutputFile();
	}
}
