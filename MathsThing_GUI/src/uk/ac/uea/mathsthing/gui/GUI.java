package uk.ac.uea.mathsthing.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private static final double INCREMENTER_VALUE = 0.25;
	
	/** Text field used to enter the formula. */
	final JTextField inputField, fromField, toField;
	/** The enter button to process the formula. */
    final JButton enterButton;
    /** The reset button to reset the formula. */
    final JButton resetButton;
    /** Reference to the graph so it can be updated. */
    final Graph chart;
    /** Reference to the window so we can use it in the menu bar. */
    final JFrame frame;
    /** Reference to the save graph menu item so we can enable/disable it. */
    JMenuItem saveGraphItem, productHelpItem, aboutItem;
	
    /**
	 * Sets up the GUI for the application.
	 */
	public GUI() {
		
		frame = this;
		
		// Sets the window size to be 700 x 600
		final Dimension screenSize = new Dimension();
		screenSize.height = 600;
		screenSize.width = 700;
        
		setTitle("Rudy Lapeer's Mathematical Mathematicon");
        setLayout(null);
        
        setSize(screenSize);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/app-icon.png")));
        
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
    	JMenu file = new JMenu("File");
    	saveGraphItem = new JMenuItem("Save Graph");
    	saveGraphItem.setEnabled(false);
    	JMenuItem exitItem = new JMenuItem("Exit");
    	
    	JMenu help = new JMenu("Help");
    	productHelpItem = new JMenuItem("Product Guide");
    	aboutItem = new JMenuItem("About");
    	
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
    	
    	productHelpItem.addActionListener(new ActionListener(){
        	
        	public void actionPerformed(ActionEvent e) {
        		
        		// Display the help guide when the product guide menu item has been clicked.
        		Help help = new Help();
        		help.display(frame);
        	}
        	
        });
    	
    	aboutItem.addActionListener(new ActionListener(){
        	
        	public void actionPerformed(ActionEvent e) {
        		
        		// Show an about window when the about menu item has been clicked.
        		JOptionPane.showMessageDialog(frame, "Maths Thing v0.1 by Laura Goold, Jake Ruston and Jordan Woerner\nfor Advanced Programming Techniques (2014).", 
        				"About", JOptionPane.INFORMATION_MESSAGE);
        	}
        	
        });
    	
    	// Add the menu items to the menu bar.
    	file.add(saveGraphItem);
    	file.add(exitItem);
    	help.add(productHelpItem);
    	help.add(aboutItem);
    	menuBar.add(file);
    	menuBar.add(help);
    	setJMenuBar(menuBar);

    	// Add a text field near the top, taking up most of the horizontal space. Used for formula entry.
        inputField = new JTextField();
        inputField.setBounds(10, 10, screenSize.width-20, 30);
        inputField.setDragEnabled(true);
        
        // Add a label saying "Values", to tell the user they need to enter the "from" and "to" values to draw the graph.
        JLabel fromLabel = new JLabel();
        fromLabel.setText("Values:");
    	fromLabel.setBounds(10, 50, 60, 30);
    	add(fromLabel);
        
    	// Add a text field for the "from" value - where the graph will start plotting from.
        fromField = new JTextField();
        fromField.setBounds(70, 50, 60, 30);
        fromField.setText("-10");
        fromField.setDragEnabled(true);
        
        // Add a separator label between the "from" and "to" values.
        JLabel separatorLabel = new JLabel();
        separatorLabel.setText("-");
        separatorLabel.setBounds(140, 50, 8, 30);
    	add(separatorLabel);
        
    	// Add a text field for the "to" value - where the graph will finish plotting from.
        toField = new JTextField();
        toField.setBounds(158, 50, 60, 30);
        toField.setText("10");
        toField.setDragEnabled(true);
        
        // Add a button to enter the formula, to the right of the text field.
        enterButton = new JButton("Enter");
        enterButton.setBounds(screenSize.width-220, 50, 90, 30);
        
        // Add a button to reset the formula, to the right of the enter button.
        resetButton = new JButton("Reset");
        resetButton.setBounds(screenSize.width-100, 50, 90, 30);

        add(inputField);
        add(fromField);
        add(toField);
        add(enterButton);
        add(resetButton);
        
        chart = new Graph(this);
        
        enterButton.addActionListener(new ActionListener(){
        	
        	public void actionPerformed(ActionEvent e) {
        		
        		double fromValue = 0.0, toValue = 0.0;
        		
        		// Attempt to parse the from and to values as numbers. Exception is thrown if they aren't valid numbers.
        		try {
        			fromValue = Double.parseDouble(fromField.getText());
        			toValue = Double.parseDouble(toField.getText());
        		} catch (Exception ex) {
        			ex.printStackTrace();
        			JOptionPane.showMessageDialog(frame, "The from and to values must be numbers!", "Invalid Values", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		
        		// Throw an error if the from value is less than the to value.
        		if (fromValue >= toValue) {
        			JOptionPane.showMessageDialog(frame, "The from value must be lower than the to value.", "Invalid Values", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		
        		if (fromValue < -10000 || toValue > 10000) {
        			JOptionPane.showMessageDialog(frame, "The from and to values must be between -10,000 and 10,000.", "Invalid Values", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		
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
       
        		for (double i=fromValue; i<=toValue; i+=INCREMENTER_VALUE) {
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
