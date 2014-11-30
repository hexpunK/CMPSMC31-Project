package uk.ac.uea.mathsthing.plugins.extensions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import uk.ac.uea.mathsthing.IPlugin.IExtensionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

import org.apache.commons.math3.analysis.solvers.BracketingNthOrderBrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.apache.commons.math3.analysis.UnivariateFunction;

/**
 * Plugin for calculating the root of a function. Currently, it can provide 2 roots at maximum
 * and uses the Brent-Dekker algorithm to calculate roots. 
 * 
 * @author Jake Ruston
 * @version 1.0
 */
public class RootPlugin extends IExtensionPlugin {
	
	JMenuItem button;
	
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
		button.setEnabled(formula.toString().length() > 0);
	}
	
	private void createGUI()
	{
		final JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		// Uses the Brent-Dekker algorithm to calculate roots.
		RootCalculate f = new RootCalculate();
		final double relativeAccuracy = 1.0e-12;
		final double absoluteAccuracy = 1.0e-8;
		final int maxOrder = 5;
		UnivariateSolver solver = new BracketingNthOrderBrentSolver(relativeAccuracy, absoluteAccuracy, maxOrder);
		
		try {
			double a = solver.solve(2000, f, -1000, 1000);
			DecimalFormat df = new DecimalFormat("#.####"); 
			String firstRoot = df.format(a);
			
			// Okay so we've calculated one root, now we should search to see if we can find another.
			try {
				double b = solver.solve(2000, f, a+0.0001, 1000);
				String secondRoot = df.format(b);
				JOptionPane.showMessageDialog(window, "The roots for this formula are "+firstRoot+" and "+secondRoot+".",
						"Root Finder", JOptionPane.INFORMATION_MESSAGE);
				
			// If not, there must only be one root for this formula.
			} catch (Exception e) {
				JOptionPane.showMessageDialog(window, "The root of this formula is "+firstRoot+".",
						"Root Finder", JOptionPane.INFORMATION_MESSAGE);
			}
			
		// We could not calculate the root. This could be because there is none. Eg. y = 1000.
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(window, "Unable to calculate the root for this formula.",
					"Root Finder", JOptionPane.ERROR_MESSAGE);
			return;
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
			} catch (Exception e) {
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
