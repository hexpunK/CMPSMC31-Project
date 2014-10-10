package uk.ac.uea.mathsthing.tests.stubs;

import java.util.HashMap;

import uk.ac.uea.mathsthing.IFormulaParser;

public class StubbedFormulaParser implements IFormulaParser {

	public void setFormula(String[] tokenised) {
		// Doesn't do anything in this example.
	}
	
	@Override
	public double getResult(HashMap<String, Double> params) {
		return 1.0;
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
