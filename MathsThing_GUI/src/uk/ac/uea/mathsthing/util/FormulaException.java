package uk.ac.uea.mathsthing.util;

public class FormulaException extends Exception {

	/** */
	private static final long serialVersionUID = -2435792122361923440L;
	
	public FormulaException() {
		super();
	}
	
	public FormulaException(String msg) {
		super(msg);
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		
		out.append("Formula exception: ");
		out.append(super.getMessage());
		
		return out.toString();
	}
}
