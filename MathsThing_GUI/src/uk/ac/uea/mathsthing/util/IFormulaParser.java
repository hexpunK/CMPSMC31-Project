package uk.ac.uea.mathsthing.util;

import uk.ac.uea.mathsthing.Token;

/**
 * Provides methods to interact with the parser. Allows a tokenised formula to 
 * be provided to the parser, and lets the calling code retrieve various 
 * results from working on the tokens.
 * 
 * @author Jordan Woerner
 * @version 1.0
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
	 * Parses the formula provided.
	 * 
	 * @return Returns the newly created {@link IFormula}.
	 * @since 1.0
	 */
	public IFormula parse();
}
