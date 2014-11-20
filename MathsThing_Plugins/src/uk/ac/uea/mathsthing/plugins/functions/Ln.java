package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class Ln implements IFunctionPlugin {

	@Override
	public String getName()
	{
		return "ln";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		double dVal = input.doubleValue();
		if (dVal <= 0 || dVal == Double.NaN)
			throw new Exception("ln only accepts positive numbers.");
		
		return new BigDecimal(Math.log(dVal));
	}

}
