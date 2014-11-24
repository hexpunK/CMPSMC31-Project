package uk.ac.uea.mathsthing.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

import uk.ac.uea.mathsthing.Formula;
import uk.ac.uea.mathsthing.IPlugin.IExtensionPlugin;
import uk.ac.uea.mathsthing.Lexer;
import uk.ac.uea.mathsthing.SimpleParser;
import uk.ac.uea.mathsthing.util.FormulaException;
import uk.ac.uea.mathsthing.util.IFormula;
import uk.ac.uea.mathsthing.util.IFormulaLexer;
import uk.ac.uea.mathsthing.util.IFormulaParser;
import uk.ac.uea.mathsthing.util.IObservable;
import uk.ac.uea.mathsthing.util.IObserver;
import uk.ac.uea.mathsthing.util.PluginSystem;

/**
 * Initialises the GUI for the application.
 * 
 * @author Jake Ruston
 * @version 0.1
 */
public class GUI extends JFrame implements IObserver {

	private static final long serialVersionUID = 1L;
	/** The number of points to chart on the graph. */
	static final int CHARTED_POINTS = 2000;

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
	/** A {@link IFormulaLexer} to use when interpreting the user formula. */
	IFormulaLexer lexer;
	/** A {@link IFormulaParser} to use when evaluating the user formula. */
	IFormulaParser parser;
	/** A list of the extension plugins loaded into the GUI. */
	ArrayList<IExtensionPlugin> loadedPlugins;

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

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/app-icon.png")));

		// Create the menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		saveGraphItem = new JMenuItem("Save Graph");
		saveGraphItem.setEnabled(false);
		JMenuItem exitItem = new JMenuItem("Exit");
		
