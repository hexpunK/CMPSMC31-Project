package uk.ac.uea.mathsthing;

import java.util.HashMap;

public class NativeParser implements IFormulaParser {

	static {
		if (System.getProperty("sun.arch.data.model").equals("64"))
			System.loadLibrary("MathsThing64");
		else
			System.loadLibrary("MathsThing");
	}
	
	protected long ptr;
	
	public NativeParser() {
		
		this.ptr = create();
	}
	
	@Override
	public void setFormula(String[] tokenised) {
		setFormulaNative(tokenised.length, tokenised);
	}

	@Override
	public double getResult(HashMap<String, Double> params) throws Exception {
		
		String[] keys = new String[params.size()];
		double[] values = new double[params.size()];
		
		keys = params.keySet().toArray(keys);
		int i = 0;
		for(Double value : params.values()) {
			values[i] = value;
			i++;
		}
		
		return getResultNative(keys, values);
	}

	@Override
	public String getFirstDerivative() {
		return getFirstDerivativeNative();
	}

	@Override
	public String getSecondDerivative() {
		return "";
	}
	
	public void close() {
		
		destroy();
		this.ptr = 0L;
	}
	
	private native long create();
	
	private native void setFormulaNative(int size, String[] tokens);
	
	private native double getResultNative(String[] names, double[] values);
	
	private native String getFirstDerivativeNative();
	
	private native void destroy();
}
