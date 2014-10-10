package uk.ac.uea.mathsthing.tests.stubs;

import uk.ac.uea.mathsthing.IFormulaLexer;

public class StubbedFormulaLexer implements IFormulaLexer {

	@Override
	public String getUserFormula() {
		return "y = mx^2";
	}

	@Override
	public String getProccessedFormula() {
		return "y=m*x^2";
	}

	@Override
	public String[] tokenize(String formula) {
		return new String[] {"y", "=", "m", "*", "x", "^", "2"};
	}

}
