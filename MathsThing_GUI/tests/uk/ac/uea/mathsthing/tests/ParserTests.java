package uk.ac.uea.mathsthing.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Stack;

import org.junit.Test;

import uk.ac.uea.mathsthing.SimpleParser;

public class ParserTests extends SimpleParser {

	protected static final String[] formula = new String[] {"y", "=", "a", "*", "(", "b", "+", "c", "*", "d", ")", "+", "e"};
	protected static final String[] formula2 = new String[] {"y", "=", "(", "10", "*", "x", "+", "5", ")", "/", "x"};
	protected static final String assignTo = "y";
	protected static final String inFix = "a*(b+c*d)+e";
	protected static final String postFix = "abcd*+*e+";
	protected static final Double input = 5.0;
	
	@Test
	public void testSetFormula() {
		
		System.out.println("\nTesting formula parsing...");
		System.out.printf("Formula: %s\n", printArray(formula));
		this.setFormula(ParserTests.formula);
		// Check the right variable is being assigned to.
		assertEquals(ParserTests.assignTo, this.getAssignTo());
		
		assertEquals(5, this.getEvalTree().size());
		
		StringBuilder output = new StringBuilder();
		Stack<String> inFix = this.getInFix();
		Stack<String> postFix = this.getPostFix();
		
		System.out.printf("Infix Stack:\t%s\n", printStack(inFix));
		System.out.printf("Postfix Stack:\t%s\n", printStack(postFix));
		
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
		
		System.out.println("\nTesting formula evaluation...");
		System.out.printf("Formula: %s\n", printArray(formula2));
		System.out.printf("x = %2.2f\n", input);
		this.setFormula(formula2);
		HashMap<String, Double> vals = new HashMap<>();
		vals.put("x", input);
		try {
			double result = this.getResult(vals);
			System.out.printf("Formula result: %2.2f\n", result);
			assertEquals(11.0, result, 0.0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	private <E> String printArray(E[] array) {
		
		StringBuilder output = new StringBuilder();
		
		for (int i = 0; i < array.length; i++) {
			output.append(array[i].toString());
		}
		
		return output.toString();
	}
	
	private <E> String printStack(Stack<E> stack) {
		
		StringBuilder output = new StringBuilder();
		Object[] stackArray = new Object[stack.size()];
		stack.toArray(stackArray);
		
		for (int i = 0; i < stack.size(); i++) {
			output.append(stackArray[i].toString());
		}
		
		return output.toString();
	}
}
