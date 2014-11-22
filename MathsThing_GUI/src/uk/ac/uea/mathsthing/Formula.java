package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.util.HashMap;

import uk.ac.uea.mathsthing.util.BinaryEvaluationTree;
import uk.ac.uea.mathsthing.util.FormulaException;
import uk.ac.uea.mathsthing.util.IFormula;

/**
 * A simplistic implementation of a mathematical formula representation.
 * This will be replaced with a native C/C++ version when possible.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public class Formula implements IFormula {

	/** The label to use on the y-axis of charts. */
	private String yAxis;
	/** The label to use on the x-axis of charts. */
	private String xAxis;
	/** The {@link Token} objects that represent this {@link Formula} */
	private Token[] tokens;
	/** A mapping of operands to their constant values. */
	private HashMap<String, Double> params;
	
	/** The postfix expression tree to evaluate this {@link Formula}. */
	private BinaryEvaluationTree evalTree;
	/** The first derivative for this {@link Formula} as an {@link IFormula} */
	private IFormula derivative;
	
	protected Formula()
	{
		this.yAxis = "y";
		this.xAxis = "x";
		this.tokens = new Token[0];
		this.params = null;
		this.derivative = null;
		this.evalTree = null;
	}
	
	protected Formula(Token[] tokens)
	{
		this.yAxis = "y";
		this.xAxis = "x";
		this.tokens = tokens;
		this.derivative = null;
		this.evalTree = null;
	}
	
	protected Formula(String y, String x, Token[] tokens, BinaryEvaluationTree eval)
	{
		this.yAxis = y;
		this.xAxis = x;
		this.tokens = tokens;
		this.params = null;
		this.derivative = null;
		this.evalTree = eval;
	}
	
	@Override
	public void setParameters(HashMap<String, Double> params)
	{ 
		this.params = params; 
	}
	
	@Override
	public String getYAxis() { return this.yAxis; }
	
	@Override
	public String getXAxis() { return this.xAxis; }
	
	/**
	 * Gets the tokens that represent this {@link Formula}.
	 * 
	 * @return An array of {@link Token}s for this {@link Formula}.
	 * @since 1.0
	 */
	Token[] getTokens() { return this.tokens; }
	
	/**
	 * Gets the evaluation tree for this {@link Formula}.
	 * 
	 * @return A {@link BinaryEvaluationTree} for evaluating this 
	 * {@link Formula}.
	 * @since 1.0
	 */
	BinaryEvaluationTree getEvalTree() { return this.evalTree; }
	
	@Override
	public BigDecimal getResult() throws FormulaException
	{
		return this.evalTree.eval(params);
	}
	
	@Override
	public IFormula getDerivative() 
	{
		if (derivative == null) {
			// Calculate the derivative.
		}
		return this.derivative;
	}
	
	@Override
	public String toString() 
	{
		return this.tokens.toString();
	}
}
