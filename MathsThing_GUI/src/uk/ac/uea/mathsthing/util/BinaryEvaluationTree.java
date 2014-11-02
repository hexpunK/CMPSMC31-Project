package uk.ac.uea.mathsthing.util;

import java.math.BigDecimal;
import java.util.HashMap;

import uk.ac.uea.mathsthing.Functions;
import uk.ac.uea.mathsthing.Token;

/**
 * Stores a formula in postfix notation as a {@link BinaryTree} to allow it to 
 * be evaluated. Works on {@link Token} values.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public class BinaryEvaluationTree extends BinaryTree<Token> {

	/**
	 * Creates a new {@link BinaryEvaluationTree} with no stored item or 
	 * child nodes.
	 * 
	 * @since 1.0
	 */
	public BinaryEvaluationTree() {
		super();
	}
	
	/**
	 * Creates a new {@link BinaryEvaluationTree} with the specified item 
	 * stored in the root.
	 * 
	 * @param item The item to store in the root node as a {@link String}.
	 * @since 1.0
	 */
	public BinaryEvaluationTree(Token item) {
		super(item);
	}
	
	/**
	 * Creates a new {@link BinaryEvaluationTree} with the specified item 
	 * stored in the root and the specified trees and children.
	 * 
	 * @param item The item to store in the root node as a {@link Token}.
	 * @param leftTree The {@link BinaryEvaluationTree} to set as the left 
	 * child.
	 * @param rightTree The {@link BinaryEvaluationTree} to set as the right 
	 * child.
	 * @since 1.0
	 */
	public BinaryEvaluationTree(Token item, BinaryEvaluationTree leftTree, BinaryEvaluationTree rightTree) {
		super(item, leftTree, rightTree);
	}
	
	/**
	 * Evaluates this {@link BinaryEvaluationTree}.
	 * 
	 * @param values A {@link HashMap} of parameters and their values.
	 * @return The result of the formula with the specified paramters as a 
	 * double.
	 * @throws Exception Thrown if there is an error in the formula such as 
	 * incorrect formatting.
	 * @since 1.0
	 */
	public BigDecimal eval(HashMap<String, Double> values) 
		throws Exception {
		
		BigDecimal leftVal = new BigDecimal(0);
		BigDecimal rightVal = new BigDecimal(0);
		
		if (leftNode != null)
			leftVal = ((BinaryEvaluationTree)leftNode).eval(values);
		if (rightNode != null) 
			rightVal = ((BinaryEvaluationTree)rightNode).eval(values);
		
		String msg; // Exception message for use later.
		
		switch(item.type) {
		case CONSTANT:
			try {
				return new BigDecimal(item.getToken());
			} catch (NumberFormatException ex) {
				msg = String.format("Constant '%s' is not a number", 
						item.getToken());
				throw new Exception(msg);
			}
		case OPERAND:
			if (values.containsKey(item.getToken())) {
				return new BigDecimal(values.get(item.getToken()));
			}
			msg = String.format(
					"Could not find matching parameter for operand '%s'", 
					item.getToken());
			throw new Exception(msg);
		case OPERATOR:
			switch(item.getToken()) {
			case "*":
				return leftVal.multiply(rightVal);
			case "+":
				return leftVal.add(rightVal);
			case "-":
				return leftVal.subtract(rightVal);
			case "/":
				return leftVal.divide(rightVal);
			case "^":
				return leftVal.pow(rightVal.intValue());
			default:
				msg = String.format(
						"Unknown operator found '%s'", item.getToken());
				throw new Exception();
			}
		case FUNCTION:
			if (Functions.isSupported(item.getToken())) {
				return Functions.processFunction(item.getToken(), rightVal);
			}
			msg = String.format("Unsupported function '%s' found.", 
					item.getToken());
			throw new Exception(msg);
		default:
			msg = String.format("Unknown token found '%s'", item);
			throw new Exception(msg);
		}
	}
}
