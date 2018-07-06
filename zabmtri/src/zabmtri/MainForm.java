package zabmtri;

import java.awt.Button;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class MainForm extends Frame implements IMainForm, ActionListener {

	private static final long serialVersionUID = 1L;

	enum ImportStep {
		Account, Warehouse, Vendor, Customer, Item, SerialNumber, JournalVoucher, OtherPayment, OtherDeposit, Purchase, Sales, Done
	}

	private ImportStep step = null;

	private TextArea output;

	private Button alphaSelect;
	private Button betaSelect;
	private Button targetSelect;

	private Button btnStart;
	private Button btnNext;

	public static void main(String[] args) {
		AppData.mainForm = new MainForm();

	}

	public MainForm() {

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showConfirmDialog(e.getWindow(), "Tutup program ini ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
				if (n == 0) {
					dispose();
				}

			}
		});

		output = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		output.setBounds(20, 100, 760, 350);
		output.setEditable(false);
		output.setBackground(Color.LIGHT_GRAY);
		add(output);

		alphaSelect = new Button("Pilih ABM_V5");
		alphaSelect.setBounds(20, 50, 150, 25);
		alphaSelect.addActionListener(this);
		add(alphaSelect);

		betaSelect = new Button("Pilih TRIAMA");
		betaSelect.setBounds(180, 50, 150, 25);
		betaSelect.addActionListener(this);
		add(betaSelect);

		targetSelect = new Button("Pilih Tujuan");
		targetSelect.setBounds(340, 50, 150, 25);
		targetSelect.addActionListener(this);

		add(targetSelect);

		btnStart = new Button("Gabungkan");
		btnStart.setBounds(630, 50, 150, 25);
		btnStart.addActionListener(this);
		btnStart.setEnabled(false);
		add(btnStart);

		btnNext = new Button("Lanjutkan");
		btnNext.setBounds(580, 460, 200, 25);
		btnNext.addActionListener(this);
		btnNext.setVisible(false);
		add(btnNext);

		setLocation(50, 50);
		setSize(800, 500);
		setLayout(null);
		setVisible(true);
		
		StringBuilder startMsg = new StringBuilder();
		
		output.append(startMsg.toString());
		
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == alphaSelect) {
				String selected = showOpenFileDialog();
				if (selected == null) {
					return;
				}

				AppData.alphaPath = Util.asFirebirdPath(selected);
				printOutput("ABM_V5 : " + AppData.alphaPath);
				checkBtnStartEnable();
			} else if (e.getSource() == betaSelect) {
				String selected = showOpenFileDialog();
				if (selected == null) {
					return;
				}

				AppData.betaPath = Util.asFirebirdPath(selected);
				printOutput("TRIAMA : " + AppData.betaPath);
				checkBtnStartEnable();
			} else if (e.getSource() == targetSelect) {
				String selected = showOpenFileDialog();
				if (selected == null) {
					return;
				}

				AppData.targetPath = Util.asFirebirdPath(selected);
				printOutput("Tujuan : " + AppData.targetPath);
				checkBtnStartEnable();
			} else if (e.getSource() == btnStart) {
				printOutput("Membaca data sumber, harap menunggu ...");

				btnStart.setEnabled(false);
				try {
					JOptionPane.showMessageDialog(this, "Proses penggabungan database akan dimulai.\n\nPerhatian: Proses mungkin membutuhkan waktu yang lama");
					Executor.executeMerge();
				} finally {
					btnStart.setEnabled(true);
				}

				showImport();
				nextImportStep();
			} else if (e.getSource() == btnNext) {
				nextImportStep();
			} else {
				throw new RuntimeException("No action defined");
			}
		} catch (Throwable t) {
			t.printStackTrace();
			printOutput(Util.getStackTrace(t));
		}
	}

	private void showImport() {
		output.setBounds(20, 50, 760, 400);
		alphaSelect.setVisible(false);
		betaSelect.setVisible(false);
		targetSelect.setVisible(false);
		btnStart.setVisible(false);
		btnNext.setVisible(true);
	}

	private void nextImportStep() {
		if (step == null) {
			showImportAccount();
			return;
		}

		if (step == ImportStep.Done) {
			JOptionPane.showMessageDialog(this, "Proses telah selesai. Program ini akan menutup sendiri.");
			dispose();
			return;
		}

		int n = JOptionPane.showConfirmDialog(this, "Lanjutkan ke langkah selanjutnya ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
		if (n != 0) {
			return;
		}

		if (step == ImportStep.Account) {
			showImportWarehouse();
		} else if (step == ImportStep.Warehouse) {
			showImportVendor();
		} else if (step == ImportStep.Vendor) {
			showImportCustomer();
		} else if (step == ImportStep.Customer) {
			showImportItem();
		} else if (step == ImportStep.Item) {
			showImportSerialNumber();
		} else if (step == ImportStep.SerialNumber) {
			showImportJournalVoucher();
		} else if (step == ImportStep.JournalVoucher) {
			showImportOtherPayment();
		} else if (step == ImportStep.OtherPayment) {
			showImportOtherDeposit();
		} else if (step == ImportStep.OtherDeposit) {
			showImportPurchase();
		} else if (step == ImportStep.Purchase) {
			showImportSales();
		} else if (step == ImportStep.Sales) {
			showImportDone();
		} else {
			throw new RuntimeException("invalid step " + step);
		}
	}

	private void showImportAccount() {
		step = ImportStep.Account;

		StringBuilder sb = new StringBuilder();
		sb.append("Langkah 1 - Akun");
		sb.append("\n");
		sb.append("\n");
		sb.append("Buka Database tujuan, masuk ke menu Daftar Akun Pekiraan dan pilih Impor.");
		sb.append("\n");
		sb.append(String.format("Gunakan file \"%s\" saat impor agar data Akun masuk ke database Tujuan", Util.glAccountOutputFile()));
		sb.append("\n");
		sb.append("\n");
		sb.append("Klik tombol [Lanjutkan] jika hal ini sudah dilakukan.");
		output.setText(sb.toString());
	}

	private void showImportWarehouse() {
		step = ImportStep.Warehouse;

		StringBuilder sb = new StringBuilder();
		sb.append("Langkah 2 - Gudang");
		sb.append("\n");
		sb.append("\n");
		sb.append(String.format("Program ini akan memasukan data gudang dari file \"%s\" ke Database tujuan", Util.warehsOutputFile()));
		sb.append(String.format("Program ini akan memasukan data penjual dari file \"%s\" ke Database tujuan", Util.salesmanOutputFile()));
		sb.append("\n");
		sb.append("Harap menunggu ..");
		sb.append("\n");
		sb.append("\n");
		output.setText(sb.toString());

		JOptionPane.showMessageDialog(this, "Program ini akan memasukan Gudang dan Penjual ke Database tujuan.");

		Executor.executeImportWarehs();
		Executor.executeImportSalesman();
		printOutput("");
		printOutput("Selesai memasukan data Gudang.");

		JOptionPane.showMessageDialog(this, "Selesai memasukan data Gudang dan Penjual. Klik tombol [Lanjutkan] untuk melanjutkan ke langkah berikutnya.");
	}

	private void showImportVendor() {
		step = ImportStep.Vendor;

		StringBuilder sb = new StringBuilder();
		sb.append("Langkah 3 - Pemasok");
		sb.append("\n");
		sb.append("\n");
		sb.append("Buka Database tujuan, masuk ke menu Daftar Pemasok dan pilih Impor.");
		sb.append("\n");
		sb.append(String.format("Gunakan file \"%s\" saat impor agar data Pemasok masuk ke database Tujuan", Util.vendorOutputFile()));
		sb.append("\n");
		sb.append("\n");
		sb.append("Klik tombol [Lanjutkan] jika hal ini sudah dilakukan.");
		output.setText(sb.toString());
	}

	private void showImportCustomer() {
		step = ImportStep.Customer;

		StringBuilder sb = new StringBuilder();
		sb.append("Langkah 4 - Pelanggan");
		sb.append("\n");
		sb.append("\n");
		sb.append("Buka Database tujuan, masuk ke menu Daftar Pelanggan dan pilih Impor.");
		sb.append("\n");
		sb.append(String.format("Gunakan file \"%s\" saat impor agar data Pelanggan masuk ke database Tujuan", Util.customerOutputFile()));
		sb.append("\n");
		sb.append("\n");
		sb.append("Klik tombol [Lanjutkan] jika hal ini sudah dilakukan.");
		output.setText(sb.toString());
	}

	private void showImportItem() {
		step = ImportStep.Item;

		StringBuilder sb = new StringBuilder();
		sb.append("Langkah 5 - Barang");
		sb.append("\n");
		sb.append("\n");
		sb.append("Buka Database tujuan, masuk ke menu Daftar Kategori Barang dan pilih Impor.");
		sb.append("\n");
		sb.append(String.format("Gunakan file \"%s\" saat impor agar data Kategori Barang masuk ke database Tujuan", Util.itemCategoryOutputFile()));
		sb.append("\n");
		sb.append("\n");
		sb.append("Buka Database tujuan, masuk ke menu Daftar Barang dan pilih Impor.");
		sb.append("\n");
		sb.append(String.format("Gunakan file \"%s\" saat impor agar data Barang masuk ke database Tujuan", Util.itemOutputFile()));
		sb.append("\n");
		sb.append("\n");
		sb.append("Klik tombol [Lanjutkan] jika hal ini sudah dilakukan.");
		output.setText(sb.toString());
	}

	private void showImportSerialNumber() {
		step = ImportStep.SerialNumber;

		StringBuilder sb = new StringBuilder();
		sb.append("Langkah 6 - Nomor Seri Barang");
		sb.append("\n");
		sb.append("\n");
		sb.append(String.format("Program ini akan memasukan data Nomor Seri pada Barang, dengan data dari file \"%s\"", Util.itemSnOutputFile()));
		sb.append("\n");
		sb.append("Harap menunggu ..");
		sb.append("\n");
		sb.append("\n");
		output.setText(sb.toString());

		btnNext.setEnabled(false);
		try {
			JOptionPane.showMessageDialog(this, "Program ini akan memasukan data Nomor Seri Barang ke Database tujuan.");
			Executor.executeImportItemSn();
			printOutput("");
			printOutput("Selesai memasukan data Nomor Seri Barang.");
		} finally {
			btnNext.setEnabled(true);
		}

		JOptionPane.showMessageDialog(this, "Selesai memasukan data Nomor Seri Barang. Klik tombol [Lanjutkan] untuk melanjutkan ke langkah berikutnya.");
	}

	private void showImportJournalVoucher() {
		step = ImportStep.JournalVoucher;

		StringBuilder sb = new StringBuilder();
		sb.append("Langkah 7 - Jurnal Umum");
		sb.append("\n");
		sb.append("\n");
		sb.append("Buka Database tujuan, masuk ke menu Berkas -> Ekspor Impor Transaksi.  ");
		sb.append("\n");
		sb.append(String.format("Pilih Import Data dan Gunakan file \"%s\" sebagai data impor", Util.jvOutputFile()));
		sb.append("\n");
		sb.append("\n");
		sb.append("Klik tombol [Lanjutkan] jika hal ini sudah dilakukan.");
		output.setText(sb.toString());
	}

	private void showImportOtherPayment() {
		step = ImportStep.OtherPayment;

		StringBuilder sb = new StringBuilder();
		sb.append("Langkah 8 - Penerimaan Lain");
		sb.append("\n");
		sb.append("\n");
		sb.append("Buka Database tujuan, masuk ke menu Berkas -> Ekspor Impor Transaksi.  ");
		sb.append("\n");
		sb.append(String.format("Pilih Import Data dan Gunakan file \"%s\" sebagai data impor", Util.opOutputFile()));
		sb.append("\n");
		sb.append("\n");
		sb.append("Klik tombol [Lanjutkan] jika hal ini sudah dilakukan.");
		output.setText(sb.toString());
	}

	private void showImportOtherDeposit() {
		step = ImportStep.OtherDeposit;

		StringBuilder sb = new StringBuilder();
		sb.append("Langkah 9 - Pendapatan Lain");
		sb.append("\n");
		sb.append("\n");
		sb.append("Buka Database tujuan, masuk ke menu Berkas -> Ekspor Impor Transaksi.  ");
		sb.append("\n");
		sb.append(String.format("Pilih Import Data dan Gunakan file \"%s\" sebagai data impor", Util.odOutputFile()));
		sb.append("\n");
		sb.append("\n");
		sb.append("Klik tombol [Lanjutkan] jika hal ini sudah dilakukan.");
		output.setText(sb.toString());
	}

	private void showImportPurchase() {
		step = ImportStep.Purchase;

		StringBuilder sb = new StringBuilder();
		sb.append("Langkah 10 - Pembelian");
		sb.append("\n");
		sb.append("\n");
		sb.append("Buka Database tujuan, masuk ke menu Berkas -> Ekspor Impor Transaksi.  ");
		sb.append("\n");
		sb.append(String.format("Pilih Import Data dan Gunakan file \"%s\" sebagai data impor", Util.purchaseOutputFile()));
		sb.append("\n");
		sb.append("\n");
		sb.append("Klik tombol [Lanjutkan] jika hal ini sudah dilakukan.");
		output.setText(sb.toString());
	}

	private void showImportSales() {
		step = ImportStep.Sales;

		StringBuilder sb = new StringBuilder();
		sb.append("Langkah 11 - Penjualan");
		sb.append("\n");
		sb.append("\n");
		sb.append("Buka Database tujuan, masuk ke menu Berkas -> Ekspor Impor Transaksi.  ");
		sb.append("\n");
		sb.append(String.format("Pilih Import Data dan Gunakan file \"%s\" sebagai data impor", Util.salesOutputFile()));
		sb.append("\n");
		sb.append("\n");
		sb.append("Klik tombol [Lanjutkan] jika hal ini sudah dilakukan.");
		output.setText(sb.toString());
	}

	private void showImportDone() {
		step = ImportStep.Done;
		output.setText("Proses penggabungan telah selesai.");
	}

	private void checkBtnStartEnable() {
		btnStart.setEnabled(AppData.alphaPath != null && AppData.betaPath != null && AppData.targetPath != null);
	}

	private String showOpenFileDialog() {
		FileDialog fd = new FileDialog(this, "Pilih file GDB", FileDialog.LOAD);
		fd.setMultipleMode(false);
		// fd.setDirectory("C:\\");
		fd.setFile("*.gdb");
		fd.setVisible(true);

		if (fd.getFile() == null) {
			return null;
		}

		return fd.getFiles()[0].getAbsolutePath();
	}

	public void printOutput(String text) {
		output.append(text);
		output.append("\n");
	}

}
