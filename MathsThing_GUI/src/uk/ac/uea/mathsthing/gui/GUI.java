package uk.ac.uea.mathsthing.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import uk.ac.uea.mathsthing.IFormulaLexer;
import uk.ac.uea.mathsthing.IFormulaParser;
import uk.ac.uea.mathsthing.Lexer;
import uk.ac.uea.mathsthing.SimpleParser;

/**
 * Initializes the GUI for the application.
 * 
 * @author Jake Ruston
 * @version 0.1
 */
public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/** Text field used to enter the formula. */
	final JTextField inputField;
	/** The enter button to process the formula. */
    final JButton enterButton;
    /** The reset button to reset the formula. */
    final JButton resetButton;
    /** Reference to the graph so it can be updated. */
    final Graph chart;
    /** Reference to the window so we can use it in the menu bar. */
    final JFrame frame;
    /** Reference to the save graph menu item so we can enable/disable it. */
    JMenuItem saveGraphItem;
	
    /**
	 * Sets up the GUI for the application.
	 */
	public GUI() {
		
		frame = this;
		
		// Sets the window size to be 700 x 500
		final Dimension screenSize = new Dimension();
		screenSize.height = 500;
		screenSize.width = 700;
        
		setTitle("Rudy Lapeer's Mathematical Mathematicon");
        setLayout(null);
        
        setSize(screenSize);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
    	JMenu file = new JMenu("File");
    	saveGraphItem = new JMenuItem("Save Graph");
    	saveGraphItem.setEnabled(false);
    	JMenuItem exitItem = new JMenuItem("Exit");
    	
    	saveGraphItem.addActionListener(new ActionListener(){
        	
        	public void actionPerformed(ActionEvent e) {
        		
        		try {
        			
        			// Allow the user to select where to save the file to
        			JFileChooser fileChooser = new JFileChooser();
            		FileFilter filter = new FileNameExtensionFilter("PNG file", new String[] {"png"});
            		fileChooser.setFileFilter(filter);
            		
            		// Save the file to the location specified.
            		if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            			File file = new File(fileChooser.getSelectedFile().getCanonicalPath() + "." + 
            					((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0]);
            			chart.saveChartToFile(file);
            		}
            		
        		} catch (Exception ex) {
        			ex.printStackTrace();
        			JOptionPane.showMessageDialog(frame, "Could not save the graph.", "Save Graph", JOptionPane.ERROR_MESSAGE);
        		}
        	}
        	
        });
    	
    	exitItem.addActionListener(new ActionListener(){
        	
        	public void actionPerformed(ActionEvent e) {
        		
        		// Exit the app if the exit menu item is selected.
        		System.exit(0);
        	}
        	
        });
    	
    	// Add the menu items to the menu bar.
    	file.add(saveGraphItem);
    	file.add(exitItem);
    	menuBar.add(file);
    	setJMenuBar(menuBar);

    	// Add a text field near the top, taking up most of the horizontal space. Used for formula entry.
        inputField = new JTextField();
        inputField.setBounds(10, 10, screenSize.width-200, 30);
        inputField.setDragEnabled(true);
        
        // Add a button to enter the formula, to the right of the text field.
        enterButton = new JButton("Enter");
        enterButton.setBounds(screenSize.width-190, 10, 90, 30);
        
        // Add a button to reset the formula, to the right of the enter button.
        resetButton = new JButton("Reset");
        resetButton.setBounds(screenSize.width-100, 10, 90, 30);

        add(inputField);
        add(enterButton);
        add(resetButton);
        
        chart = new Graph(this);
        
        enterButton.addActionListener(new ActionListener(){
        	
        	public void actionPerformed(ActionEvent e) {
        		
        		// Tokenize the input from the user.
        		IFormulaLexer lexer = new Lexer(inputField.getText());
        		lexer.tokenize(lexer.getUserFormula());
        		
        		IFormulaParser parser = new SimpleParser();
        		
        		// Attempt to parse the formula from the tokens.
        		try {
        			parser.setFormula(lexer.getTokens());
        		} catch (Exception ex) {
        			ex.printStackTrace();
        			JOptionPane.showMessageDialog(frame, ex.getMessage(), "Invalid Formula", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		
        		HashMap<Double, BigDecimal> results = new HashMap<>();
        		
        		// It currently plots x between 0 and 10, so enter values of x to plot the graph.
        		for (double i=-10; i<=10; i+=0.25) {
        			HashMap<String, Double> vals = new HashMap<>();
        			vals.put("x", i);
        			
        			// Get the result and add it to the hash map.
        			try {
						BigDecimal result = parser.getResult(vals);
						results.put(i, result);
					} catch (Exception e1) {
						
						e1.printStackTrace();
						JOptionPane.showMessageDialog(frame, e1.getMessage(), "Invalid Formula", JOptionPane.ERROR_MESSAGE);
						return;
					}
        		}
        		
        		// Update the chart and allow the graph to be saved as a PNG.
        		chart.updateChart(inputField.getText(), results);
        		saveGraphItem.setEnabled(true);
        	}
        	
        });
        
        resetButton.addActionListener(new ActionListener(){
        	
        	public void actionPerformed(ActionEvent e) {
        		
        		// Reset the input and hide the chart.
        		inputField.setText("");
        		chart.changeVisibility(false);
        		saveGraphItem.setEnabled(false);
        	}
        	
        });
	}
	
	public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	
            	// If on a Mac, make it use the system menu bar.
            	try {
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Maths");
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                	// Do nothing - they aren't on a Mac
                }
            	
            	// Create the GUI
                GUI gui = new GUI();
                gui.setVisible(true);
            }
        });
    }
}
