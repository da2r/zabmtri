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

public class ItemExporter {

	public void execute() {
		try {
			Path path = Paths.get(getOutputFileName());
			BufferedWriter writer = Files.newBufferedWriter(path);
			CSVPrinter csvPrinter = new CSVPrinter(writer, getHeader());
			try {
				for (EItem data : AppData.alphaItem) {
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
		header.add("No. Barang");
		header.add("Nama");

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

	private Iterable<?> getRow(EItem data) {
		List<Object> result = new ArrayList<Object>();
		result.add(data.itemno);
		result.add(data.itemdescription);

		return result;
	}

	private String getOutputFileName() {
		return Util.itemOutputFile();
	}
}
