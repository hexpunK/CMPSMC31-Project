package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import uk.ac.uea.mathsthing.util.FormulaException;

/**
 * Describes a plugin that performs a mathematical function. Requires a name 
 * to be defined, and the function itself.
 * 
 * @author Jordan Woerner
 * @version 1.1
 */
public abstract class IFunctionPlugin implements Callable<BigDecimal> {
	
	/** The value to run the function this plugin performs on. */
	private BigDecimal input = new BigDecimal(0);
	
	final void setInput(BigDecimal input) { this.input = input; }
	
	/**
	 * Gets the name of this {@link IFunctionPlugin} to be used when evaluating 
	 * a formula.
	 * 
	 * @return A {@link String} containing the name of this 
	 * {@link IFunctionPlugin}.
	 * @since 1.0
	 */
	public abstract String getName();
	
	/**
	 * The function to perform. Returns the result to an arbitrary precision 
	 * using {@link BigDecimal}.
	 * 
	 * @param input The number to work on in the function as a 
	 * {@link BigDecimal}.
	 * @return A {@link BigDecimal} containing the result from the function.
	 * @throws FormulaException Throw this Exception if there are any errors 
	 * during the execution of this {@link IFunctionPlugin}.
	 * @since 1.0
	 */
	public abstract BigDecimal function(BigDecimal input) 
			throws FormulaException;
	
	/**
	 * Runs the plugin code in a protected environment to prevent it from 
	 * having access to certain JVM functions that would risk the safety of 
	 * the program.
	 * 
	 * @return Returns the result of this {@link IFunctionPlugin} as a 
	 * {@link BigDecimal}.
	 * @throws FormulaException Thrown if there are any issues executing the 
	 * function.
	 * @throws SecurityException Thrown if the {@link SecurityManager} rejects 
	 * a request for a permission.
	 * @since 1.1
	 */
	public final BigDecimal call() throws FormulaException, SecurityException
	{
		// Run the plugin code.
		BigDecimal result = null;
		result = function(this.input);
		
		return result;
	}
}
