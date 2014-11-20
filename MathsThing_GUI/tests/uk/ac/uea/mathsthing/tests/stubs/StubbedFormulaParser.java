package uk.ac.uea.mathsthing.tests.stubs;

import java.math.BigDecimal;
import java.util.HashMap;

import uk.ac.uea.mathsthing.Token;
import uk.ac.uea.mathsthing.util.IFormula;
import uk.ac.uea.mathsthing.util.IFormulaParser;
import uk.ac.uea.mathsthing.util.IObserver;

public class StubbedFormulaParser implements IFormulaParser {

	public void setFormula(Token[] tokens) {
		// Do nothing.
	}
	
	@Override
	public BigDecimal getResult(HashMap<String, Double> params) {
		return new BigDecimal(1.0);
	}

	@Override
	public IFormula getFirstDerivative() {
		return null;
	}

	@Override
	public IFormula getSecondDerivative() {
		return null;
	}

	@Override
	public IFormula parse() {
		return null;
	}

	@Override
	public void attach(IObserver observable) {
		
	}

	@Override
	public void detach(IObserver observable) {
		
	}

	@Override
	public void update() {
		
	}

}
