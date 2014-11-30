package uk.ac.uea.mathsthing.plugins.extensions;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import uk.ac.uea.mathsthing.IPlugin.IExtensionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

public class RudyPlugin extends IExtensionPlugin {
	
	@Override
	public String getName() { return "rudyView"; }

	@Override
	public JMenuItem getMenuEntry()
	{		
		JMenuItem button = new JMenuItem("Rudy Lapeer");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createGUI();
			}
		});
		
		return button;
	}

	@Override
	public void processFormula() throws FormulaException { }
	
	private void createGUI()
	{
		final JFrame window = new JFrame();
		window.getContentPane().setLayout(null);
		final Dimension screenSize = new Dimension();
		screenSize.height = 499;
		screenSize.width = 600;
		window.setSize(screenSize);
		window.setTitle("Rudy Lapeer");
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		BufferedImage myPicture = null;
		
		try {
			myPicture = ImageIO.read(this.getClass().getResourceAsStream("/rudy.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.setBounds(0, 0, 600, 399);
		window.add(picLabel);
		
		JButton writeToFileButton = new JButton("Write to file");
		writeToFileButton.setBounds(80, 412, 200, 50);
		
		writeToFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				PrintWriter writer;
				try {
					writer = new PrintWriter("test.txt", "UTF-8");
					writer.println("This text file should not be created - the security functionality should block attempts to write to files.");
					writer.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(window, e.getMessage(),
							"Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton closeButton = new JButton("Close application");
		closeButton.setBounds(320, 412, 200, 50);
		
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					System.exit(0);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(window, e.getMessage(),
							"Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		window.add(writeToFileButton);
		window.add(closeButton);
		
		window.setResizable(false);
		window.setVisible(true);
	}

	@Override
	public void onReset() { }
}
