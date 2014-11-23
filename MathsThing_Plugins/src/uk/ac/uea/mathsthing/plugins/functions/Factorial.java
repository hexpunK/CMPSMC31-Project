package uk.ac.uea.mathsthing.plugins.functions;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;

public class Factorial extends IFunctionPlugin {
	
	@Override
	public String getName()
	{
		return "fact";
	}

	@Override
	public BigDecimal function(BigDecimal input) throws FormulaException
	{
		double dVal = input.doubleValue();
		if (dVal <= 0 || dVal == Double.NaN)
			throw new FormulaException("fact only accepts positive numbers.");
		
		return new BigDecimal(fact(input.intValue()));
	}

	/**
	 * Calculates factorial numbers. Can only be used for numbers that would 
	 * return a result that fits within a double. A minor limit, but it should 
	 * be more than enough for our needs.
	 * 
	 * @param n The number to compute the factorial of as an integer.
	 * @return The computed factorial as a double.
	 * @since 1.0
	 */
	public static final double fact(final int n)
	{		
		if (n <= 1) {
			return 1.0;
		} else {
			return n * fact (n - 1);
		}
	}
}
