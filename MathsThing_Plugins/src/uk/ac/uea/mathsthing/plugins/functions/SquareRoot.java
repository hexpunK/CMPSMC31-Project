package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

public class SquareRoot extends IFunctionPlugin {
	
	@Override
	public String getName()
	{
		return "sqrt";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws FormulaException
	{
		double dVal = input.doubleValue();
		if (dVal <= 0 || dVal == Double.NaN)
			throw new FormulaException("sqrt only accepts positive numbers.");
		
		return new BigDecimal(Math.sqrt(dVal));
	}

}
