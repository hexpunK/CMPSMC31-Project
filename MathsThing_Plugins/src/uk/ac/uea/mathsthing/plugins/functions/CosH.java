package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class CosH implements IFunctionPlugin {

	@Override
	public String getName()
	{
		return "cosh";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.cosh(input.doubleValue()));
	}

}
