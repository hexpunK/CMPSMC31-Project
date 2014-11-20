package uk.ac.uea.mathsthing.plugins.constants;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IConstantPlugin;

public class Euler implements IConstantPlugin {

	@Override
	public String getName()
	{
		return "e";
	}

	@Override
	public BigDecimal getValue()
	{
		return new BigDecimal(Math.E);
	}

}
