package uk.ac.uea.mathsthing;
import java.util.ArrayList;
import java.util.HashMap;

public class Lexer implements IFormulaLexer{
	
	private String input;
	private String userEquation;
	//add stuff for parameters later
	private ArrayList<String> equation;
	private HashMap<String, Integer> parameters;
	
	public Lexer()
	{
		input="";
		userEquation = "";
		equation = new ArrayList<>();
		parameters = new HashMap<String, Integer>();
	}
	
	public Lexer(String in)
	{
		input = in;
		userEquation = "";
		equation = new ArrayList<>();
		parameters = new HashMap<String, Integer>();
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
	 * constants, parameters, functions and operators.
	 * 
	 * @param formula The formula to tokenise.
	 * @return A String array containing all the tokens found in the formula.
	 */
	@Override
	public String[] tokenize(String formula) {
		ArrayList<String> tokEq = new ArrayList<>();
		formula.trim();
		formula.replaceAll(" ", "");
		
		//get parameters from end of formula
		int start = formula.indexOf(",");
		String param = formula.substring(start);
		while(param.length()>0){
			int ind = param.indexOf(":"); 
			String p = param.substring(ind);
			String x, num;
			x="";
			num="";
			while(p.length()>0)
			{
				if(p.matches("^[a-z].*"))//1st character is a letter
				{
					x = p.substring(0, 1);//store character
					p = p.substring(1);//remove from string working on
				}else if(p.matches("^\\d.*"))//1st character is a number
				{
					num = p.substring(0, 1);
					p = p.substring(1);
					while(p.length()>0)
					{
						num += p.substring(0, 1);
						p = p.substring(1);
					}
				}else if(p.matches("^=.*"))
				{
					p = p.substring(1);
				}
				Integer val = Integer.parseInt(num);				
				parameters.put(x, val);
			}
			
		}
		
		//remove the start (y=) from equation and add to ArrayList
		start = formula.indexOf("=")+1;
		equation.add("y");
		equation.add("=");
		formula = formula.substring(start);
		
		//work through string tokenising it
		while(formula.length() >0)
		{
			if(formula.matches("^[\\+\\-\\*\\^/\\(\\)].*$"))
            {
                tokEq.add(formula.substring(0, 1));
                //System.out.println(formula.substring(0, 1));
                formula = formula.substring(1);
                
            }else if(formula.matches("^\\d.*"))//checks if 1st character is a digit
            {
                String num = formula.substring(0, 1);
                formula = formula.substring(1);
                while(formula.matches("^\\d.*"))
                {
                    num += formula.substring(0,1);
                    formula = formula.substring(1);
                }
                tokEq.add(num);
                //System.out.println(num);
                if (formula.startsWith("(")) {
                    //implied multiplication
                    tokEq.add("*");
                    tokEq.add("(");
                    //System.out.println("*(");
                    formula = formula.substring(1);
                } else if (formula.startsWith("sin")) {
                    //implied multiplication
                    tokEq.add("*");
                    //System.out.println("* function");
                    //deal with function
                }else{
                    while(formula.matches("^[a-z].*"))
                    {
                        tokEq.add("*");
                        //System.out.println("*");
                        tokEq.add(formula.substring(0,1)); //gets 1st letter
                        //System.out.println(formula.substring(0,1));
                        formula = formula.substring(1);
                    }
                }
            }else if (formula.startsWith("sin")) //function time
            {
                //deal with le functions here
                System.out.println("function");
            }//else if (formula.startsWith("x")) { //what if xz
            else if(formula.matches("^[a-z].*"))
            {
                tokEq.add(formula.substring(0,1)); //gets 1st letter
                //System.out.println(formula.substring(0,1));
                formula = formula.substring(1);
                //System.out.println("Current string: "+orig);
                if (formula.startsWith("(")) {
                    tokEq.add("*");
                    tokEq.add("(");
                    //System.out.println("*(");
                    formula = formula.substring(1);
                } else if (formula.startsWith("sin"))//function
                {
                    tokEq.add("*");
                    //System.out.println("*function");
                    //deal with function or could leave that for later
                }else
                {
                    while(formula.matches("^[a-z].*"))
                    {
                        tokEq.add("*");
                        //System.out.println("*");
                        tokEq.add(formula.substring(0,1)); //gets 1st letter
                        //System.out.println(formula.substring(0,1));
                        formula = formula.substring(1);
                    }
                }
            }else
            {
                //fucked up somewhere
                tokEq.add(formula.substring(0,1));
                //System.out.println("Loop bottom");
                //System.out.println(formula.substring(0, 1));
                System.out.println("damn");
                formula = formula.substring(1);
            }

        }
		return (String[]) tokEq.toArray();
	}
	

}
