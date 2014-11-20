package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class ACos implements IFunctionPlugin {

	@Override
	public String getName()
	{
		return "acos";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.acos(input.doubleValue()));
	}

}
