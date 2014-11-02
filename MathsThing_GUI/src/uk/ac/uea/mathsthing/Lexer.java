package uk.ac.uea.mathsthing;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lexer implements IFormulaLexer{
	
	private String input;
	private String userEquation;
	private Token[] tokens; //change from String to Token in a bit
	//add stuff for parameters later
	private ArrayList<Token> equation;
	private HashMap<String, Integer> parameters;
	
	public Lexer()
	{
		input="";
		userEquation = "";
		tokens = new Token[0];
		equation = new ArrayList<>();
		parameters = new HashMap<String, Integer>();
	}
	
	public Lexer(String in)
	{
		input = in;
		userEquation = "";
		tokens = new Token[0];
		equation = new ArrayList<>();
		parameters = new HashMap<String, Integer>();
	}

	@Override
	public String getUserFormula() {
		
		int i = input.indexOf(",");
		if (i >= 0)
			userEquation = input.substring(0, i);
		else
			userEquation = input;
		return userEquation;
	}

	@Override
	public String getProccessedFormula() {
		
		StringBuilder output = new StringBuilder();
		
		for(Token token : tokens) {
			output.append(token);
		}
		
		return output.toString();
	}

	@Override
	public Token[] tokenize(String formula) {

		this.input = formula;
		String orig = getUserFormula();
		orig.trim();
		orig = orig.replaceAll("\\s+", "");
		
		//create the patter to match
		Pattern p1 = Pattern.compile(TokenType.CONSTANT.getToken() + "|" + TokenType.OPERATOR.getToken() +
				"|" + TokenType.OPERAND.getToken() + "|" + TokenType.FUNCTION.getToken());
		
		//set up the matcher using the pattern created
		Matcher mat = p1.matcher(orig);
		
		while(mat.find())
		{
			//constant
			if(mat.group(TokenType.CONSTANT.ordinal()+1) != null) //matcher indexes groups from 1
			{
				Token t = new Token(mat.group(TokenType.CONSTANT.ordinal()+1), TokenType.CONSTANT);
				equation.add(t);
			}
			//operator
			if(mat.group(TokenType.OPERATOR.ordinal()+1) != null)
			{
				Token t = new Token(mat.group(TokenType.OPERATOR.ordinal()+1), TokenType.OPERATOR);
				equation.add(t);
			}
			//operand
			if(mat.group(TokenType.OPERAND.ordinal()+1) != null)
			{
				Token t = new Token(mat.group(TokenType.OPERAND.ordinal()+1), TokenType.OPERAND);
				equation.add(t);
			}
			//function
			if(mat.group(TokenType.FUNCTION.ordinal()+1) != null)
			{
				Token t = new Token(mat.group(TokenType.FUNCTION.ordinal()+1), TokenType.FUNCTION);
				equation.add(t);
			}
		}
		
		this.tokens = equation.toArray(this.tokens);
		
		return this.tokens;
	}
	
	@Override
	public Token[] getTokens() {
		
		return this.tokens;
	}
	

}
