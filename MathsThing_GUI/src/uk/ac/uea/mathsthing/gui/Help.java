package uk.ac.uea.mathsthing.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.commons.io.IOUtils;

/**
 * Initializes the help menu for the user. This loads the HTML file and displays it to the user.
 * 
 * @author Jake Ruston
 * @version 0.1
 */
public class Help
{
	
  /**
   * Empty constructor.
   */
  public Help()
  {
  }
  
  /**
   * Method to display the help menu to the user.
   * @param mainFrame The frame of the main window so we can draw the help menu on the top of it.
   */
  public void display(final JFrame mainFrame) {
	  
	  SwingUtilities.invokeLater(new Runnable()
	    {
	      public void run()
	      {
	        JEditorPane jEditorPane = new JEditorPane();
	        
	        jEditorPane.setEditable(false);
	        
	        JScrollPane scrollPane = new JScrollPane(jEditorPane);
	        
	        HTMLEditorKit kit = new HTMLEditorKit();
	        jEditorPane.setEditorKit(kit);
	        
	        String htmlString = "";
	        
	        // Load the HTML file to display to the user.
	        try {
				htmlString = IOUtils.toString(this.getClass().getResourceAsStream("/help.html"),
                        "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
	        	        
	        Document doc = kit.createDefaultDocument();
	        jEditorPane.setDocument(doc);
	        jEditorPane.setText(htmlString);

	        JFrame j = new JFrame("Help");
	        j.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/app-icon.png")));
	        j.getContentPane().add(scrollPane, BorderLayout.CENTER);
	        
	        j.setSize(new Dimension(700, 500));
	        
	        j.setLocationRelativeTo(mainFrame);
	        j.setVisible(true);
	      }
	    });
  }
}