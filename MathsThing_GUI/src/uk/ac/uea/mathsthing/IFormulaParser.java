package uk.ac.uea.mathsthing;

import java.util.HashMap;

/**
 * Provides methods to interact with the parser. Allows a tokenised formula to 
 * be provided to the parser, and lets the calling code retrieve various 
 * results from working on the tokens.
 * 
 * @author Jordan Woerner
 * @version 0.1
 */
public interface IFormulaParser {

	/**
	 * Sets the formula that this parser should work on to a specifed array of 
	 * tokens.
	 * 
	 * @param tokenised A String array of the required formula tokens.
	 */
	public void setFormula(String[] tokenised);
	
	/**
	 * Gets the resulting number from using the stored formula with the 
	 * provided parameter values replacing the parameter names in the tokens.    
	 * 
	 * @param params A mapping of the parameter token names to their required 
	 * values. A parameter can only be mapped in this set once.
	 * @return A floating point value from the result of the computation.
	 */
	public double getResult(HashMap<String, Double> params);
	
	/**
	 * Gets the first derivative of the stored formula.
	 * 
	 * @return A String representing the first derivative of the formula.
	 */
	public String getFirstDerivative();
	
	/**
	 * Gets the second derivative of the store formula.
	 * 
	 * @return A String representing the second derivative of the formula.
	 */
	public String getSecondDerivative();
}
