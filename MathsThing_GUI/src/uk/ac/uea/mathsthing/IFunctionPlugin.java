package uk.ac.uea.mathsthing;

import java.math.BigDecimal;

/**
 * Describes a plugin that performs a mathematical function. Requires a name 
 * to be defined, and the function itself.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public interface IFunctionPlugin {
	
	/**
	 * Gets the name of this {@link IFunctionPlugin} to be used when evaluating 
	 * a formula.
	 * 
	 * @return A {@link String} containing the name of this 
	 * {@link IFunctionPlugin}.
	 * @since 1.0
	 */
	public String getName();
	
	/**
	 * The function to perform. Returns the result to an arbitrary precision 
	 * using {@link BigDecimal}.
	 * 
	 * @param input The number to work on in the function as a 
	 * {@link BigDecimal}.
	 * @return A {@link BigDecimal} containing the result from the function.
	 * @throws Exception Throw this Exception if there are any errors during 
	 * the execution of this {@link IFunctionPlugin}.
	 * @since 1.0
	 */
	public BigDecimal function(BigDecimal input) throws Exception;
}
