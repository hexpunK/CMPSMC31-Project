package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.util.HashMap;

import uk.ac.uea.mathsthing.util.BinaryEvaluationTree;

/**
 * A simplistic implementation of a mathematical formula representation.
 * This will be replaced with a native C/C++ version when possible.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public class Formula implements IFormula {

	private String yAxis;
	private String xAxis;
	private Token[] tokens;
	
	private BinaryEvaluationTree evalTree;
	private Formula derivative;
	
	protected Formula()
	{
		this.yAxis = "y";
		this.xAxis = "x";
		this.tokens = new Token[0];
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
		this.derivative = null;
		this.evalTree = eval;
	}
	
	public String getYAxis() { return this.yAxis; }
	
	public String getXAxis() { return this.xAxis; }
	
	public Token[] getTokens() { return this.tokens; }
	
	public BinaryEvaluationTree getEvalTree() { return this.evalTree; }
	
	public BigDecimal getResult(HashMap<String, Double> params) 
			throws Exception
	{
		return this.evalTree.eval(params);
	}
	
	public Formula getDerivative() { return this.derivative; }
	
	@Override
	public String toString() {
		return this.tokens.toString();
	}
}
