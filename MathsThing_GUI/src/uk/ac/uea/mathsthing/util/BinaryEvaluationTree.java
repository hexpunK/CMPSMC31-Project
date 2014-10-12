package uk.ac.uea.mathsthing.util;

import java.util.HashMap;

import uk.ac.uea.mathsthing.Functions;

public class BinaryEvaluationTree extends BinaryTree<String> {

	public BinaryEvaluationTree() {
		super();
	}
	
	public BinaryEvaluationTree(String item) {
		super(item);
	}
	
	public BinaryEvaluationTree(String item, BinaryEvaluationTree leftTree, BinaryEvaluationTree rightTree) {
		super(item, leftTree, rightTree);
	}
	
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
				return Functions.processFunction(item);
			}
			throw new Exception(
					String.format("Unknown value '%s' found in formula.", item)
			);
		}
	}
}
