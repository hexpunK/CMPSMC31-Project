package uk.ac.uea.mathsthing;

/**
 * Provides methods that will allow a lexer to accept and tokenise a provided 
 * mathematical formula. Calling {@link IFormulaLexer#tokenize(String)} will 
 * create a String array containing the tokens found in a formula.
 * 
 * @author Jordan Woerner
 * @version 0.1
 */
public interface IFormulaLexer {

	/**
	 * Get the formula the user provided to this instance, as written before 
	 * any processing was performed.
	 * 
	 * @return The formula provided by the user as a String.
	 */
	public String getUserFormula();
	
	/**
	 * Get the formula the user provided once it has been processed to include 
	 * any extra operators that the parser will need (such as implied 
	 * multiplications).
	 * 
	 * @return A String containing the processed formula.
	 */
	public String getProccessedFormula();
	
	/**
	 * Analyse and tokenise a provided formula. The tokens will contain 
	 * constants, paramters, functions and operators.
	 * 
	 * @param formula The formula to tokenise.
	 * @return A String array containing all the tokens found in the formula.
	 */
	public String[] tokenize(String formula);
}
