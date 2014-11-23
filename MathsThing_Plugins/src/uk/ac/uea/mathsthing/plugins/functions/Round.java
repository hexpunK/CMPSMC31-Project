package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

public class Round extends IFunctionPlugin {
	
	@Override
	public String getName()
	{
		return "round";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws FormulaException
	{
		return new BigDecimal(Math.round(input.doubleValue()));
	}

}
