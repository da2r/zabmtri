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
import zabmtri.entity.EItem;
import zabmtri.entity.EItemWhQuantity;

public class ItemExporter {

	public void execute() {
		try {
			Path path = Paths.get(getOutputFileName());
			BufferedWriter writer = Files.newBufferedWriter(path);
			CSVPrinter csvPrinter = new CSVPrinter(writer, getHeader());
			try {
				for (EItem data : AppData.item) {
					for (EItemWhQuantity wqty : data.whQuantity) {
						csvPrinter.printRecord(getRow(data, wqty));
					}
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
		header.add("No. Barang");
		header.add("Deskripsi Barang");
		header.add("Tipe Barang");
		header.add("Kategori Barang");
		header.add("Kuantitas");
		header.add("Total Biaya");
		header.add("Gudang");
		header.add("Per Tgl.");
		header.add("Unit 1");
		header.add("Unit 2");
		header.add("Unit 3");
		header.add("Rasio Unit 2");
		header.add("Rasio Unit 3");
		header.add("Satuan Kontrol Qty");
		header.add("Def. Harga Jual 1");
		header.add("Def. Harga Jual 2");
		header.add("Def. Harga Jual 3");
		header.add("Def. Harga Jual 4");
		header.add("Def. Harga Jual 5");
		header.add("Diskon");
		header.add("Kode Pajak Penjualan");
		header.add("Kts Minimum");
		header.add("Pemasok Utama");
		header.add("Kode Pajak Pembelian");
		header.add("Akun Persediaan/Beban");
		header.add("Akun Penjualan");
		header.add("Akun Retur Penjualan");
		header.add("Diskon Barang Penjualan");
		header.add("Akun HPP");
		header.add("Akun Return Pembelian");
		header.add("Akun Belum Tertagih");
		header.add("Goods In Transit Account");
//		header.add("Kolom Cadangan 1");
//		header.add("Kolom Cadangan 2");
//		header.add("Kolom Cadangan 3");
//		header.add("Kolom Cadangan 4");
//		header.add("Kolom Cadangan 5");
//		header.add("Kolom Cadangan 6");
//		header.add("Kolom Cadangan 7");
//		header.add("Kolom Cadangan 8");
//		header.add("Kolom Cadangan 9");
//		header.add("Kolom Cadangan 10");
		header.add("Catatan");
		header.add("Barang Induk");
		header.add("Pakai No Seri");
		header.add("Paksa No Seri");
		header.add("Kirim meski stok kosong");
		header.add("Tipe Seri/Batch");
		header.add("Pakai Kadaluarsa");
		header.add("Berat");
		header.add("Lama Pengiriman");
		header.add("Tinggi");
		header.add("Lebar");
		header.add("Panjang");

		// No. Barang = itemno
		// Deskripsi Barang = itemdescription
		// Tipe Barang = itemtype
		// Tipe Persediaan = INVENTORYGROUP
		// Kategori Barang = categoryid
		// Kuantitas = 12 ???
		// Total Biaya = 9540000 ???
		// Kontrol Qty = ???
		// Gudang = ??? WAREHOUSEID ?
		// Per Tgl. = ???
		// Unit 1 = unit1
		// Unit 2 = unit2
		// Unit 3 = unit3
		// Rasio Unit 2 = ratio2
		// Rasio Unit 3 = ratio3
		// Satuan Kontrol Qty = unitcontrol
		// Def. Harga Jual 1 = UNITPRICE
		// Def. Harga Jual 2 = UNITPRICE2
		// Def. Harga Jual 3 = UNITPRICE3
		// Def. Harga Jual 4 = UNITPRICE4
		// Def. Harga Jual 5 = UNITPRICE5
		// Diskon = discpc
		// Kode Pajak Penjualan = TAXCODES
		// Kts Minimum = MINIMUMQTY
		// Pemasok Utama = PREFEREDVENDOR
		// Kode Pajak Pembelian = PTAXCODES
		// Akun Persediaan/Beban = INVENTORYGLACCNT
		// Akun Penjualan = SALESGLACCNT
		// Akun Retur Penjualan = SALESRETGLACCNT
		// Diskon Barang Penjualan = SALESDISCOUNTACCNT
		// Akun HPP = COGSGLACCNT
		// Akun Return Pembelian = PURCHASERETGLACCNT
		// Akun Belum Tertagih = GOODSTRANSITACCNT
		// Goods In Transit Account
		// Kolom Cadangan 1 = RESERVED1
		// Kolom Cadangan 2 = RESERVED2
		// Kolom Cadangan 3 = RESERVED3
		// Kolom Cadangan 4 = RESERVED4
		// Kolom Cadangan 5 = RESERVED5
		// Kolom Cadangan 6 = RESERVED6
		// Kolom Cadangan 7 = RESERVED7
		// Kolom Cadangan 8 = RESERVED8
		// Kolom Cadangan 9 = RESERVED9
		// Kolom Cadangan 10 = RESERVED10
		// Catatan = NOTES
		// Barang Induk = PARENTITEM / FIRSTPARENTITEM
		// Pakai No Seri = MANAGESN
		// Paksa No Seri = FORCESN
		// Kirim meski stok kosong = DELIVERNOSTOCKSN
		// Tipe Seri/Batch = SERIALNUMBERTYPE (Unik / Produksi)
		// Pakai Kadaluarsa = MANAGEEXPIRED
		// Def. Nama Dept = DEPTID
		// Def. Nama Proyek = PROJECTID
		// Berat = WEIGHT
		// Lama Pengiriman = DELIVERYLEADTIME
		// Tinggi = DIMHEIGHT
		// Lebar = DIMWIDTH
		// Panjang = DIMDEPTH

		String[] arr = header.toArray(new String[header.size()]);
		return CSVFormat.DEFAULT.withHeader(arr);
	}

	private Iterable<?> getRow(EItem data, EItemWhQuantity wqty) {
		List<Object> result = new ArrayList<Object>();

		result.add(data.itemno);
		result.add(Util.removeNewLine(data.itemdescription));
		result.add(asItemTypeName(data.itemtype));
		result.add(data.categoryname);
		if (wqty != null) {
			result.add(Util.formatNumber(wqty.quantity));
			result.add(Util.formatNumber(wqty.cost));
			result.add(wqty.warehousename);
		} else {
			result.add("0");
			result.add("0");
			result.add("CENTRE");
		}
		result.add(Util.formatDateCsv(AppData.dateCutOff));
		result.add(data.unit1);
		result.add(data.unit2);
		result.add(data.unit3);
		result.add(Util.formatNumber(data.ratio2));
		result.add(Util.formatNumber(data.ratio3));
		result.add(data.unitcontrol);
		result.add(Util.formatNumber(data.unitprice));
		result.add(Util.formatNumber(data.unitprice2));
		result.add(Util.formatNumber(data.unitprice3));
		result.add(Util.formatNumber(data.unitprice4));
		result.add(Util.formatNumber(data.unitprice5));
		result.add(data.discpc);
		result.add(data.taxcodes);
		result.add(Util.formatNumber(data.minimumqty));
		result.add(data.preferedvendorname);
		result.add(data.ptaxcodes);
		result.add(data.inventoryglaccnt);
		result.add(data.salesglaccnt);
		result.add(data.salesretglaccnt);
		result.add(data.salesdiscountaccnt);
		result.add(data.cogsglaccnt);
		result.add(data.purchaseretglaccnt);
		result.add(data.unbilledaccount);
		result.add(data.goodstransitaccnt);
//		result.add(data.reserved1);
//		result.add(data.reserved2);
//		result.add(data.reserved3);
//		result.add(data.reserved4);
//		result.add(data.reserved5);
//		result.add(data.reserved6);
//		result.add(data.reserved7);
//		result.add(data.reserved8);
//		result.add(data.reserved9);
//		result.add(data.reserved10);
		result.add(Util.escapeNewLine(data.notes));
		result.add(data.parentitem);
		result.add(Util.booleanText(data.managesn));
		result.add(Util.booleanText(false)); //data.forcesn
		result.add(Util.booleanText(false)); // data.delivernostocksn
		result.add(asSerialNumberTypeName(data.serialnumbertype));
		result.add(Util.booleanText(data.manageexpired));
		result.add(Util.formatNumber(data.weight));
		result.add(Util.formatNumber(data.deliveryleadtime));
		result.add(Util.formatNumber(data.dimheight));
		result.add(Util.formatNumber(data.dimwidth));
		result.add(Util.formatNumber(data.dimdepth));

		return result;
	}

	private String asSerialNumberTypeName(Integer serialnumbertype) {
		switch (serialnumbertype) {
		case 1:
			return "Unik";
		case 2:
			return "Produksi";
		default:
			return null;
		}
	}

	private String asItemTypeName(Integer itemtype) {
		if (itemtype == null) {
			return "null";
		}

		if (itemtype.equals(0)) {
			return "Persediaan";
		} else if (itemtype.equals(1)) {
			return "Non-Persediaan";
		} else if (itemtype.equals(2)) {
			return "Servis";
		} else if (itemtype.equals(3)) {
			return "Group";
		} else {
			return "Lain";
		}
	}

	private String getOutputFileName() {
		return Util.itemOutputFile();
	}
}
