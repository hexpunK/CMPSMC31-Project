package uk.ac.uea.mathsthing;

public class Token {

	public final TokenType type;
	public final String val;
	public final boolean urany;
	
	public Token(String val, TokenType type)
	{
		this.type = type;
		this.val = val;
		this.urany = false;
	}
	
	public Token(String val, TokenType type, boolean urany)
	{
		this.type = type;
		this.val = val;
		this.urany = urany;
	}
	
	public String getToken() { return this.val; }
	
	@Override
	public String toString() { return this.val; }
}
