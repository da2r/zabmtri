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
import zabmtri.entity.ECustomer;

public class CustomerExporter {

	public void execute() {
		try {
			Path path = Paths.get(getOutputFileName());
			BufferedWriter writer = Files.newBufferedWriter(path);
			CSVPrinter csvPrinter = new CSVPrinter(writer, getHeader());
			try {
				for (ECustomer data : AppData.alphaCustomer) {
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
		header.add("No. Pelanggan");
		header.add("Nama Pelanggan");
		header.add("Alamat 1");
		header.add("Alamat 2");
		header.add("Alamat Pajak 1");
		header.add("Alamat Pajak 2");
		header.add("Kota");
		header.add("Propinsi");
		header.add("Kode Pos");
		header.add("Negara");
		header.add("No. Telepon");
		header.add("No. Faksimili");
		header.add("Nama kontak");
		header.add("Email");
		header.add("Halaman WEB");
		header.add("Pajak pertama");
		header.add("Pajak kedua");
		header.add("Nomor Pajak");
		header.add("NPPKP");
		header.add("Tipe Pajak");
		header.add("Termasuk Pajak");
		header.add("Default Diskon");
		header.add("Batas Piutang");
		header.add("Limit Days");
		header.add("Tingkatan Harga Jual");
		header.add("Saldo Awal");
		header.add("Mata Uang");
		header.add("Syarat");
		header.add("Penjual");
		header.add("Tipe Pelanggan");
		header.add("Pesan Pernyataan");
		header.add("Catatan");
		header.add("Saldo per Tgl");
		header.add("No Faktur Saldo Awal");

		// No. Pelanggan = personno
		// Nama Pelanggan = personname
		// Alamat 1 = addressline1
		// Alamat 2 = addressline2
		// Alamat Pajak 1 = TaxAddressLine1
		// Alamat Pajak 2 = TaxAddressLine2
		// Kota = City
		// Propinsi = StateProv
		// Kode Pos = ZipCode
		// Negara = Country
		// No. Telepon = Phone
		// No. Faksimili = Fax
		// Nama kontak = Contact
		// Email = Email
		// Halaman WEB = webpage
		// Pajak pertama = tax1code
		// Pajak kedua = tax2code
		// Nomor Pajak = taxnumber
		// NPPKP = Tax2ExemptionNo
		// Tipe Pajak = TaxType
		// Termasuk Pajak = DefInclusiveTax
		// Default Diskon = DefaultDisc
		// Batas Piutang = CreditLimit
		// Limit Days = LimitDays
		// Tingkatan Harga Jual = PriceLevel
		// Saldo Awal = ???
		// Mata Uang = currencyname
		// Syarat = termname
		// Penjual = salesmanname
		// Tipe Pelanggan = customertypename
		// Pesan Pernyataan = StatementMsg
		// Catatan = notes
		// Saldo per Tgl = ???
		// No Faktur Saldo Awal = ???

		String[] arr = header.toArray(new String[header.size()]);
		return CSVFormat.DEFAULT.withHeader(arr);
	}

	private Iterable<?> getRow(ECustomer data) {
		List<Object> result = new ArrayList<Object>();
		result.add(data.personno);
		result.add(data.name);
		result.add(data.addressline1);
		result.add(data.addressline2);
		result.add(data.taxaddress1);
		result.add(data.taxaddress2);
		result.add(data.city);
		result.add(data.stateprov);
		result.add(data.zipcode);
		result.add(data.country);
		result.add(data.phone);
		result.add(data.fax);
		result.add(data.contact);
		result.add(data.email);
		result.add(data.webpage);
		result.add(data.tax1name);
		result.add(data.tax2name);
		result.add(data.tax1exemptionno);
		result.add(data.tax2exemptionno);
		result.add(data.taxtypename);
		result.add(Util.booleanText(data.defaultinclusivetax));
		result.add(data.defaultdisc);
		result.add(data.creditlimit);
		result.add(data.creditlimitdays);
		result.add(data.pricelevel);
		result.add(0); // result.add(data.openbal);
		result.add(data.currencyname);
		result.add(data.termname);
		result.add(data.salesmanname);
		result.add(data.customertypename);
		result.add(Util.booleanText(data.printstatement));
		result.add(data.notes);
		result.add(Util.formatDateCsv(AppData.dateCutOff));
		result.add(""); // result.add(data.invoiceob);

		return result;
	}

	private String getOutputFileName() {
		return Util.customerOutputFile();
	}
}
