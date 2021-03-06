package uk.ac.uea.mathsthing;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

import uk.ac.uea.mathsthing.gui.Graph;
import uk.ac.uea.mathsthing.util.BinaryEvaluationTree;
import uk.ac.uea.mathsthing.util.IFormula;
import uk.ac.uea.mathsthing.util.IFormulaParser;
import uk.ac.uea.mathsthing.util.IObservable;
import uk.ac.uea.mathsthing.util.IObserver;

/**
 * A simplistic parser that converts a formula from infix notation to postfix.
 * Provides methods to access both the infix and postfix notations, allowing
 * them to be displayed to the user if needed.
 * 
 * Generates a evaluation tree of the postfix notation, which can be evaluated.
 * Evaluation can take a mapping of variables to values.
 * 
 * @author Jordan Woerner, Jake Ruston
 * @version 1.0
 */
public class SimpleParser implements IFormulaParser, IObservable, Runnable {

	/** The {@link Token} objects of the formula being parsed. */
	private Token[] tokens;
	private HashMap<String, Double> params;
	/** The infix and postfix notations. */
	private Stack<Token> inFix, postFix;
	/** The formula that has been processed. */
	protected IFormula formula;
	/** The {@link Graph} to notify. */
	protected IObserver observed;

	/**
	 * Sets up a new {@link SimpleParser} with empty infix and postfix stacks.
	 * 
	 * @since 1.0
	 */
	public SimpleParser() 
	{
		inFix = new Stack<>();
		postFix = new Stack<>();
	}

	@Override
	public void setFormula(Token[] tokenised) 
	{
		this.tokens = tokenised;
	}
	
	@Override
	public void setParameters(HashMap<String, Double> params)
	{
		if (params == null)
			this.params = new HashMap<>();
		else				
			this.params = params;
	}
	
