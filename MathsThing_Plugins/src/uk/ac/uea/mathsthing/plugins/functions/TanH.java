package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class TanH implements IFunctionPlugin {

	@Override
	public String getName()
	{
		return "tanh";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.atan(input.doubleValue()));
	}

	
}
