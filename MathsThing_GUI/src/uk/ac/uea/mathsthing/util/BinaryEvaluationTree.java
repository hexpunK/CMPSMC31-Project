package uk.ac.uea.mathsthing.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

import uk.ac.uea.mathsthing.Constants;
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
	public BinaryEvaluationTree()
	{
		super();
	}
	
	/**
	 * Creates a new {@link BinaryEvaluationTree} with the specified item 
	 * stored in the root.
	 * 
	 * @param item The item to store in the root node as a {@link String}.
	 * @since 1.0
	 */
	public BinaryEvaluationTree(final Token item)
	{
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
	public BinaryEvaluationTree(final Token item, 
			final BinaryEvaluationTree leftTree, 
			final BinaryEvaluationTree rightTree)
	{
		super(item, leftTree, rightTree);
	}
	
	/**
	 * Evaluates this {@link BinaryEvaluationTree}.
	 * 
	 * @param values A {@link HashMap} of parameters and their values.
	 * @return The result of the formula with the specified parameters as a 
	 * double.
	 * @throws FormulaException Thrown if there is an error in the formula 
	 * such as incorrect formatting.
	 * @since 1.0
	 */
	public BigDecimal eval(final HashMap<String, Double> values) 
		throws FormulaException
	{		
		MathContext mc=new MathContext(64, RoundingMode.HALF_EVEN);
		BigDecimal leftVal = new BigDecimal(0, mc);
		BigDecimal rightVal = new BigDecimal(0, mc);
		if (values.get("x") == Double.valueOf(1.0) || values.get("x") == Double.valueOf(2.0))
			System.out.printf("X: %3.2f,\tItem: %s,\tLeft: %3.2f,\tRight: %3.2f\n", values.get("x"), item.val, leftVal, rightVal);
		if (leftNode != null)
			leftVal = ((BinaryEvaluationTree)leftNode).eval(values);
		if (rightNode != null) 
			rightVal = ((BinaryEvaluationTree)rightNode).eval(values);
		
		String msg; // Exception message for use later.
		
		switch(item.type) {
		// Constants just need converting to BigDecimal. Invalid tokens will
		// throw an exception.
		case CONSTANT:
			try {
				return new BigDecimal(item.getToken());
			} catch (NumberFormatException ex) {
				msg = String.format("Constant '%s' is not a number", 
						item.getToken());
				throw new FormulaException(msg);
			}
		// Operands will be replaced at evaluation time.
		case OPERAND:
			if (values.containsKey(item.getToken())) {
				return new BigDecimal(values.get(item.getToken()));
			}
			msg = String.format("Could not find matching parameter for operand '%s'", 
					item.getToken());
			throw new FormulaException(msg);
		// Operators work on the left and right child results. As these are 0.0
		// by default, null children aren't really a problem.
		case OPERATOR:
			switch(item.getToken()) {
			case "*":
				return leftVal.multiply(rightVal, mc);
			case "+":
				return leftVal.add(rightVal, mc);
			case "-":
				return leftVal.subtract(rightVal, mc);
			case "/":
				return leftVal.divide(rightVal, mc);
			case "^":				
				return new BigDecimal(Math.pow(leftVal.doubleValue(), rightVal.doubleValue()));
			default:
				msg = String.format("Unknown operator found '%s'", item.getToken());
				throw new FormulaException();
			}
		// Functions will be called as needed, if something goes wrong inside 
		// the function call an exception will be thrown.
		case FUNCTION:
			if (Functions.isSupported(item.getToken())) {
				try {
					return Functions.processFunction(item.getToken(), rightVal);
				} catch (FormulaException e) {
					throw e;
				}
			}
			msg = String.format("Unsupported function '%s' found.", 
					item.getToken());
			throw new FormulaException(msg);
		// Mathematical constants such as pi will be evaluated here if they are
		// supported. If not, an exception will be thrown.
		case MAGICNUM:
			if (Constants.isSupported(item.getToken())) {
				try {
					return Constants.processConstant(item.getToken());
				} catch (FormulaException e) {
					throw e;
				}
			}
			msg = String.format("Unsupported constant '%s' found.", 
					item.getToken());
			throw new FormulaException(msg);
		}
		// If an unknown token type is encountered, throw this.
		msg = String.format("Unknown token found '%s'", item);
		throw new FormulaException(msg);
	}
}
