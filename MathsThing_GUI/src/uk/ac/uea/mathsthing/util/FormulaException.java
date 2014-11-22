package uk.ac.uea.mathsthing.util;

public class FormulaException extends Exception {

	private static final long serialVersionUID = -2435792122361923440L;

	public FormulaException() {
		super();
	}
	
	public FormulaException(String msg) {
		super(msg);
	}
	
	public FormulaException(Throwable cause) {
		super(cause);
	}
	
	public FormulaException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
