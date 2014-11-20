package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class SinH implements IFunctionPlugin {
	
	@Override
	public String getName()
	{
		return "sinh";
	}
	
	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.sinh(input.doubleValue()));
	}
}
