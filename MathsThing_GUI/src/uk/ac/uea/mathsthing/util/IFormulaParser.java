package uk.ac.uea.mathsthing.util;

import java.math.BigDecimal;
import java.util.HashMap;

import uk.ac.uea.mathsthing.Token;

/**
 * Provides methods to interact with the parser. Allows a tokenised formula to 
 * be provided to the parser, and lets the calling code retrieve various 
 * results from working on the tokens.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public interface IFormulaParser extends IObservable {

	/**
	 * Sets the formula that this parser should work on to a specified array of 
	 * tokens.
	 * 
	 * @param tokenised A {@link Token} array of the required formula tokens.
	 * @since 1.0
	 */
	public void setFormula(Token[] tokenised);
	
	/**
	 * Parses the formula provided.
	 * 
	 * @return Returns the newly created {@link IFormula}.
	 * @since 1.0
	 */
	public IFormula parse();
	
	/**
	 * Gets the resulting number from using the stored formula with the 
	 * provided parameter values replacing the parameter names in the tokens.    
	 * 
	 * @param params A mapping of the parameter token names to their required 
	 * values. A parameter can only be mapped in this set once.
	 * @return A floating point value from the result of the computation.
	 * @throws Exception Thrown if there is an error when evaluating the 
	 * formula.
	 * @since 1.0
	 */
	public BigDecimal getResult(HashMap<String, Double> params) 
			throws Exception;
	
	/**
	 * Gets the first derivative of the stored formula.
	 * 
	 * @return A {@link IFormula} representing the first derivative of the 
	 * formula.
	 * @since 1.0
	 */
	public IFormula getFirstDerivative();
	
	/**
	 * Gets the second derivative of the store formula.
	 * 
	 * @return A {@link IFormula} representing the second derivative of the 
	 * formula.
	 * @since 1.0
	 */
	public IFormula getSecondDerivative();
}
