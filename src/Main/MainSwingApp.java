package Main;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainSwingApp {

	private static BufferedReader myFile;
	private static String ip="";
	public static void main(String[] args) {
		
		JFrame win1 = new JFrame("Redirector");
		win1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btn1 = new JButton("GO!");
		JButton btn2 = new JButton("IP");
		JTextField txt1 = new JTextField();
		JPanel frame = new JPanel(new GridLayout(2, 2));
		JPanel panel1 = new JPanel(new GridLayout(1, 2));
		JPanel panel2 = new JPanel(new GridLayout(1, 2));
		JLabel iplbl = new JLabel("");
		panel1.add(btn1);
		panel1.add(txt1);
		panel2.add(btn2);
		panel2.add(iplbl);
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Runtime r=Runtime.getRuntime();
				try {
					r.exec("cmd /c start cmd.exe /C \"ipconfig > E:\\a.txt\"");
				} catch (IOException e1) {
					System.out.println("ERROR");
				}
				try {
					ip=ReadIP("E:\\a.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				iplbl.setText(ip);
			}
		});
		
		frame.add(panel1);
		frame.add(panel2);
		win1.add(frame);
		win1.setSize(1200, 100);
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Desktop defDesk = Desktop.getDesktop();
				String address = txt1.getText();
				if (address == null || address.trim().isEmpty()) {
					System.out.println("ERROR");
				} else {
					address = address.toLowerCase();
					boolean isFile = address.contains(":");
					if (isFile) {
						try {
							defDesk.open(new File(address));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						boolean isWww = address.contains("www.");
						boolean isCoil = address.contains(".co.il");
						boolean isCom = address.contains(".com");
						if (isWww == false) {
							address = "www." + address;
						}
						if (!(isCoil && isCom)) {
							address += ".com";
						}
						try {
							defDesk.browse(new URI(address));
						} catch (Exception e1) {
						}
					}
				}
			}
		});
		win1.setVisible(true);
	}
	public static String ReadIP(String path) throws IOException {
		try {
			myFile = new BufferedReader(new FileReader(path));
			for(int i=0;i<23;i++) {
				myFile.readLine();
			}
		 String[] toProcess= myFile.readLine().split(":");
		 return toProcess[1].replace(" ", "");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}


