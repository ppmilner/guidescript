package guide_maker;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.Scanner;


public class guide_maker_GUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guide_maker_GUI window = new guide_maker_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public guide_maker_GUI() throws MalformedURLException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private void initialize() throws MalformedURLException, IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 693, 414);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(112, 63, 535, 289);
		frame.getContentPane().add(scrollPane);
		
		final JEditorPane editorPane = new JEditorPane("text/html", "");
		editorPane.setContentType("text/html; charset=UTF-8");
		scrollPane.setViewportView(editorPane);
		
		String path ="src.txt";
		File file = new File(path);
		editorPane.setPage(file.toURI().toURL());
		
		
		JButton btnAddHeader = new JButton("Done");
		btnAddHeader.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String raw = editorPane.getText();
					raw = raw + "$end$";
					editorPane.setText(raw);
					System.out.println(editorPane.getText());
					try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				              new FileOutputStream("src.txt"), "utf-8"))) {
				   writer.write(editorPane.getText());
					}
					main.main(null);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAddHeader.setBounds(29, 22, 117, 29);
		frame.getContentPane().add(btnAddHeader);
		
		JButton btnAddNewAccordion = new JButton("Add New Accordion");
		btnAddNewAccordion.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				String selected = editorPane.getSelectedText();
				String raw = editorPane.getText();
				selected = "#$accordion#$" + selected.substring(0, selected.length());
				System.out.println(selected);
				raw = raw.substring(0, editorPane.getSelectionStart()) + selected + raw.substring(editorPane.getSelectionEnd(),raw.length());
				editorPane.setText(raw);
			}
		});
		btnAddNewAccordion.setBounds(158, 22, 168, 29);
		frame.getContentPane().add(btnAddNewAccordion);
		
		JButton btnEndAccordion = new JButton("End Accordion");
		btnEndAccordion.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				String selected = editorPane.getSelectedText();
				String raw = editorPane.getText();
				selected = "#$accordion#$" + selected.substring(0, selected.length());
				System.out.println(selected);
				raw = raw.substring(0, editorPane.getSelectionStart()) + selected + raw.substring(editorPane.getSelectionEnd(),raw.length());
				editorPane.setText(raw);
			}
		});
		btnEndAccordion.setBounds(338, 22, 117, 29);
		frame.getContentPane().add(btnEndAccordion);
		
		JButton btnAccordionHeading = new JButton("Accordion Heading");
		btnAccordionHeading.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				String selected = editorPane.getSelectedText();
				String raw = editorPane.getText();
				selected = "#$accordionH#$" + selected.substring(0, selected.length());
				System.out.println(selected);
				raw = raw.substring(0, editorPane.getSelectionStart()) + selected + raw.substring(editorPane.getSelectionEnd(),raw.length());
				editorPane.setText(raw);
			}
		});
		btnAccordionHeading.setBounds(467, 22, 146, 29);
		frame.getContentPane().add(btnAccordionHeading);
		
		JButton btnBold = new JButton("Bold");
		btnBold.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				String selected = editorPane.getSelectedText();
				String raw = editorPane.getText();
				selected = "<strong>" + selected.substring(0, selected.length()) + "</strong>"   ;
				System.out.println(selected);
				raw = raw.substring(0, editorPane.getSelectionStart()) + selected + raw.substring(editorPane.getSelectionEnd(),raw.length());
				editorPane.setText(raw);
			}
		});
		btnBold.setBounds(0, 202, 117, 29);
		frame.getContentPane().add(btnBold);
	}
}
