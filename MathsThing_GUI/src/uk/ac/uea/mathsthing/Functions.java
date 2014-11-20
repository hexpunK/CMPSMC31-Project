package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.regex.Matcher;

/**
 * Provides methods to handle mathematical functions in a provided formula.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public final class Functions {

	/** 
	 * A collection of the functions the class currently understands.
	 * 
	 * @since 1.0
	 */
	public static final String[] SUPPORTED_FUNCTIONS = {
		"sinh", "cosh", "tanh",
		"sin", "cos", "tan",
		"floor", "ceil", "round", 
		"log", "ln", 
		"fact", 
		"sqrt"
	};
	
	/** 
	 * A {@link String} containing a Regex pattern of all supported 
	 * functions. Allows for known functions to be captured by {@link Matcher}.
	 * 
	 *  @since 1.0
	 */
	public static final String functionRegex;
	
	static {
		// Initialise the regex.
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (int i = 0; i < Functions.SUPPORTED_FUNCTIONS.length; i++) {
			sb.append(Functions.SUPPORTED_FUNCTIONS[i]);
			if (i < Functions.SUPPORTED_FUNCTIONS.length - 1) {
				sb.append("|");
			}
		}
		sb.append(")");
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
	public static final boolean isSupported(final String funcName) 
			throws InvalidParameterException
	{		
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
	public static final BigDecimal processFunction(final String funcName, 
			final BigDecimal result) throws Exception
	{		
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
				BigDecimal bd = new BigDecimal(Math.tan(dResult));
				// This is to stop abnormally large results being returned as a result of the asymptote.
				if (bd.doubleValue() > 100 || bd.doubleValue() < -100) return null;
				else return bd;
			case "tanh":
				return new BigDecimal(Math.tanh(dResult));
			case "floor":
				return new BigDecimal(Math.floor(dResult));
			case "ceil":
				return new BigDecimal(Math.ceil(dResult));
			case "round":
				return new BigDecimal(Math.round(dResult));
			case "log":
				if (dResult <= 0 || dResult == Double.NaN)
					throw new Exception("log only accepts positive numbers.");
				return new BigDecimal(Math.log10(dResult));
			case "ln":
				if (dResult <= 0 || dResult == Double.NaN)
					throw new Exception("ln only accepts positive numbers.");
				return new BigDecimal(Math.log(dResult));
			case "fact":
				if (dResult <= 0 || dResult == Double.NaN)
					throw new Exception("fact only accepts positive numbers.");
				return new BigDecimal(Functions.fact((int)Math.round(dResult)));
			case "sqrt":
				if (dResult <= 0 || dResult == Double.NaN)
					throw new Exception("sqrt only accepts positive numbers.");
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
	public static final double fact(final int n)
	{		
		if (n <= 1) {
			return 1.0;
		} else {
			return n * fact (n - 1);
		}
	}
}
