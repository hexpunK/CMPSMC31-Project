package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class Tan implements IFunctionPlugin {

	@Override
	public String getName()
	{
		return "tan";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.tan(input.doubleValue()));
	}

	
}
