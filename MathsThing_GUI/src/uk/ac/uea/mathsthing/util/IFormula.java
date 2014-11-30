package uk.ac.uea.mathsthing.util;

import java.math.BigDecimal;
import java.util.HashMap;

import uk.ac.uea.mathsthing.Token;

/**
 * Provides the required methods for a representation of a mathematical 
 * formula.
 * 
 * @author Jordan Woerner
 * @version 1.2
 */
public interface IFormula {

	/**
	 * Sets the parameters that will be used when evaluating this 
	 * {@link IFormula}.
	 * 
	 * @param params A mapping of operands to constant values.
	 * @since 1.0
	 */
	public void setParameters(HashMap<String, Double> params);
	
	/**
	 * Retrieves the mapping of parameters stored in this {@link IFormula}.
	 * 
	 * @return A {@link HashMap} of {@link String} to {@link Double} containing
	 *  the paramters used in this formula.
	 * @since 1.2
	 */
	public HashMap<String, Double> getParameters();
	
	/**
	 * Gets the operand that represents the Y axis on a graph.
	 * 
	 * @return The y axis name as a {@link String}
	 * @since 1.0
	 */
	public String getYAxis();
	
	/**
	 * Gets the operand that represents the X axis on a graph.
	 * 
	 * @return The x axis name as a {@link String}
	 * @since 1.0
	 */
	public String getXAxis();
	
	/**
	 * Processes the stored formula, evaluating a result for the formula.
	 * 
	 * @return Returns a {@link BigDecimal} representation of the result.
	 * @throws FormulaException Thrown if there are any errors processing the 
	 * stored formula.
	 * @since 1.0
	 */
	public BigDecimal getResult() 
			throws FormulaException;
	
	/**
	 * Gets the derivative for this {@link IFormula}.
	 * 
	 * @return Returns a new {@link IFormula} instance representing the 
	 * derivative of this {@link IFormula}.
	 * @since 1.0
	 */
	public IFormula getDerivative();
	
	/**
	 * Gets the {@link Token} objects that represent this {@link IFormula} 
	 * internally.
	 * 
	 * @return Returns an array of {@link Token} objects.
	 * @since 1.1
	 */
	public Token[] getTokens();
}
