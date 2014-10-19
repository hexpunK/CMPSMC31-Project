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

	/** The variable to assign the output to when evaluating. */
	private String assignTo;
	/** The infix and postfix notations. */
	private Stack<String> inFix, postFix;
	/** The evaluation tree. */
	private BinaryEvaluationTree evalTree;
	
	/**
	 * Sets up a new {@link SimpleParser} with empty infix and postfix stacks.
	 */
	public SimpleParser() {
		
		inFix = new Stack<>();
		postFix = new Stack<>();
		evalTree = null;
	}
	
	/**
	 * Sets the formula this {@link SimpleParser} works on to the specified 
	 * {@link String} array of tokens.
	 * 
	 * @param tokenised The {@link String} array containing the formula 
	 * tokens.
	 */
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
					// Closing brackets just pop all items off until a opening 
					// bracket is found in the stack.
					while (!(tmpOp = opStack.pop()).equals("(")) {
						postFix.push(tmpOp);
					}
				} catch (EmptyStackException e) {
					break; // Nothing needs to be done if the stack is empty.
				}
				break;
			case "^":
				try {
					// Pop items off the stack that have higher or equal 
					// precedence.
					while (opStack.peek().matches("[\\^=]")) {
						postFix.push(opStack.pop());
					}
					opStack.push(token);
				} catch (EmptyStackException e) {
					// If the stack is empty, just add the current operator.
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
					while (opStack.peek().matches("[\\^\\*/\\+\\-=]")) {
						postFix.push(opStack.pop());
					}
					opStack.push(token);
				} catch (EmptyStackException e) { 
					opStack.push(token);
					break; 
				}
				break;
			case "-":
				if (inFix.isEmpty() || inFix.peek().matches("[(\\*\\+/\\^\\-=]")) {
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
				// Handle non-operator tokens.
				if (!negation) {
					postFix.push(token);
				} else {
					postFix.push("0");
					postFix.push(token);
					postFix.push("-");
					negation = false;
				}
			}
 
			if (!token.equals("="))
				inFix.push(token);
		}
		// Move the rest of the operators into postfix.
		while (!opStack.empty() && (tmpOp = opStack.pop()) != null) {
			postFix.push(tmpOp);
		}
		
		String[] postFixArray = new String[postFix.size()];
		Stack<BinaryEvaluationTree> tmpStack = new Stack<>();
		postFix.copyInto(postFixArray);
		
		// Build the BinaryEvaluationTree.
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

	/**
	 * Process the formula stored in the evaluation tree.
	 * 
	 * @param params The mapping of variables to values as a {@link HashMap}.
	 */
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

	// These are only here for testing purposes.
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
