package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Provides the required methods for a representation of a mathematical 
 * formula.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public interface IFormula {

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
	 * @param params A mapping of operands to constant values.
	 * @return Returns a {@link BigDecimal} representation of the result.
	 * @throws Exception Thrown if there are any errors processing the stored 
	 * formula.
	 * @since 1.0
	 */
	public BigDecimal getResult(HashMap<String, Double> params) 
			throws Exception;
	
	/**
	 * Gets the derivative for this {@link IFormula}.
	 * 
	 * @return Returns a new {@link IFormula} instance representing the 
	 * derivative of this {@link IFormula}.
	 * @since 1.0
	 */
	public IFormula getDerivative();
}
