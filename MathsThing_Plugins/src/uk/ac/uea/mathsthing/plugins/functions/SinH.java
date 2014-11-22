package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

public class SinH extends IFunctionPlugin {
	
	@Override
	public String getName()
	{
		return "sinh";
	}
	
	@Override
	public BigDecimal function(BigDecimal input) throws FormulaException
	{
		return new BigDecimal(Math.sinh(input.doubleValue()));
	}
}
