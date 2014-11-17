package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.regex.Matcher;

public class Constants {

	/** 
	 * A collection of the constants the class currently understands.
	 * 
	 * @since 1.0
	 */
	public static final String[] SUPPORTED_CONSTANTS = {
		"e", "pi"
	};
	
	/** 
	 * A {@link String} containing a Regex pattern of all supported 
	 * constants. Allows for known constant to be captured by {@link Matcher}.
	 * 
	 *  @since 1.0
	 */
	public static final String constantRegex;
	
	static {
		// Initialise the regex.
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (int i = 0; i < Constants.SUPPORTED_CONSTANTS.length; i++) {
			sb.append(Constants.SUPPORTED_CONSTANTS[i]);
			if (i < Constants.SUPPORTED_CONSTANTS.length - 1) {
				sb.append("|");
			}
		}
		sb.append(")");
		constantRegex = sb.toString();
	}
	
	/**
	 * Checks to see if the specified constant is supported before it is 
	 * processed.
	 * 
	 * @param constant The constant name as a {@link String}.
	 * @return Returns true if the constant is supported, false otherwise.
	 * @throws InvalidParameterException Thrown if an empty or null constant 
	 * name is passed.
	 * @since 1.0
	 */
	public static final boolean isSupported(String constant) 
			throws InvalidParameterException {
		
		if (constant == null || constant.isEmpty())
			throw new InvalidParameterException("A function name must be provided.");
		
		for (String func : SUPPORTED_CONSTANTS) {
			if (func.equals(constant))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Processes a provided constant and returns the approximated value of it.
	 * 
	 * @param constant The name of the constant to evaluate as a String.
	 * @return Returns a {@link BigDecimal} containing the value of the 
	 * constant.
	 * @throws Exception Thrown if there is an error evaluating this constant.
	 * @since 1.0
	 */
	public static final BigDecimal processConstant(String constant)
			throws Exception {
		
		// Execute the constant specified.
		switch (constant) {
			case "e":
				return new BigDecimal(Math.E);
			case "pi":
				return new BigDecimal(Math.PI);
			default:
				return new BigDecimal(0);
		}
	}
}
