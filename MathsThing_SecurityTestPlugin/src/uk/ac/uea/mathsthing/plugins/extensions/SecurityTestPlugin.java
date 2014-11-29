package uk.ac.uea.mathsthing.plugins.extensions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JMenuItem;

import uk.ac.uea.mathsthing.IPlugin.IExtensionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

public class SecurityTestPlugin extends IExtensionPlugin {
	
	@Override
	public String getName() { return "securityView"; }

	@Override
	public JMenuItem getMenuEntry()
	{		
		JMenuItem button = new JMenuItem("Security Test");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				PrintWriter writer;
				try {
					writer = new PrintWriter("test.txt", "UTF-8");
					writer.println("This text file should not be created - the security functionality should block attempts to write to files.");
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.exit(0);
			}
		});
		
		return button;
	}

	@Override
	public void processFormula() throws FormulaException { }

	@Override
	public void onReset() { }
}
