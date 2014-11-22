package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import uk.ac.uea.mathsthing.util.FormulaException;

/**
 * Describes a plugin that contains a mathematical constant. Requires a name  
 * to be defined, and the value of the constant to be returned.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public abstract class IConstantPlugin implements Callable<BigDecimal> {
	
	/**
	 * Gets the name of this {@link IConstantPlugin} to be used when evaluating 
	 *  a formula.
	 * 
	 * @return A {@link String} containing the name of this 
	 * {@link IConstantPlugin}.
	 * @since 1.0
	 */
	public abstract String getName();
	
	/**
	 * The constant to return. Returns the result to an arbitrary precision  
	 * using {@link BigDecimal}.
	 * 
	 * @return A {@link BigDecimal} containing the result from the function.
	 * @since 1.0
	 */
	public abstract BigDecimal getValue();
	
	/**
	 * Runs the plugin code in a protected environment to prevent it from 
	 * having access to certain JVM functions that would risk the safety of 
	 * the program.
	 * 
	 * @return The result of {@link IConstantPlugin#getValue()}.
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
		result = getValue();
		
		return result;
	}
}
