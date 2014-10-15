package uk.ac.uea.mathsthing;

import java.util.HashMap;

public class NativeParser implements IFormulaParser {

	static {
		System.loadLibrary("MathsThing");
	}
	
	private long ptr;
	
	public NativeParser() {
		
		this.ptr = 0L;
	}
	
	@Override
	public void setFormula(String[] tokenised) {
		setFormulaNative(tokenised);
	}

	@Override
	public double getResult(HashMap<String, Double> params) throws Exception {
		return getResultNative(params);
	}

	@Override
	public String getFirstDerivative() {
		return getFirstDerivativeNative();
	}

	@Override
	public String getSecondDerivative() {
		return getSecondDerivativeNative();
	}
	
	private native void setFormulaNative(String[] tokens);
	
	private native double getResultNative(HashMap<String, Double> params);
	
	private native String getFirstDerivativeNative();
	
	private native String getSecondDerivativeNative();
}
