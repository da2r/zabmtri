package zabmtri;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import zabmtri.entity.EApCheq;
import zabmtri.entity.EApInv;
import zabmtri.entity.EArInv;
import zabmtri.entity.EArPmt;
import zabmtri.entity.ECustomer;
import zabmtri.entity.EFaFiscal;
import zabmtri.entity.EFaSetTyp;
import zabmtri.entity.EFixAsset;
import zabmtri.entity.EGlAccount;
import zabmtri.entity.EItem;
import zabmtri.entity.EItemCategory;
import zabmtri.entity.EItemSn;
import zabmtri.entity.EJv;
import zabmtri.entity.EOwingPi;
import zabmtri.entity.EOwingSi;
import zabmtri.entity.ESalesman;
import zabmtri.entity.EVendor;
import zabmtri.entity.EWarehs;

public class AppData {

	public static LocalDate dateCutOff = LocalDate.of(2017, 12, 31);

	public static String alphaPath;
	public static String betaPath;
	public static String targetPath;

	public static Connection alpha;
	public static Connection beta;
	public static Connection target;

	public static List<EGlAccount> alphaGlAccount;
	public static List<EGlAccount> betaGlAccount;
	public static List<EGlAccount> glAccount;

	public static List<EItem> alphaItem;
	public static List<EItem> betaItem;
	public static List<EItem> item;

	public static List<EItemCategory> alphaItemCategory;
	public static List<EItemCategory> betaItemCategory;

	public static List<EItemSn> itemSn;
	
	public static List<EItemSn> itemSnUsed;
	public static List<EItemSn> itemSnVacant;
	public static List<ItemQty> itemWithoutSn;

	public static List<EWarehs> alphaWarehs;
	public static List<EWarehs> betaWarehs;
	public static List<EWarehs> warehs;

	public static List<ESalesman> alphaSalesman;
	public static List<ESalesman> betaSalesman;
	public static List<ESalesman> salesman;

	public static List<ECustomer> alphaCustomer;
	public static List<ECustomer> betaCustomer;
	public static List<ECustomer> customer;

	public static List<EVendor> alphaVendor;
	public static List<EVendor> betaVendor;
	public static List<EVendor> vendor;

	public static List<EOwingSi> owingSi;
	public static List<EOwingPi> owingPi;

	public static List<EJv> alphaJv;
	public static List<EJv> alphaOp;
	public static List<EJv> alphaOd;

	public static List<EArInv> arDel;
	public static List<EArInv> arInv;
	public static List<EArPmt> arPmt;

	public static List<EApInv> apRec;
	public static List<EApInv> apInv;
	public static List<EApCheq> apCheq;

	public static List<EFaFiscal> faFiscal;
	public static List<EFaSetTyp> faSetTyp;
	public static List<EFixAsset> fixAsset;

	public static IMainForm mainForm;

	public static String branchCode;
	public static String obe;

	public static String getAlphaCurrencyName(String glaccount) {
		for (EGlAccount account : alphaGlAccount) {
			if (account.glaccount.equals(glaccount)) {
				return account.currencyname;
			}
		}

		throw new RuntimeException("Cannot find currency for glaccount " + glaccount);
	}

	public static List<EOwingSi> getOwingSi(Integer customerid) {
		List<EOwingSi> result = new ArrayList<EOwingSi>();
		for (EOwingSi ow : owingSi) {
			if (ow.customerid.equals(customerid)) {
				result.add(ow);
			}
		}

		return result;
	}

	public static List<EOwingPi> getOwingPi(Integer vendorid) {
		List<EOwingPi> result = new ArrayList<EOwingPi>();
		for (EOwingPi ow : owingPi) {
			if (ow.vendorid.equals(vendorid)) {
				result.add(ow);
			}
		}

		return result;
	}

	public static String getFaFiscalDesc(Integer afistypeid) {
		for (EFaFiscal fis : faFiscal) {
			if (fis.assetfistype.equals(afistypeid)) {
				return fis.fafisdesc;
			}
		}
		return "" + afistypeid;
	}

	public static String getFaSetTypDesc(Integer assettype) {
		for (EFaSetTyp fis : faSetTyp) {
			if (fis.afistypeid.equals(assettype)) {
				return fis.afistypedesc;
			}
		}
		return "" + assettype;
	}

	public static EItem getItem(String itemno) {
		if (item == null || Util.isBlank(itemno)) {
			return null;
		}
		
		for (EItem result : item) {
			if (result.itemno.equals(itemno)) {
				return result;
			}
		}
		
		return null;
	}

}
