package uk.ac.uea.mathsthing;

import java.math.BigDecimal;

/**
 * Describes a plugin that contains a mathematical constant. Requires a name  
 * to be defined, and the value of the constant to be returned.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public interface IConstantPlugin {

	/**
	 * Gets the name of this {@link IConstantPlugin} to be used when evaluating 
	 * a formula.
	 * 
	 * @return A {@link String} containing the name of this 
	 * {@link IConstantPlugin}.
	 * @since 1.0
	 */
	public String getName();
	
	/**
	 * The constant to return. Returns the result to an arbitrary precision  
	 * using {@link BigDecimal}.
	 * 
	 * @return A {@link BigDecimal} containing the result from the function.
	 * @since 1.0
	 */
	public BigDecimal getValue();
}
