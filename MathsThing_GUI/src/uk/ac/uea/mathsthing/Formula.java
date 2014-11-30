package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.Callable;

import uk.ac.uea.mathsthing.util.BinaryEvaluationTree;
import uk.ac.uea.mathsthing.util.FormulaException;
import uk.ac.uea.mathsthing.util.IFormula;

/**
 * A simplistic implementation of a mathematical formula representation.
 * This will be replaced with a native C/C++ version when possible.
 * 
 * @author Jordan Woerner, Jake Ruston
 * @version 1.0
 */
public class Formula implements IFormula, Callable<BigDecimal> {

	/** The label to use on the y-axis of charts. */
	private String yAxis;
	/** The label to use on the x-axis of charts. */
	private String xAxis;
	/** The {@link Token} objects that represent this {@link Formula} */
	private Token[] tokens;
	/** A mapping of operands to their constant values. */
	private HashMap<String, Double> params;
	
	/** The post-fix expression tree to evaluate this {@link Formula}. */
	private BinaryEvaluationTree evalTree;
	/** The first derivative for this {@link Formula} as an {@link IFormula} */
	private IFormula derivative;
	
	/**
	 * Creates a new {@link Formula} with the axes set to "x" and "y".
	 * 
	 * @since 1.0
	 */
	protected Formula()
	{
		this.yAxis = "y";
		this.xAxis = "x";
		this.tokens = new Token[0];
		this.params = null;
		this.derivative = null;
		this.evalTree = null;
	}
	
	/**
	 * Creates a new {@link Formula} with the specified array of {@link Token} 
	 * objects as the internal representation of the formula. Axes labels are 
	 * "x" and "y".
	 * 
	 * @param tokens An array of {@link Token} objects to represent the stored 
	 * formula.
	 * @since 1.0
	 */
	protected Formula(Token[] tokens)
	{
		this.yAxis = "y";
		this.xAxis = "x";
		this.tokens = tokens;
		this.derivative = null;
		this.evalTree = null;
	}
	
	/**
	 * Creates a new {@link Formula} with the specified array of {@link Token} 
	 * objects as the internal representation of the formula. Axes labels can 
	 * be specified by providing a {@link String} for each.
	 * 
	 * @param x The {@link String} to use as the label for the x axis.
	 * @param y The {@link String} to use as the label for the y axis.
	 * @param tokens An array of {@link Token} objects to represent the stored 
	 * formula.
	 * @param eval The {@link BinaryEvaluationTree} to use when evaluating this
	 *  {@link Formula}.
	 * @since 1.0
	 */
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
	 * Gets the tokens that represent this {@link Formula}. This is only a 
	 * testing method. It isn't accessible externally.
	 * 
	 * @return An array of {@link Token}s for this {@link Formula}.
	 * @since 1.0
	 */
	Token[] getTokens() { return this.tokens; }
	
	/**
	 * Gets the evaluation tree for this {@link Formula}. This is only a  
	 * testing method. It isn't accessible externally.
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
		// Lazy load the derivative.
		if (derivative == null) {
			throw new UnsupportedOperationException("Differentiation does not work in this version of Formula.");
		}
		return this.derivative;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		
		for (Token tok : tokens) {
			sb.append(tok.toString());
		}
		
		return sb.toString();
	}

	@Override
	public BigDecimal call() throws FormulaException {
		
		return this.getResult();
	}
}
