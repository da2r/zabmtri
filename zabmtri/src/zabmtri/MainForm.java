package zabmtri;

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainForm extends Frame implements IMainForm, ActionListener {

	private static final long serialVersionUID = 1L;

	private TextArea label;

	private Button alphaSelect;
	private Button betaSelect;
	private Button btnStart;

	public TextField tf;
	public Button btn;

	public static void main(String[] args) {
		new MainForm();
	}

	public MainForm() {

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		label = new TextArea();
		label.setBounds(20, 100, 760, 350);
		add(label);

		alphaSelect = new Button("Pilih ABM");
		alphaSelect.setBounds(20, 50, 150, 25);
		alphaSelect.addActionListener(this);
		add(alphaSelect);

		betaSelect = new Button("Pilih TRI");
		betaSelect.setBounds(180, 50, 150, 25);
		betaSelect.addActionListener(this);
		add(betaSelect);

		btnStart = new Button("Mulai");
		btnStart.setBounds(500, 50, 150, 25);
		btnStart.addActionListener(this);
		add(btnStart);

		setLocation(50, 50);
		setSize(800, 500);
		setLayout(null);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == alphaSelect) {
				String selected = showOpenFileDialog();
				if (selected == null) {
					return;
				}

				AppData.alphaPath = "localhost/3051:" + selected;
				printOutput("ABM : " + selected);
			} else if (e.getSource() == betaSelect) {
				String selected = showOpenFileDialog();
				if (selected == null) {
					return;
				}

				AppData.betaPath = "localhost/3051:" + selected;
				printOutput("TRI : " + selected);
			} else if (e.getSource() == btnStart) {
				if (AppData.alphaPath == null || AppData.alphaPath.isEmpty()) {
					throw new RuntimeException("ABM belum dipilih");
				}
				if (AppData.betaPath == null || AppData.betaPath.isEmpty()) {
					throw new RuntimeException("TRI belum dipilih");
				}
				// if (AppData.targetPath == null ||
				// AppData.targetPath.isEmpty()) {
				// throw new RuntimeException("Tujuan belum dipilih");
				// }

				Executor.execute();

				printOutput("");
				printOutput(String.format("Hasil bisa dilihat di folder : %s", Util.getOutputFolder()));
			} else {
				throw new RuntimeException("No action defined");
			}
		} catch (Throwable t) {
			printOutput(Util.getStackTrace(t));
		}
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
		label.append(text);
		label.append("\n");
	}

}
