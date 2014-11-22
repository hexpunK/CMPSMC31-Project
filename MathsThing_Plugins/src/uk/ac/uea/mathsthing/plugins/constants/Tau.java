package uk.ac.uea.mathsthing.plugins.constants;

import java.math.BigDecimal;

import uk.ac.uea.mathsthing.IConstantPlugin;

public class Tau extends IConstantPlugin {

	@Override
	public String getName() 
	{
		return "tau";
	}

	@Override
	public BigDecimal getValue() 
	{
		return new BigDecimal(Math.PI * 2.0);
	}

}
