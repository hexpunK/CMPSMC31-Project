package uk.ac.uea.mathsthing;

/**
 * Represents a component of a formula as either an {@link TokenType#OPERATOR}, 
 * {@link TokenType#OPERAND}, {@link TokenType#CONSTANT}, or {@link TokenType#FUNCTION}. 
 * Each Token contains a String of the component it represents.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public enum TokenType {

	/** Mathematical functions such as sin, cos, floor, etc. */
	FUNCTION(Functions.functionRegex),
	/** Mathematical operators such as addition, subtraction, etc. */
	OPERATOR("([*|/|+|\\-|(|)|=|^])"),
	/** Replaceable values such as x, y, s, t, etc. */
	OPERAND("([a-z])"),
	/** Numeric values. */
	CONSTANT("([0-9]+\\.?[0-9]*)");
	
	/** The value this Token represents. */
	private String pattern;
	
	/**
	 * Creates a new {@link TokenType} with the specified {@link String} as the 
	 * stored value.
	 * 
	 * @param pattern The regex pattern to use for this Token.
	 * @since 1.0 
	 */
	private TokenType(String pattern)
	{
		this.pattern = pattern;
	}
	
	/**
	 * Gets the value this Token represents.
	 * 
	 * @return The value of this Token as a {@link String}.
	 * @since 1.0
	 */
	public String getToken()
	{
		return this.pattern;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s(%s)", this.name(), this.pattern);
	}
}
