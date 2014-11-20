package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class Round implements IFunctionPlugin {

	@Override
	public String getName()
	{
		return "round";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.round(input.doubleValue()));
	}

}
