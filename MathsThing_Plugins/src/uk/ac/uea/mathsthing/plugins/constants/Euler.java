package uk.ac.uea.mathsthing.plugins.constants;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IPlugin.IConstantPlugin;

public class Euler extends IConstantPlugin {

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
