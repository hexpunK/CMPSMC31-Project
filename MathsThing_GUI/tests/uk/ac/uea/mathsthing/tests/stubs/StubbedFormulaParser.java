package uk.ac.uea.mathsthing.tests.stubs;

import java.util.HashMap;

import uk.ac.uea.mathsthing.Token;
import uk.ac.uea.mathsthing.util.IFormula;
import uk.ac.uea.mathsthing.util.IFormulaParser;
import uk.ac.uea.mathsthing.util.IObserver;

public class StubbedFormulaParser implements IFormulaParser {

	public void setFormula(Token[] tokens) { }

	@Override
	public IFormula parse() {
		return null;
	}

	@Override
	public void attach(IObserver observable) { }

	@Override
	public void detach(IObserver observable) { }

	@Override
	public void update() { }

	@Override
	public void setParameters(HashMap<String, Double> params) { }

}
