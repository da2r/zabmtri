package zabmtri;

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainForm extends Frame implements IMainForm, ActionListener {

	private static final long serialVersionUID = 1L;

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

		tf = new TextField();
		tf.setBounds(60, 50, 170, 20);

		btn = new Button("click me");
		btn.setBounds(100, 120, 80, 30);
		btn.addActionListener(this);

		add(btn);
		add(tf);
		setSize(300, 300);
		setLayout(null);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn) {
			tf.setText("test");
			// showOpenFileDialog();

			String alpha = "192.168.1.202/3051:C:\\Documents and Settings\\herman\\Desktop\\TEST.GDB";
			String beta = "192.168.1.202/3051:C:\\Program Files\\CPSSoft\\ACCURATE4 deluxe 1423\\SAMPLE\\SAMPLE.GDB";
			String target = "192.168.1.202/3051:C:\\Documents and Settings\\herman\\Desktop\\TEST2.GDB";

//			CompareData compare = new CompareData(this, alpha, beta, target);
//			compare.start();
		}
	}

	@SuppressWarnings("unused")
	private void showOpenFileDialog() {
		FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.LOAD);
		fd.setDirectory("C:\\");
		fd.setFile("*.gdb");
		fd.setVisible(true);
		String filename = fd.getFile();
		if (filename != null) {
			System.out.println(filename);
			for (File f : fd.getFiles()) {
				System.out.println(f.getAbsolutePath());
			}
		}
	}

	public void setProgressText(String text) {
		tf.setText(text);
	}

}
