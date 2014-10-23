package uk.ac.uea.mathsthing;
import java.util.ArrayList;

public class Lexer implements IFormulaLexer{
	
	private String input;
	private String userEquation;
	private String[] tokens;
	//add stuff for parameters later
	private ArrayList<String> equation;
	
	public Lexer()
	{
		input="";
		userEquation = "";
		tokens = new String[0];
		equation = new ArrayList<>();
	}
	
	public Lexer(String in)
	{
		input = in;
		userEquation = "";
		tokens = new String[0];
		equation = new ArrayList<>();
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
		
		for(String token : tokens) {
			output.append(token);
		}
		
		return output.toString();
	}

	@Override
	public String[] tokenize(String formula) {

		this.input = formula;
		String orig = getUserFormula();
		orig.trim();
		orig = orig.replaceAll("\\s+", "");
		
		//remove the start (y=) from equation and add to ArrayList
		int start = orig.indexOf("=")+1;
		equation.add("y");
		equation.add("=");
		orig = orig.substring(start);
		
		//work through string tokenising it
		while(orig.length() >0)
		{
			if(orig.matches("^[\\+\\-\\*\\^/\\(\\)].*$"))
            {
                equation.add(orig.substring(0, 1));
                //System.out.println(orig.substring(0, 1));
                orig = orig.substring(1);
                
            }else if(orig.matches("^\\d.*"))//checks if 1st character is a digit
            {
                String num = orig.substring(0, 1);
                orig = orig.substring(1);
                while(orig.matches("^\\d.*"))
                {
                    num += orig.substring(0,1);
                    orig = orig.substring(1);
                }
                equation.add(num);
                //System.out.println(num);
                if (orig.startsWith("(")) {
                    //implied multiplication
                    equation.add("*");
                    equation.add("(");
                    //System.out.println("*(");
                    orig = orig.substring(1);
                } else if (orig.startsWith("sin")) {
                    //implied multiplication
                    equation.add("*");
                    //System.out.println("* function");
                    //deal with function
                }else{
                    while(orig.matches("^[a-z].*"))
                    {
                        equation.add("*");
                        //System.out.println("*");
                        equation.add(orig.substring(0,1)); //gets 1st letter
                        //System.out.println(orig.substring(0,1));
                        orig = orig.substring(1);
                    }
                }
            }
            else if (orig.startsWith("sin")) //function time
            {
                //deal with le functions here
                System.out.println("function");
            }//else if (orig.startsWith("x")) { //what if xz
            else if(orig.matches("^[a-z].*"))
            {
                equation.add(orig.substring(0,1)); //gets 1st letter
                //System.out.println(orig.substring(0,1));
                orig = orig.substring(1);
                //System.out.println("Current string: "+orig);
                if (orig.startsWith("(")) {
                    equation.add("*");
                    equation.add("(");
                    //System.out.println("*(");
                    orig = orig.substring(1);
                } else if (orig.startsWith("sin"))//function
                {
                    equation.add("*");
                    //System.out.println("*function");
                    //deal with function or could leave that for later
                }else
                {
                    while(orig.matches("^[a-z].*"))
                    {
                        equation.add("*");
                        //System.out.println("*");
                        equation.add(orig.substring(0,1)); //gets 1st letter
                        //System.out.println(orig.substring(0,1));
                        orig = orig.substring(1);
                    }
                }
            }else
            {
                //fucked up somewhere
                equation.add(orig.substring(0,1));
                //System.out.println("Loop bottom");
                //System.out.println(orig.substring(0, 1));
                System.out.println("damn");
                orig = orig.substring(1);
            }

        }
		
		this.tokens = equation.toArray(this.tokens);
		
		return this.tokens;
	}

	public String[] getTokens() {
		
		return this.tokens;
	}
	

}