	@Override
	public IFormula parse() 
	{
		Stack<Token> opStack = new Stack<>();
		Token tmpOp = null;
		Token funcOp = null;
		String yAxis = "y";
		String xAxis = "x";
		HashMap<String, Integer> valCount = new HashMap<>();
		boolean negation = false;
		
		for (Token token : tokens) {

			// Handle implicit multiplication. 
			if (!inFix.empty() && 
				// If we have an opening bracket, multiply if the last
				// token was anything other than an operator or function.
				((token.val.matches("[\\(]")
					&& !inFix.peek().val.matches("[\\(\\*\\+/\\^\\-%=]")
					&& inFix.peek().type != TokenType.FUNCTION
					)
				// If the current token isn't an operator and the last 
				// token was a closing bracket.
				|| (token.type != TokenType.OPERATOR
					&& inFix.peek().val.matches("[\\)]")
					)
				// If the current token isn't an operator, and the last 
				// token wasn't another operator or function.
				|| (token.type != TokenType.OPERATOR
					&& inFix.peek().type != TokenType.OPERATOR
					&& inFix.peek().type != TokenType.FUNCTION
					)
				)
			) {
				// Perform an implicit multiplication.
				Token implied = new Token("*", TokenType.OPERATOR);
				inFix.push(implied);
				opStack.push(implied);
			}

			switch (token.type) {
			case OPERATOR:
				switch (token.val) {
				case "(":
					opStack.push(token);
					break;
				case ")":
					try {
						// Closing brackets just pop all items off until a
						// opening bracket is found in the stack.
						while (!(tmpOp = opStack.pop()).val.equals("(")) {
							postFix.push(tmpOp);
						}
					} catch (EmptyStackException e) {
						// Nothing needs to be done if the stack is empty.
						break;
					}
					break;
				case "^":
					try {
						// Pop items off the stack that have higher or equal
						// precedence.
						while (opStack.peek().val.matches("[\\^=]")) {
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
				case "*":
				case "%": // Division, multiplication and modulo are equal.
					try {
						while (opStack.peek().val.matches("[\\^/\\*%=]")) {
							postFix.push(opStack.pop());
						}
						opStack.push(token);
					} catch (EmptyStackException e) {
						opStack.push(token);
						break;
					}
					break;
				case "+":
				case "-": // Addition and subtraction are equal. 
					if (inFix.isEmpty() || inFix.peek().val.matches("[\\*\\+/\\^\\-%=]")) {
						negation = !negation;
						break;
					}
					try {
						while (opStack.peek().val.matches("[\\*/\\+\\-%=\\^]")) {
							postFix.push(opStack.pop());
						}
						opStack.push(token);
					} catch (EmptyStackException e) {
						opStack.push(token);
						break;
					}
					break;
				case "=":
					yAxis = postFix.pop().val;

					inFix.clear();
					postFix.clear();
					opStack.clear();
					break;
				}
				break;
			case OPERAND:
				// Handle non-operator tokens.
				if (!valCount.containsKey(token.val)) {
					valCount.put(token.val, 1);
				} else {
					int oldVal = valCount.get(token.val);
					valCount.put(token.val, ++oldVal);
				}
			case CONSTANT:
				if (!negation) {
					postFix.push(token);
				} else {
					postFix.push(new Token("0", TokenType.CONSTANT));
					postFix.push(token);
					postFix.push(new Token("-", TokenType.OPERATOR));
					negation = false;
				}
				break;
			case FUNCTION:
				if (funcOp != null) {
					opStack.push(funcOp);
				}
				funcOp = token;
				break;
			case MAGICNUM:
				postFix.push(token);
				break;
			}

			if (funcOp != null && funcOp != token) {
				opStack.push(funcOp);
				funcOp = null;
			}
			
			if (!token.val.equals("="))
				inFix.push(token);
		}
		// Move the rest of the operators into postfix.
		while (!opStack.empty() && (tmpOp = opStack.pop()) != null) {
			postFix.push(tmpOp);
		}

		Token[] postFixArray = new Token[postFix.size()];
		Stack<BinaryEvaluationTree> tmpStack = new Stack<>();
		postFix.copyInto(postFixArray);

		// Build the BinaryEvaluationTree.
		for (Token token : postFixArray) {
			if (token.type == TokenType.OPERATOR 
					|| token.type == TokenType.FUNCTION) {
				BinaryEvaluationTree rightNode = null;
				BinaryEvaluationTree leftNode = null;

				if (!tmpStack.empty())
					rightNode = tmpStack.pop();
				if (!tmpStack.empty() && token.type != TokenType.FUNCTION)
					leftNode = tmpStack.pop();

				tmpStack.push(new BinaryEvaluationTree(token, leftNode, rightNode));
			} else {
				tmpStack.push(new BinaryEvaluationTree(token));
			}
		}

		// Work out the most used operand, that's probably the x value.
		int lastMax = 0;
		for (String operand : valCount.keySet()) {
			if (operand.equals(yAxis)) continue;
			if (params != null && params.containsKey(operand)) continue;
			int curVal = valCount.get(operand);
			if (curVal > lastMax) {
				xAxis = operand;
				lastMax = curVal;
			}
		}
		
		if (!tmpStack.empty())			
			this.formula = new Formula(yAxis, xAxis, tokens, tmpStack.pop());
		
		return this.formula;
	}

	// These are only here for testing purposes.
	protected final String getAssignTo() 
	{
		return this.formula.getYAxis();
	}

	protected final Stack<Token> getInFix() 
	{
		return this.inFix;
	}

	protected final Stack<Token> getPostFix() 
	{
		return this.postFix;
	}

	protected final BinaryEvaluationTree getEvalTree() 
	{
		return ((Formula)this.formula).getEvalTree();
	}

	@Override
	public void attach(IObserver observable) 
	{
		this.observed = observable;
	}

	@Override
	public void detach(IObserver observable) 
	{
		this.observed = null;
	}

	@Override
	public void update() 
	{
		observed.update(this.formula);
	}

	@Override
	public void run() 
	{
		parse();
		update();
	}
}
