package uk.ac.uea.mathsthing.util;

import java.util.HashMap;

import uk.ac.uea.mathsthing.Token;

/**
 * Provides methods to interact with the parser. Allows a tokenised formula to 
 * be provided to the parser, and lets the calling code retrieve various 
 * results from working on the tokens.
 * 
 * @author Jordan Woerner
 * @version 1.1
 */
public interface IFormulaParser extends IObservable {

	/**
	 * Sets the formula that this parser should work on to a specified array of 
	 * tokens.
	 * 
	 * @param tokenised A {@link Token} array of the required formula tokens.
	 * @since 1.0
	 */
	public void setFormula(Token[] tokenised);
	
	/**
	 * Sets the parameter {@link HashMap} that this {@link IFormulaParser} can 
	 * use to work out the axis labels for the current formula being parsed.
	 * 
	 * @param params A {@link HashMap} of {@link String} to {@link Double} for 
	 * each parametrised value in the function.
	 * @since 1.1
	 */
	public void setParameters(HashMap<String, Double> params);
	
	/**
	 * Parses the formula provided.
	 * 
	 * @return Returns the newly created {@link IFormula}.
	 * @since 1.0
	 */
	public IFormula parse();
}
