package uk.ac.uea.mathsthing.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Stack;

import org.junit.Test;

import uk.ac.uea.mathsthing.SimpleParser;

public class ParserTests extends SimpleParser {

	protected static final String[] formula = new String[] {"y", "=", "a", "*", "(", "b", "+", "c", "*", "d", ")", "+", "e"};
	protected static final String assignTo = "y";
	protected static final String inFix = "a*(b+c*d)+e";
	protected static final String postFix = "abcd*+*e+";
	
	@Test
	public void testSetFormula() {
		
		this.setFormula(ParserTests.formula);
		// Check the right variable is being assigned to.
		assertEquals(ParserTests.assignTo, this.getAssignTo());
		
		assertEquals(5, this.getEvalTree().size());
		
		StringBuilder output = new StringBuilder();
		Stack<String> inFix = this.getInFix();
		Stack<String> postFix = this.getPostFix();
		
		// Check the infix representation is stored correctly.
		while (!inFix.empty()) {
			output.insert(0, inFix.pop());
		}
		assertEquals(ParserTests.inFix, output.toString());
		
		// Check the postfix representation is created correctly.
		output = new StringBuilder();
		while (!postFix.empty()) {
			output.insert(0, postFix.pop());
		}
		assertEquals(ParserTests.postFix, output.toString());
	}
	
	@Test
	public void testGetResult() {
		
		this.setFormula(new String[]{"y", "=", "(", "10", "*", "x", "+", "5", ")", "/", "x"});
		HashMap<String, Double> vals = new HashMap<>();
		vals.put("x", 5.0);
		try {
			double result = this.getResult(vals);
			assertEquals(11.0, result, 0.0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
