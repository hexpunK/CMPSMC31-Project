package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class Floor implements IFunctionPlugin {

	@Override
	public String getName()
	{
		return "floor";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		return new BigDecimal(Math.floor(input.doubleValue()));
	}

}
