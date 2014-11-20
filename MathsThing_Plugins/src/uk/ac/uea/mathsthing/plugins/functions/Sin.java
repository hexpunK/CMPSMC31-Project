package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class Sin implements IFunctionPlugin {
	
	@Override
	public String getName()
	{
		return "sin";
	}
	
	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.sin(input.doubleValue()));
	}
}
