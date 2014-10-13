package uk.ac.uea.mathsthing;

import java.security.InvalidParameterException;
import java.util.HashMap;

/**
 * Provides methods to handle mathematical functions in a provided formula.
 * 
 * @author Jordan Woerner
 * @version 0.1
 */
public final class Functions {

	/** A collection of the functions the class currently understands. */
	protected static final String[] supportedFunctions = {
		"sin", "cos", "tan", 
		"floor", "ceil", "round", 
		"log", "ln", 
		"fact", 
		"sqrt"
	};
	
	/** The formula lexing implementation to use when processing parameters. */
	protected static IFormulaLexer lexer = null;
	/** The formula parser to use when evaluating parameters. */
	protected static IFormulaParser parser = null;
	
	/**
	 * Set the {@link IFormulaLexer} to use when processing parameters that are
	 *  passed to the {@link Functions#processFunction(String)} method.
	 * 
	 * @param newLexer The lexer to use when processing formulae.
	 * @since 0.1
	 */
	public static final void setFormulaLexer(IFormulaLexer newLexer) {
		Functions.lexer = newLexer;
	}
	
	/**
	 * Set the {@link IFormulaParser} to use when evaluating parameters that 
	 * are passed to the {@link Functions#processFunction(String)} method.
	 * 
	 * @param newParser The parser to use when evaluating formulae.
	 * @since 0.1
	 */
	public static final void setFormulaParser(IFormulaParser newParser) {
		Functions.parser = newParser;
	}
	
	/**
	 * Returns an array of the supported mathematical functions.
	 * 
	 * @return An array of Strings representing the names of the supported 
	 * functions.
	 * @since 0.1
	 */
	public static final String[] getSupportedFunctions() {
		return Functions.supportedFunctions;
	}
	
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
	public static final boolean isSupported(String funcName) 
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
	 * @param function The function to process as a {@link String}.
	 * @return Returns a double containing the result of the function.
	 * @throws Exception Thrown if there is an error evaluating the 
	 * formula this function works on.
	 * @since 0.1
	 */
	public static final double processFunction(String function, HashMap<String, Double> params)
			throws Exception {
		
		if (function == null || function.isEmpty()) 
			throw new InvalidParameterException("A function must be provided.");
		
		String funcName = function.substring(0, function.indexOf('('));
		String parameter = function.substring(function.indexOf('(')+1, function.lastIndexOf(')'));
		
		if (parameter == null || parameter.isEmpty()) 
			throw new InvalidParameterException("A paramter must be provided.");
			
		if (!Functions.isSupported(funcName))
			throw new UnsupportedOperationException("Function not supported.");
		
		parser.setFormula(lexer.tokenize(parameter));
		double result = 0.0;
		try {
			result = parser.getResult(params);
		} catch (Exception e) {
			throw e;
		}
		// Execute the function specified.
		switch (funcName) {
			case "sin":
				return Math.sin(result);
			case "cos":
				return Math.cos(result);
			case "tan":
				return Math.tan(result);
			case "floor":
				return Math.floor(result);
			case "ceil":
				return Math.ceil(result);
			case "round":
				return Math.round(result);
			case "log":
				return Math.log10(result);
			case "ln":
				return Math.log(result);
			case "fact":
				return Functions.fact((int)Math.round(result));
			case "sqrt":
				return Math.sqrt(result);
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
	 * @since 0.1
	 */
	public static final double fact(int n) {
		
		if (n <= 1) {
			return 1.0;
		} else {
			return n * fact (n - 1);
		}
	}
}
