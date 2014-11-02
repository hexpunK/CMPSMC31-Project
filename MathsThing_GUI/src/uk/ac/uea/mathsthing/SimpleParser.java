package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

import uk.ac.uea.mathsthing.util.BinaryEvaluationTree;

/**
 * A simplistic parser that converts a formula from infix notation to postfix.
 * Provides methods to access both the infix and postfix notations, allowing
 * them to be displayed to the user if needed.
 * 
 * Generates a evaluation tree of the postfix notation, which can be evaluated.
 * Evaluation can take a mapping of variables to values.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public class SimpleParser implements IFormulaParser {

	/** The infix and postfix notations. */
	private Stack<Token> inFix, postFix;
	/** The formula that has been processed. */
	protected Formula formula;

	/**
	 * Sets up a new {@link SimpleParser} with empty infix and postfix stacks.
	 */
	public SimpleParser() {

		inFix = new Stack<>();
		postFix = new Stack<>();
	}

	/**
	 * Sets the formula this {@link SimpleParser} works on to the specified
	 * {@link Token} array of tokens.
	 * 
	 * @param tokenised The {@link Token} array containing the formula tokens.
	 */
	@Override
	public void setFormula(Token[] tokenised) {

		Stack<Token> opStack = new Stack<>();
		Token tmpOp = null;
		Token funcOp = null;
		String yAxis = null;
		String xAxis = null;
		HashMap<String, Integer> valCount = new HashMap<>();
		boolean negation = false;
		
		for (Token token : tokenised) {

			if (!inFix.empty() 
					&& inFix.peek().type != TokenType.OPERATOR
					&& inFix.peek().type != TokenType.FUNCTION
					&& token.type != TokenType.OPERATOR) {
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
						break; // Nothing needs to be done if the stack is
							   // empty.
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
					try {
						while (opStack.peek().val.matches("[\\^/=]")) {
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
						while (opStack.peek().val.matches("[\\^/\\*=]")) {
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
						while (opStack.peek().val.matches("[\\^\\*/\\+=]")) {
							postFix.push(opStack.pop());
						}
						opStack.push(token);
					} catch (EmptyStackException e) {
						opStack.push(token);
						break;
					}
					break;
				case "-":
					if (inFix.isEmpty() || inFix.peek().val.matches("[(\\*\\+/\\^\\-=]")) {
						negation = !negation;
						break;
					}
					try {
						while (opStack.peek().val.matches("[\\^\\*/\\+\\-=]")) {
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
				postFix.push(token);
				break;
			case FUNCTION:
				if (funcOp != null) {
					opStack.push(funcOp);
				}
				funcOp = token;
				break;
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

				BinaryEvaluationTree newTree = new BinaryEvaluationTree(token,
						leftNode, rightNode);
				tmpStack.push(newTree);
			} else {
				tmpStack.push(new BinaryEvaluationTree(token));
			}
		}

		int lastMax = 0;
		for (String operand : valCount.keySet()) {
			int curVal = valCount.get(operand);
			if (curVal > lastMax) {
				xAxis = operand;
				lastMax = curVal;
			}
		}
		
		this.formula = new Formula(yAxis, xAxis, tokenised, tmpStack.pop());
	}

	/**
	 * Process the formula stored in the evaluation tree.
	 * 
	 * @param params
	 *            The mapping of variables to values as a {@link HashMap}.
	 */
	@Override
	public BigDecimal getResult(HashMap<String, Double> params)
			throws Exception {
		try {
			return formula.getResult(params);
		} catch (Exception e) {
			String err = String.format(
					"The provided function is not properly formed:\n%s",
					e.getMessage());
			throw new Exception(err);
		}
	}

	@Override
	public String getFirstDerivative() {		
		return formula.getDerivative().toString();
	}

	@Override
	public String getSecondDerivative() {		
		return formula.getDerivative().getDerivative().toString();
	}

	// These are only here for testing purposes.
	protected final String getAssignTo() {
		return this.formula.getYAxis();
	}

	protected final Stack<Token> getInFix() {
		return this.inFix;
	}

	protected final Stack<Token> getPostFix() {
		return this.postFix;
	}

	protected final BinaryEvaluationTree getEvalTree() {
		return this.formula.getEvalTree();
	}
}
