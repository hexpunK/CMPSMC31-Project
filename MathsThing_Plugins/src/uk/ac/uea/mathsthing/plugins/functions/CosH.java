package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

public class CosH extends IFunctionPlugin {
	
	@Override
	public String getName()
	{
		return "cosh";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws FormulaException
	{
		return new BigDecimal(Math.cosh(input.doubleValue()));
	}

}
