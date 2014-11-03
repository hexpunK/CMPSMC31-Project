package uk.ac.uea.mathsthing.tests.stubs;

import java.math.BigDecimal;
import java.util.HashMap;

import uk.ac.uea.mathsthing.Formula;
import uk.ac.uea.mathsthing.IFormulaParser;
import uk.ac.uea.mathsthing.Token;

public class StubbedFormulaParser implements IFormulaParser {

	public Formula setFormula(Token[] tokenised) {
		return null;
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
