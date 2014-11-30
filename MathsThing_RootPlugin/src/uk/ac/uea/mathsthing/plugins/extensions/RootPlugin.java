package uk.ac.uea.mathsthing.plugins.extensions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BracketingNthOrderBrentSolver;

import uk.ac.uea.mathsthing.IPlugin.IExtensionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

/**
 * Plugin for calculating the root of a function. Currently, it can calculate an unlimited
 * number of roots occurring between -1000 and 1000, and uses the Brent-Dekker algorithm
 * to calculate roots. 
 * 
 * @author Jake Ruston
 * @version 1.0
 */
public class RootPlugin extends IExtensionPlugin {
	
	JMenuItem button;
	ArrayList<Double> roots;
	
	@Override
	public String getName() { return "rootView"; }

	@Override
	public JMenuItem getMenuEntry()
	{		
		button = new JMenuItem("Root Finder");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createGUI();
			}
		});
		button.setEnabled(false);
		return button;
	}

	@Override
	public void processFormula() throws FormulaException {
		// Uses the Brent-Dekker algorithm to calculate roots.
		RootCalculate f = new RootCalculate();
		final double relativeAccuracy = 1.0e-12;
		final double absoluteAccuracy = 1.0e-8;
		final int maxOrder = 5;
		BracketingNthOrderBrentSolver solver = new BracketingNthOrderBrentSolver(relativeAccuracy, absoluteAccuracy, maxOrder);
				
		roots = new ArrayList<Double>();
		double lowestValue = -1000;
				
		// Loop through attempting to find all root solutions
		while (true) {		
			// When a root has been found, increment slightly and move on to find more roots.
			try {
				double a = solver.solve(4000, f, lowestValue, 1000);
				lowestValue = a+0.00000001;
				roots.add(Double.valueOf(a));
			} catch (Exception e) {
				break;
			}
		}
				
		button.setEnabled(formula.toString().length() > 0);
	}
	
	private void createGUI()
	{
		final JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		for (int i=0; i<formula.getTokens().length; i++) {
			
			if (formula.getTokens()[i].val.equals("sin")) {
				JOptionPane.showMessageDialog(window, "The roots of sin occur at all integer multiples of pi.",
						"Root Finder", JOptionPane.INFORMATION_MESSAGE);
				return;
				
			} else if (formula.getTokens()[i].val.equals("cos")) {
				JOptionPane.showMessageDialog(window, "The roots of cos occur at all integer multiples of pi/2.",
						"Root Finder", JOptionPane.INFORMATION_MESSAGE);
				return;
				
			} else if (formula.getTokens()[i].val.equals("tan")) {
				JOptionPane.showMessageDialog(window, "The roots of tan occur at all integer multiples of pi.",
						"Root Finder", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		
		// If there are no roots, throw an error message to the user.
		if (roots.isEmpty()) {
			JOptionPane.showMessageDialog(window, "Unable to find any roots between -1000 and 1000 for this formula.",
					"Root Finder", JOptionPane.ERROR_MESSAGE);
		} else {
			
			String rootsString = "";
			DecimalFormat df = new DecimalFormat("#.####"); 
			
			// Loop through all of the roots, creating a string with the list of them to show.
			for (int i=0; i<roots.size(); i++) {
				if (i>0) rootsString = rootsString + ", ";
				Double root = roots.get(i);
				rootsString = rootsString + df.format(root.doubleValue());
			}
			
			// If we have more than 1 root, then "roots" is plural.
			if (roots.size() > 1)
				JOptionPane.showMessageDialog(window, "The roots for this formula are "+rootsString+".",
					"Root Finder", JOptionPane.INFORMATION_MESSAGE);
			
			// If we have 1 root, then it is just one "root".
			else
				JOptionPane.showMessageDialog(window, "The root for this formula is "+rootsString+".",
						"Root Finder", JOptionPane.INFORMATION_MESSAGE);
		}
		
		window.setVisible(false);
	}
	
	/**
	 * A class which implements UnivariateFunction, required for the Brent-Dekker solver. It uses
	 * an instance of the parser to calculate solutions based on a variety of inputs, to find
	 * the correct roots of a formula.
	 * 
	 * @author Jake Ruston
	 * @version 1.0
	 */
	public class RootCalculate implements UnivariateFunction {

		@Override
		public double value(double input) {
			
			// Set the parameters for the parser.
			HashMap<String,Double> params = new HashMap<String,Double>();
			params.put(formula.getXAxis(), Double.valueOf(input));
			formula.setParameters(params);
			
			try {
				return formula.getResult().doubleValue();
			} catch (FormulaException e) {
				e.printStackTrace();
			}
			
			return -1;
		}
		
	}

	@Override
	public void onReset() {
		this.button.setEnabled(false);
	}
}
