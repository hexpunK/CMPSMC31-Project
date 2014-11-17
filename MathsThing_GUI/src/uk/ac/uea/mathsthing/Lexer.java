package uk.ac.uea.mathsthing;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.uea.mathsthing.util.IObserver;

public class Lexer implements IFormulaLexer, Runnable {
	
	private String input;
	private String userEquation;
	private Token[] tokens; //change from String to Token in a bit
	//add stuff for parameters later
	private ArrayList<Token> equation;
	private HashMap<String, Double> parameters;
	private IObserver observed;
	
	public Lexer()
	{
		input="";
		userEquation = "";
		tokens = new Token[0];
		equation = new ArrayList<>();
		parameters = new HashMap<>();
	}
	
	public Lexer(String in)
	{
		input = in;
		userEquation = "";
		tokens = new Token[0];
		equation = new ArrayList<>();
		parameters = new HashMap<>();
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
	public void setForumla(String formula) {
		this.input = formula;
	}
	
	@Override
	public Token[] tokenize() {

		String orig = getUserFormula();
		orig.trim();
		orig = orig.replaceAll("\\s+", "");
		
		//create the patter to match
		Pattern p1 = Pattern.compile(TokenType.FUNCTION.getToken() 
				+ "|" + TokenType.MAGICNUM.getToken() 
				+ "|" + TokenType.OPERATOR.getToken() 
				+ "|" + TokenType.OPERAND.getToken() 
				+ "|" + TokenType.CONSTANT.getToken());
		
		//set up the matcher using the pattern created
		Matcher mat = p1.matcher(orig);
		
		while(mat.find())
		{
			//function
			if(mat.group(TokenType.FUNCTION.ordinal()+1) != null)
			{
				Token t = new Token(mat.group(TokenType.FUNCTION.ordinal()+1), TokenType.FUNCTION);
				equation.add(t);
			}
			//function
			if(mat.group(TokenType.MAGICNUM.ordinal()+1) != null)
			{
				Token t = new Token(mat.group(TokenType.MAGICNUM.ordinal()+1), TokenType.MAGICNUM);
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
			//constant
			if(mat.group(TokenType.CONSTANT.ordinal()+1) != null) //matcher indexes groups from 1
			{
				Token t = new Token(mat.group(TokenType.CONSTANT.ordinal()+1), TokenType.CONSTANT);
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

	@Override
	public HashMap<String, Double> getParameters() {
		return this.parameters;
	}

	@Override
	public void attach(IObserver observable) {
		this.observed = observable;
	}

	@Override
	public void detach(IObserver observable) {
		this.observed = null;
	}

	@Override
	public void update() {
		observed.update(this);
	}

	@Override
	public void run() {
		tokenize();
		update();
	}
}
