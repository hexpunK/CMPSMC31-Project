package uk.ac.uea.mathsthing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.uea.mathsthing.util.IFormulaLexer;
import uk.ac.uea.mathsthing.util.IObserver;

/**
 * Provides implementation of the Lexer Interface. This includes
 * tokenising a given formula including parameters
 * @author Laura Goold
 *
 */
public class Lexer implements IFormulaLexer, Runnable {
	
	private String input;
	private String userEquation;
	private Token[] tokens; //change from String to Token in a bit
	//add stuff for parameters later
	private ArrayList<Token> equation;
	private HashMap<String, Double> parameters;
	private IObserver observed;
	
	/**
	 * Constructor for Lexer class, intialises all class variables
	 */
	public Lexer()
	{
		input="";
		userEquation = "";
		tokens = new Token[0];
		equation = new ArrayList<>();
		parameters = null;
	}
	
	/**
	 * Constructor for Lexer class
	 * @param in String representing user input
	 */
	public Lexer(String in)
	{
		input = in;
		userEquation = "";
		tokens = new Token[0];
		equation = new ArrayList<>();
		parameters = null;
	}

	@Override
	public String getUserFormula() {
		
		int i = input.indexOf(":");
		if (i >= 0)
			userEquation = input.substring(0, i); //get equation from original input
		else
			userEquation = input; //no parameters so input just contains equation
		return userEquation;
	}

	@Override
	public String getProccessedFormula() {
		
		StringBuilder output = new StringBuilder();
		
		for(Token token : tokens) {
			output.append(token);
			//loop through all tokens adding to final string representation
		}
		
		return output.toString();
	}

	@Override
	public void setFormula(String formula) {
		this.input = formula;
	}
	
	@Override
	public Token[] tokenize() {

		String orig = getUserFormula();
		orig.trim();
		orig = orig.replaceAll("\\s+", ""); //remove all whitespace from formula
		
		// Build up a regex pattern.
		StringBuilder pattern = new StringBuilder();
		// If there are no functions or constants, we need to match against nothing.
		if (!Functions.functionRegex.equals("()"))
			pattern.append(TokenType.FUNCTION.getToken()).append("|");
		else
			pattern.append("($^)").append("|"); // Nothing!s
		
		if (!Constants.constantRegex.equals("()"))
			pattern.append(TokenType.MAGICNUM.getToken()).append("|");
		else
			pattern.append("($^)").append("|");
		// These will always have something to match against however.
		pattern.append(TokenType.OPERATOR.getToken()).append("|");
		pattern.append(TokenType.OPERAND.getToken()).append("|");
		pattern.append(TokenType.CONSTANT.getToken());
		
		//create the patter to match
		Pattern p1 = Pattern.compile(pattern.toString());
		
		//set up the matcher using the pattern created
		Matcher mat = p1.matcher(orig);
		
		//loop through all matches, convert them to tokens and add to the tokenised equation
		while(mat.find())
		{
			//function
			if(mat.group(TokenType.FUNCTION.ordinal()+1) != null)
			{
				Token t = new Token(mat.group(TokenType.FUNCTION.ordinal()+1), TokenType.FUNCTION);
				equation.add(t);
			}
			//mathematical constants
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
		
		if (this.parameters == null) {
			this.parameters = new HashMap<>();
			int index = input.indexOf(":"); 
			if (index != -1) {
				String allP = input.substring(index+1, input.length()); //get list of parameters from original input
				allP = allP.replaceAll("\\s+", "");
				String[] params = allP.split(","); //separate the parameters1
			
				for(int i=0; i< params.length; i++)
				{
					String[] p = params[i].split("="); //split into letter and assigned value
					parameters.put(p[0], Double.valueOf(p[1]));
				}
			}
		}
		
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
		getParameters();
		update();
	}
}
