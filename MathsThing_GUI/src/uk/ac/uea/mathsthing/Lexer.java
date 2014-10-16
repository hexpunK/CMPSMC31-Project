package uk.ac.uea.mathsthing;
import java.util.ArrayList;

public class Lexer implements IFormulaLexer{
	
	private String input;
	private String userEquation;
	//add stuff for parameters later
	private ArrayList<String> equation;
	
	public Lexer()
	{
		input="";
		userEquation = "";
		equation = new ArrayList<>();
	}
	
	public Lexer(String in)
	{
		input = in;
		userEquation = "";
		equation = new ArrayList<>();
	}

	/**
	 * Get the formula the user provided to this instance, as written before 
	 * any processing was performed.
	 * 
	 * @return The formula provided by the user as a String.
	 */
	@Override
	public String getUserFormula() {
		
		int i = input.indexOf(",");
		userEquation = input.substring(0, i);
		return userEquation;
	}

	/**
	 * Get the formula the user provided once it has been processed to include 
	 * any extra operators that the parser will need (such as implied 
	 * multiplications).
	 * 
	 * @return A String containing the processed formula.
	 */
	@Override
	public String getProccessedFormula() {
		String orig = this.userEquation;
		orig.trim();
		orig.replaceAll(" ", "");
		
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
            }else if (orig.startsWith("sin")) //function time
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
		
		//put it all back together as a string
		String finalEquation = "";
		for(int i=0; i < equation.size(); i++)
		{
			finalEquation += equation.get(i);
		}
		return finalEquation;
	}

	/**
	 * Analyse and tokenise a provided formula. The tokens will contain 
	 * constants, paramters, functions and operators.
	 * 
	 * @param formula The formula to tokenise.
	 * @return A String array containing all the tokens found in the formula.
	 */
	@Override
	public String[] tokenize(String formula) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
