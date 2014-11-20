package uk.ac.uea.mathsthing.util;

import java.util.HashMap;

import uk.ac.uea.mathsthing.Token;

/**
 * Provides methods that will allow a lexer to accept and tokenise a provided 
 * mathematical formula. Calling {@link IFormulaLexer#tokenize()} will 
 * create a String array containing the tokens found in a formula.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public interface IFormulaLexer extends IObservable {

	/**
	 * Get the formula the user provided to this instance, as written before 
	 * any processing was performed.
	 * 
	 * @return The formula provided by the user as a String.
	 * @since 1.0
	 */
	public String getUserFormula();
	
	/**
	 * Get the formula the user provided once it has been processed to include 
	 * any extra operators that the parser will need (such as implied 
	 * multiplications).
	 * 
	 * @return A String containing the processed formula.
	 * @since 1.0
	 */
	public String getProccessedFormula();
	
	/**
	 * Sets the formula to be tokenised.
	 * 
	 * @param formula A String representation of the formula.
	 * @since 1.0
	 */
	public void setForumla(String formula);
	
	/**
	 * Analyse and tokenise a provided formula. The tokens will contain 
	 * constants, paramters, functions and operators.
	 * 
	 * @return A String array containing all the tokens found in the formula.
	 * @since 1.0
	 */
	public Token[] tokenize();
	
	/**
	 * Returns the tokens created by this lexer if they exist. The tokens will 
	 * contain constants, paramters, functions and operators.
	 * 
	 * @return Returns a {@link String} array containing the tokens of a 
	 * processed formula.
	 * @since 1.0
	 */
	public Token[] getTokens();
	
	/**
	 * Returns a mapping of parameters found in a formula. Mapping contains a 
	 * mapping of a operand to a constant to replace it with.
	 * 
	 * @return A {@link HashMap} of {@link String} to {@link Double} for each 
	 * parameter found in the formula being parsed.
	 * @since 1.0
	 */
	public HashMap<String, Double> getParameters();
}
