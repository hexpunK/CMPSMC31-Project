package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

public class TanH extends IFunctionPlugin {
	
	@Override
	public String getName()
	{
		return "tanh";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws FormulaException
	{
		BigDecimal bd = new BigDecimal(Math.tanh(input.doubleValue()));
		// This is to stop abnormally large results being returned as a result of the asymptote.
		if (bd.doubleValue() > 100 || bd.doubleValue() < -100) return null;
		else return bd;
	}

	
}