		// Load IExtensionPlugin instances to hook into the GUI.
		loadedPlugins = new ArrayList<>();
		try {
			for(Class<?> plugin : PluginSystem.getPlugins()) {
				if (IExtensionPlugin.class.isAssignableFrom(plugin)) {
					loadedPlugins.add((IExtensionPlugin)plugin.newInstance());
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | ClassNotFoundException | IOException
				| InstantiationException e) {
			e.printStackTrace();
			System.err.println("Error loading extension plugins. Reason: " + e.getMessage());
		}
		// Create the plugins menu if any extensions are found.
		JMenu plugins = null;		
		if (!loadedPlugins.isEmpty()) {
			plugins = new JMenu("Plugins");
			for(IExtensionPlugin plugin : loadedPlugins) {
				plugins.add(plugin.getMenuEntry());
			}
		}

		JMenu help = new JMenu("Help");
		productHelpItem = new JMenuItem("Product Guide");
		aboutItem = new JMenuItem("About");

		saveGraphItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					// Allow the user to select where to save the file to
					JFileChooser fileChooser = new JFileChooser();
					FileFilter filter = new FileNameExtensionFilter("PNG file",
							new String[]{"png"});
					fileChooser.setFileFilter(filter);

					// Save the file to the location specified.
					if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
						File file = new File(fileChooser.getSelectedFile()
								.getCanonicalPath()
								+ "."
								+ ((FileNameExtensionFilter) fileChooser
										.getFileFilter()).getExtensions()[0]);
						chart.saveChartToFile(file);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame,
							"Could not save the graph.", "Save Graph",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		exitItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Exit the app if the exit menu item is selected.
				System.exit(0);
			}

		});

		productHelpItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Display the help guide when the product guide menu item has
				// been clicked.
				Help help = new Help();
				help.display(frame);
			}

		});

		aboutItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Show an about window when the about menu item has been
				// clicked.
				JOptionPane
						.showMessageDialog(
								frame,
								"Maths Thing v1.0 by Laura Goold, Jake Ruston and Jordan Woerner\nfor Advanced Programming Techniques (2014).",
								"About", JOptionPane.INFORMATION_MESSAGE);
			}

		});

		// Add the menu items to the menu bar.
		file.add(saveGraphItem);
		file.add(exitItem);
		help.add(productHelpItem);
		help.add(aboutItem);
		menuBar.add(file);
		if (plugins != null) {
			menuBar.add(plugins);
		}
		menuBar.add(help);
		setJMenuBar(menuBar);

		// Add a text field near the top, taking up most of the horizontal
		// space. Used for formula entry.
		inputField = new JTextField();
		inputField.setBounds(10, 10, screenSize.width - 20, 30);
		inputField.setDragEnabled(true);

		// Add a label saying "Values", to tell the user they need to enter the
		// "from" and "to" values to draw the graph.
		JLabel fromLabel = new JLabel();
		fromLabel.setText("Values:");
		fromLabel.setBounds(10, 50, 60, 30);
		add(fromLabel);

		// Add a text field for the "from" value - where the graph will start
		// plotting from.
		fromField = new JTextField();
		fromField.setBounds(70, 50, 60, 30);
		fromField.setText("-10");
		fromField.setDragEnabled(true);

		// Add a separator label between the "from" and "to" values.
		JLabel separatorLabel = new JLabel();
		separatorLabel.setText("-");
		separatorLabel.setBounds(140, 50, 8, 30);
		add(separatorLabel);

		// Add a text field for the "to" value - where the graph will finish
		// plotting from.
		toField = new JTextField();
		toField.setBounds(158, 50, 60, 30);
		toField.setText("10");
		toField.setDragEnabled(true);

		// Add a button to enter the formula, to the right of the text field.
		enterButton = new JButton("Enter");
		enterButton.setBounds(screenSize.width - 220, 50, 90, 30);

		// Add a button to reset the formula, to the right of the enter button.
		resetButton = new JButton("Reset");
		resetButton.setBounds(screenSize.width - 100, 50, 90, 30);

		add(inputField);
		add(fromField);
		add(toField);
		add(enterButton);
		add(resetButton);
		getRootPane().setDefaultButton(enterButton);

		chart = new Graph(this);

		enterButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				// Tokenize the input from the user.
				IFormulaLexer lexer = new Lexer(inputField.getText());
				lexer.setFormula(lexer.getUserFormula());
				lexer.attach(GUI.this);
				new Thread((Runnable) lexer).start();
			}

		});

		resetButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Reset the input and hide the chart.
				inputField.setText("");
				chart.changeVisibility(false);
				saveGraphItem.setEnabled(false);
				setCursor(Cursor.getDefaultCursor());
			}

		});
	}

	/**
	 * Performed once lexical analysis of a provided function is completed.
	 * 
	 * @param data The {@link IFormulaLexer} to grab the analysed tokens from.
	 */
	private void processLexing(Object data) {

		// If it fails, then we just received the Lexer instance instead.
		lexer = (IFormulaLexer) data;
		parser = new SimpleParser();
		parser.attach(GUI.this);
		// Attempt to parse the formula from the tokens.
		try {
			parser.setFormula(lexer.getTokens());
			new Thread((Runnable) parser).start();
		} catch (Exception ex) {
			ex.printStackTrace();
			setCursor(Cursor.getDefaultCursor());
			JOptionPane.showMessageDialog(frame, ex.getMessage(),
					"Invalid Formula", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Evaluates a parsed {@link IFormula}, plotting points on the chart.
	 * 
	 * @param formula The {@link IFormula} to evaluate.
	 */
	private void processEvaluation(IFormula formula) {

		// There is a possibility that the returned formula won't actually
		// exist.
		if (formula == null) {
			setCursor(Cursor.getDefaultCursor());
			JOptionPane.showMessageDialog(frame,
					"No formula could be created from input.",
					"Invalid Formula", JOptionPane.ERROR_MESSAGE);
			return;
		}
		formula.setParameters(lexer.getParameters());

		double fromValue = 0.0, toValue = 0.0;

		// Attempt to parse the from and to values as numbers. Exception is
		// thrown if they aren't valid numbers.
		try {
			fromValue = Double.parseDouble(fromField.getText());
			toValue = Double.parseDouble(toField.getText());
		} catch (Exception ex) {
			ex.printStackTrace();
			setCursor(Cursor.getDefaultCursor());
			JOptionPane.showMessageDialog(frame,
					"The from and to values must be numbers!",
					"Invalid Values", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Throw an error if the from value is less than the to value.
		if (fromValue >= toValue) {
			setCursor(Cursor.getDefaultCursor());
			JOptionPane.showMessageDialog(frame,
					"The from value must be lower than the to value.",
					"Invalid Values", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Ensure the values are between -1000 and 1000 to prevent abnormally
		// large numbers.
		if (fromValue < -1000 || toValue > 1000) {
			setCursor(Cursor.getDefaultCursor());
			JOptionPane.showMessageDialog(frame,
					"The from and to values must be between -1,000 and 1,000.",
					"Invalid Values", JOptionPane.ERROR_MESSAGE);
			return;
		}

		HashMap<Double, BigDecimal> results = new HashMap<>();
		HashMap<String, Double> vals = lexer.getParameters();
		// Calculate the increment for charted points.
		double incr = ((toValue - fromValue) / CHARTED_POINTS);
		double i = fromValue;
		BigDecimal lastResult = new BigDecimal(0.0);

		for (; i <= toValue; i += incr) {
			vals.put(formula.getXAxis(), i);
			// Get the result and add it to the hash map.
			try {
				BigDecimal result = formula.getResult();

				// Calculate the last value received. If it's 0 set it to 1,
				// because we're going to divide by it.
				double lastValue = (lastResult.doubleValue() != 0) ? lastResult
						.doubleValue() : 1;
				// Calculate the rate of change between the last two values.
				double rateOfChange = Math
						.abs((result.doubleValue() - lastResult.doubleValue())
								/ lastValue);

				// If the rate of change is not ridiculously high, put it in the
				// results data set.
				if (rateOfChange < 10000.0
						|| Math.abs(result.doubleValue()
								- lastResult.doubleValue()) < 1)
					results.put(i, result);
				// If the rate of change is abnormally high, this is likely to
				// be approaching an asymptote.
				else
					results.put(i, null);

				lastResult = result;
			} catch (SecurityException | InvalidParameterException sEx) {
				
				JOptionPane.showMessageDialog(frame,
						"A problem has occurred with the plugin which supports this function.",
						"Plugin Issue", JOptionPane.ERROR_MESSAGE);
				
				System.err.println(sEx.getMessage());
				setCursor(Cursor.getDefaultCursor());
				return;
			} catch (FormulaException formEx) {
				
				JOptionPane.showMessageDialog(frame,
						formEx.getMessage(),
						"Formula", JOptionPane.ERROR_MESSAGE);
				
				System.err.println(formEx.getMessage());
				setCursor(Cursor.getDefaultCursor());
				return;
				
			} catch (NullPointerException nEx) {
				results.put(i, null);
			}
		}

		// We need to make sure we plot the points for every increment of pi/2.
		// Eg. pi/2, 3pi/2, 5pi/2 etc...
		// This is to find the asymptotes in tan. Therefore, we find each
		// increment of pi/2 between the values entered.
		double firstVal = fromValue - (fromValue % (Math.PI / 2));
		if (firstVal % Math.PI == 0)
			firstVal += Math.PI / 2;

		// Loop through each increment of pi/2 between the from and to values.
		for (i = firstVal; i <= toValue; i += (Math.PI / 2)) {
			vals.put(formula.getXAxis(), i);
			// Get the result and add it to the hash map.
			try {
				BigDecimal result = formula.getResult();

				// Calculate the last value received. If it's 0 set it to 1,
				// because we're going to divide by it.
				double lastValue = (lastResult.doubleValue() != 0) ? lastResult
						.doubleValue() : 1;
				// Calculate the rate of change between the last two values.
				double rateOfChange = Math
						.abs((result.doubleValue() - lastResult.doubleValue())
								/ lastValue);

				// If the rate of change is not ridiculously high, put it in the
				// results data set.
				if (rateOfChange < 10000.0
						|| Math.abs(result.doubleValue()
								- lastResult.doubleValue()) < 1)
					results.put(i, result);
				// If the rate of change is abnormally high, this is likely to
				// be approaching an asymptote.
				else
					results.put(i, null);

			} catch (Exception e1) {
				results.put(i, null);
			}
		}

		setCursor(Cursor.getDefaultCursor());

		// Update the chart and allow the graph to be saved as a PNG.
		chart.updateChart(inputField.getText(), formula.getXAxis(),
				formula.getYAxis(), results);
		saveGraphItem.setEnabled(true);
	}

	/**
	 * Multiple threaded objects will notify this {@link GUI} instance when 
	 * they finish working. Handle redirecting the output of these tasks to the
	 *  correct method here.
	 *  
	 * @param data The object sent by the notifying {@link IObservable}.
	 */
	@Override
	public void update(Object data) {

		IFormula formula;

		// Attempt to cast the received object to a Formula.
		try {
			formula = (Formula) data;
		} catch (ClassCastException castEx) {
			processLexing(data);
			return;
		}

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		for(IExtensionPlugin plugin : loadedPlugins) {
			plugin.setFormula(formula);
			final Future<Void> output = executor.submit(plugin);
			executor.schedule(new Runnable(){
			     public void run(){
			         output.cancel(true);
			     }      
			 }, 10000, TimeUnit.MILLISECONDS);
		}
		
		processEvaluation(formula);
	}
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				// If on a Mac, make it use the system menu bar.
				try {
					System.setProperty("apple.laf.useScreenMenuBar", "true");
					System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Maths");
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
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
