package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

/**
 * Provides methods to handle mathematical functions in a provided formula.
 * 
 * @author Jordan Woerner
 * @version 0.1
 */
public final class Functions {

	/** A collection of the functions the class currently understands. */
	public static final String[] SUPPORTED_FUNCTIONS = {
		"sin", "cos", "tan", 
		"sinh", "costh", "tanh",
		"floor", "ceil", "round", 
		"log", "ln", 
		"fact", 
		"sqrt"
	};
	
	public static final String functionRegex;
	
	static {
		StringBuilder sb = new StringBuilder();
		sb.append("\b");
		for (int i = 0; i < Functions.SUPPORTED_FUNCTIONS.length; i++) {
			sb.append(Functions.SUPPORTED_FUNCTIONS[i]);
			if (i < Functions.SUPPORTED_FUNCTIONS.length - 1) {
				sb.append("|");
			}
		}
		sb.append("\b");
		functionRegex = sb.toString();
	}
	
	/**
	 * Checks to see if the specified function is supported before it is 
	 * processed.
	 * 
	 * @param funcName The function name as a {@link String}.
	 * @return Returns true if the function is supported, false otherwise.
	 * @throws InvalidParameterException Thrown if an empty or null function 
	 * name is passed.
	 * @since 1.0
	 */
	public static final boolean isSupported(String funcName) 
			throws InvalidParameterException {
		
		if (funcName == null || funcName.isEmpty())
			throw new InvalidParameterException("A function name must be provided.");
		
		for (String func : SUPPORTED_FUNCTIONS) {
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
	 * @param funcName The name of the function to call as a String.
	 * @param result The result from the evaluation tree to process.
	 * @return Returns a double containing the result of the function.
	 * @throws Exception Thrown if there is an error evaluating the 
	 * formula this function works on.
	 * @since 1.0
	 */
	public static final BigDecimal processFunction(String funcName, BigDecimal result)
			throws Exception {
		
		double dResult = result.doubleValue();
		
		if (dResult >= Double.POSITIVE_INFINITY || dResult <= Double.NEGATIVE_INFINITY) {
			throw new Exception("Provided value is too large to use in functions.");
		}
		
		// Execute the function specified.
		switch (funcName) {
			case "sin":
				return new BigDecimal(Math.sin(dResult));
			case "sinh":
				return new BigDecimal(Math.sinh(dResult));
			case "cos":
				return new BigDecimal(Math.cos(dResult));
			case "cosh":
				return new BigDecimal(Math.cosh(dResult));
			case "tan":
				return new BigDecimal(Math.tan(dResult));
			case "tanh":
				return new BigDecimal(Math.tanh(dResult));
			case "floor":
				return new BigDecimal(Math.floor(dResult));
			case "ceil":
				return new BigDecimal(Math.ceil(dResult));
			case "round":
				return new BigDecimal(Math.round(dResult));
			case "log":
				return new BigDecimal(Math.log10(dResult));
			case "ln":
				return new BigDecimal(Math.log(dResult));
			case "fact":
				return new BigDecimal(Functions.fact((int)Math.round(dResult)));
			case "sqrt":
				return new BigDecimal(Math.sqrt(dResult));
			default:
				return result;
		}
	}
	
	/**
	 * Calculates factorial numbers. Can only be used for numbers that would 
	 * return a result that fits within a double. A minor limit, but it should 
	 * be more than enough for our needs.
	 * 
	 * @param n The number to compute the factorial of as an integer.
	 * @return The computed factorial as a double.
	 * @since 1.0
	 */
	public static final double fact(int n) {
		
		if (n <= 1) {
			return 1.0;
		} else {
			return n * fact (n - 1);
		}
	}
}
