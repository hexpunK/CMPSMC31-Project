package uk.ac.uea.mathsthing;

import java.security.InvalidParameterException;

/**
 * Provides methods to handle mathematical functions in a provided formula.
 * 
 * @author Jordan Woerner
 * @version 0.1
 */
public class Functions {

	/** A collection of the functions the class currently understands. */
	protected static final String[] supportedFunctions = {
		"sin", "cos", "tan", 
		"floor", "ceil", "round", 
		"log", "ln", 
		"fact", 
		"sqrt"
	};
	
	/**
	 * Checks to see if the specified function is supported before it is 
	 * processed.
	 * 
	 * @param funcName The function name as a {@link String}.
	 * @return Returns true if the function is supported, false otherwise.
	 * @throws InvalidParameterException Thrown if an empty or null function 
	 * name is passed.
	 * @since 0.1
	 */
	public static boolean isSupported(String funcName) 
			throws InvalidParameterException {
		
		if (funcName == null || funcName.isEmpty())
			throw new InvalidParameterException("A function name must be provided.");
		
		for (String func : supportedFunctions) {
			if (func.equals(funcName))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Processes a provided function and returns the output from the function. 
	 * If the parameter is a formula itself rather than a constant or number it
	 *  will be calculated before it is used.
	 * 
	 * @param funcName The name of the function to process as a {@link String}.
	 * @param parameter The value or formula provided to the function as a 
	 * {@link String}.
	 * @return Returns a double containing the result of the function.
	 * @throws InvalidParameterException Thrown if an empty or null String is 
	 * provided as a parameter.
	 * @throws UnsupportedOperationException Thrown if a unsupported function  
	 * is called.
	 * @since 0.1
	 */
	public static double processFunction(String funcName, String parameter)
			throws InvalidParameterException, UnsupportedOperationException {
		
		if (parameter == null || parameter.isEmpty()) 
			throw new InvalidParameterException("A paramter must be provided.");
			
		if (!Functions.isSupported(funcName))
			throw new UnsupportedOperationException("Function not supported.");
		
		// TODO: Add in code to process the parameter.
		
		switch (funcName) {
			case "sin":
				return Math.sin(0.0);
			case "cos":
				return Math.cos(0.0);
			case "tan":
				return Math.tan(0.0);
			case "floor":
				return Math.floor(0.0);
			case "ceil":
				return Math.ceil(0.0);
			case "round":
				return Math.round(0.0);
			case "log":
				return Math.log10(0.0);
			case "ln":
				return Math.log(0.0);
			case "fact":
				return Functions.fact(0);
			case "sqrt":
				return Math.sqrt(0.0);
			default:
				return 0.0;
		}
	}
	
	/**
	 * Calculates factorals. Can only be used for numbers that would return a 
	 * result that fits within a double. A minor limit, but it should be more 
	 * than enough for our needs.
	 * 
	 * @param n The number to compute the factoral of as an integer.
	 * @return The computed factoral as a double.
	 * @since 0.1
	 */
	public static double fact(int n) {
		
		if (n <= 1) {
			return 1;
		} else {
			return n * fact (n - 1);
		}
	}
}
