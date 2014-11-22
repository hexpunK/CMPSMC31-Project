package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IFunctionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

public class Log extends IFunctionPlugin {
	
	@Override
	public String getName()
	{
		return "log";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws FormulaException
	{
		double dVal = input.doubleValue();
		if (dVal <= 0 || dVal == Double.NaN)
			throw new FormulaException("ln only accepts positive numbers.");
		
		return new BigDecimal(Math.log10(dVal));
	}

}
