package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

public class ASin extends IFunctionPlugin {
	
	@Override
	public String getName()
	{
		return "asin";
	}
	
	@Override
	public BigDecimal function(BigDecimal input) throws FormulaException
	{
		return new BigDecimal(Math.asin(input.doubleValue()));
	}
}
