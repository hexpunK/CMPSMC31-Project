package uk.ac.uea.mathsthing.plugins.extensions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

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
	public void processFormula() throws FormulaException, SecurityException
	{
	}
	
	private void createGUI()
	{
		JFrame window = new JFrame();
		window.setBounds(50, 50, 600, 399);
		window.setTitle("Rudy Lapeer");
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		BufferedImage myPicture = null;
		
		try {
			myPicture = ImageIO.read(this.getClass().getResourceAsStream("/rudy.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		
		window.add(picLabel);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
	}
}
