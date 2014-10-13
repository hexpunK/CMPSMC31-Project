package uk.ac.uea.mathsthing.util;

import java.util.HashMap;

import uk.ac.uea.mathsthing.Functions;

/**
 * Stores a formula in postfix notation as a {@link BinaryTree} to allow it to 
 * be evaluated.
 * 
 * @author Jordan Woerner
 * @version 0.1
 */
public class BinaryEvaluationTree extends BinaryTree<String> {

	/**
	 * Creates a new {@link BinaryEvaluationTree} with no stored item or 
	 * child nodes.
	 * 
	 * @since 0.1
	 */
	public BinaryEvaluationTree() {
		super();
	}
	
	/**
	 * Creates a new {@link BinaryEvaluationTree} with the specified item 
	 * stored in the root.
	 * 
	 * @param item The item to store in the root node as a {@link String}.
	 * @since 0.1
	 */
	public BinaryEvaluationTree(String item) {
		super(item);
	}
	
	/**
	 * Creates a new {@link BinaryEvaluationTree} with the specified item 
	 * stored in the root and the specified trees and children.
	 * 
	 * @param item The item to store in the root node as a {@link String}.
	 * @param leftTree The {@link BinaryEvaluationTree} to set as the left 
	 * child.
	 * @param rightTree The {@link BinaryEvaluationTree} to set as the right 
	 * child.
	 * @since 0.1
	 */
	public BinaryEvaluationTree(String item, BinaryEvaluationTree leftTree, BinaryEvaluationTree rightTree) {
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
	 * @since 0.1
	 */
	public double eval(HashMap<String, Double> values) 
		throws Exception {
		
		double leftVal = 0.0;
		double rightVal = 0.0;
		
		if (leftNode != null)
			leftVal = ((BinaryEvaluationTree)leftNode).eval(values);
		if (rightNode != null) 
			rightVal = ((BinaryEvaluationTree)rightNode).eval(values);
		
		switch(item) {
		case "*":
			return leftVal * rightVal;
		case "+":
			return leftVal + rightVal;
		case "-":
			return leftVal - rightVal;
		case "/":
			return leftVal / rightVal;
		case "^":
			return Math.pow(leftVal, rightVal);
		default:
			if (item.matches("[\\d]*")) {
				return Double.parseDouble(item);
			}
			if (values.containsKey(item)) {
				return values.get(item);
			}
			if (Functions.isSupported(item)) {
				return Functions.processFunction(item, values);
			}
			throw new Exception(
					String.format("Unknown value '%s' found in formula.", item)
			);
		}
	}
}
