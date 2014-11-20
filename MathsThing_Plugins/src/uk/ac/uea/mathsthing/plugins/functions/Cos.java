package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class Cos implements IFunctionPlugin {

	@Override
	public String getName()
	{
		return "cos";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.cos(input.doubleValue()));
	}

}
