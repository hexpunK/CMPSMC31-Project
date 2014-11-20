package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class Ceiling implements IFunctionPlugin {

	@Override
	public String getName()
	{
		return "ceil";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.ceil(input.doubleValue()));
	}

}
