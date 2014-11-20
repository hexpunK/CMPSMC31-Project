package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;

public class SquareRoot implements IFunctionPlugin {

	@Override
	public String getName()
	{
		return "sqrt";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws Exception
	{
		double dVal = input.doubleValue();
		if (dVal <= 0 || dVal == Double.NaN)
			throw new Exception("sqrt only accepts positive numbers.");
		
		return new BigDecimal(Math.sqrt(dVal));
	}

}
