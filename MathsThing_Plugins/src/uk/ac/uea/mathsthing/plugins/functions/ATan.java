package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class ATan implements IFunctionPlugin {

	@Override
	public String getName()
	{
		return "atan";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.tan(input.doubleValue()));
	}

	
}
