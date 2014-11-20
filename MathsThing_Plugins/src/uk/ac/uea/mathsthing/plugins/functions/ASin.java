package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class ASin implements IFunctionPlugin {
	
	@Override
	public String getName()
	{
		return "asin";
	}
	
	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.asin(input.doubleValue()));
	}
}
