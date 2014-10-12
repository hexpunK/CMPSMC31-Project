package uk.ac.uea.mathsthing;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

import uk.ac.uea.mathsthing.util.BinaryEvaluationTree;

/**
 * A simplistic parser that converts a formula from infix notation to postfix. 
 * Provides methods to access both the infix and postfix notations, allowing 
 * them to be displayed to the user if needed.
 * 
 * Generates a evaluation tree of the postfix notation, which can be 
 * evaluated. Evaluation can take a mapping of variables to values.
 * 
 * @author Jordan Woerner
 */
public class SimpleParser implements IFormulaParser {

	private String assignTo;
	private Stack<String> inFix, postFix;
	private BinaryEvaluationTree evalTree;
	
	public SimpleParser() {
		
		inFix = new Stack<>();
		postFix = new Stack<>();
		evalTree = null;
	}
	
	@Override
	public void setFormula(String[] tokenised) {
		
		Stack<String> opStack = new Stack<>();
		String tmpOp;
		boolean negation = false;
		
		for(String token : tokenised) {
			tmpOp = "";
			
			switch(token) {
			case "(":
				opStack.push(token);
				break;
			case ")":
				try {
					while (!(tmpOp = opStack.pop()).equals("(")) {
						postFix.push(tmpOp);
					}
				} catch (EmptyStackException e) {
					break; 
				}
				break;
			case "^":
				try {
					while (opStack.peek().matches("[\\^=]")) {
						postFix.push(opStack.pop());
					}
					opStack.push(token);
				} catch (EmptyStackException e) { 
					opStack.push(token);
					break; 
				}
				break;
			case "/":
				try {
					while (opStack.peek().matches("[\\^/=]")) {
						postFix.push(opStack.pop());
					}
					opStack.push(token);
				} catch (EmptyStackException e) { 
					opStack.push(token);
					break; 
				}
				break;
			case "*":
				try {
					while (opStack.peek().matches("[\\^/\\*=]")) {
						postFix.push(opStack.pop());
					}
					opStack.push(token);
				} catch (EmptyStackException e) { 
					opStack.push(token);
					break; 
				}
				break;
			case "+":
				try {
					while (opStack.peek().matches("[\\^\\*/\\+=]")) {
						postFix.push(opStack.pop());
					}
					opStack.push(token);
				} catch (EmptyStackException e) { 
					opStack.push(token);
					break; 
				}
				break;
			case "-":
				if (inFix.peek().matches("[(\\*\\-\\+/\\^=]")) {
					negation = !negation;
					break;
				}
				try {
					while (opStack.peek().matches("[\\^\\*/\\+\\-=]")) {
						postFix.push(opStack.pop());
					}
					opStack.push(token);
				} catch (EmptyStackException e) { 
					opStack.push(token);
					break; 
				}
				break;
			case "=":
				assignTo = postFix.pop();
				
				inFix.clear();
				postFix.clear();
				opStack.clear();				
				break;
			default:
				if (!negation) {
					postFix.push(token);
				} else {
					postFix.push("-"+token);
					negation = false;
				}
			}
			
			if (!token.equals("="))
				inFix.push(token);
		}
		
		while (!opStack.empty() && (tmpOp = opStack.pop()) != null) {
			postFix.push(tmpOp);
		}
		
		String[] postFixArray = new String[postFix.size()];
		Stack<BinaryEvaluationTree> tmpStack = new Stack<>();
		postFix.copyInto(postFixArray);
		
		for(String token : postFixArray) {
			if (token.matches("[\\+\\*/\\-\\^]")) {
				BinaryEvaluationTree rightNode = null;
				BinaryEvaluationTree leftNode = null;
				
				if (!tmpStack.empty())
					rightNode = tmpStack.pop();
				if (!tmpStack.empty())
					leftNode = tmpStack.pop();
				
				BinaryEvaluationTree newTree = new BinaryEvaluationTree(token, leftNode, rightNode);
				tmpStack.push(newTree);
			} else {
				tmpStack.push(new BinaryEvaluationTree(token));
			}
		}
		
		evalTree = tmpStack.pop();
	}

	@Override
	public double getResult(HashMap<String, Double> params) 
			throws Exception {
		try {
			return evalTree.eval(params);
		} catch (Exception e) {
			String err = String.format(
					"The provided function is not properly formed:\n%s", 
					e.getMessage());
			throw new Exception(err);
		}
	}

	@Override
	public String getFirstDerivative() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSecondDerivative() {
		// TODO Auto-generated method stub
		return null;
	}

	protected final String getAssignTo() {
		return this.assignTo;
	}
	
	protected final Stack<String> getInFix() {
		return this.inFix;
	}
	
	protected final Stack<String> getPostFix() {		
		return this.postFix;
	}
	
	protected final BinaryEvaluationTree getEvalTree() {
		return this.evalTree;
	}
}
