package uk.ac.uea.mathsthing.tests.stubs;

import java.math.BigDecimal;
import java.util.HashMap;

import uk.ac.uea.mathsthing.IFormulaParser;

public class StubbedFormulaParser implements IFormulaParser {

	public void setFormula(String[] tokenised) {
		// Doesn't do anything in this example.
	}
	
	@Override
	public BigDecimal getResult(HashMap<String, Double> params) {
		return new BigDecimal(1.0);
	}

	@Override
	public String getFirstDerivative() {
		return "y=2*x";
	}

	@Override
	public String getSecondDerivative() {
		return "y=x";
	}

}
