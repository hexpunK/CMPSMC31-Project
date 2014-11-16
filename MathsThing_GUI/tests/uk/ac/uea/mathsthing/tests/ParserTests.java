package uk.ac.uea.mathsthing.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Stack;

import org.junit.Test;

import uk.ac.uea.mathsthing.SimpleParser;
import uk.ac.uea.mathsthing.Token;
import uk.ac.uea.mathsthing.TokenType;

public class ParserTests extends SimpleParser {

	// y = a(b+cd)+a
	protected static final Token[] testFormula = new Token[] {
		new Token("y", TokenType.OPERAND),
		new Token("=", TokenType.OPERATOR),
		new Token("a", TokenType.OPERAND),
		new Token("*", TokenType.OPERATOR),
		new Token("(", TokenType.OPERATOR),
		new Token("b", TokenType.OPERAND),
		new Token("+", TokenType.OPERATOR),
		new Token("c", TokenType.OPERAND),
		new Token("*", TokenType.OPERATOR),
		new Token("d", TokenType.OPERAND),
		new Token(")", TokenType.OPERATOR),
		new Token("+", TokenType.OPERATOR),
		new Token("a", TokenType.OPERAND)
	};
	// y = (10x+5)/x
	protected static final Token[] formula2 = new Token[] {
		new Token("y", TokenType.OPERAND),
		new Token("=", TokenType.OPERATOR),
		new Token("(", TokenType.OPERATOR),
		new Token("10", TokenType.CONSTANT),
		new Token("*", TokenType.OPERATOR),
		new Token("x", TokenType.OPERAND),
		new Token("+", TokenType.OPERATOR),
		new Token("5", TokenType.CONSTANT),
		new Token(")", TokenType.OPERATOR),
		new Token("/", TokenType.OPERATOR),
		new Token("x", TokenType.OPERAND)
	};
	
	// y = (--10x+5)/x
	protected static final Token[] formula3 = new Token[] {
		new Token("y", TokenType.OPERAND),
		new Token("=", TokenType.OPERATOR),
		new Token("(", TokenType.OPERATOR),
		new Token("-", TokenType.OPERATOR),
		new Token("-", TokenType.OPERATOR),
		new Token("10", TokenType.CONSTANT),
		new Token("*", TokenType.OPERATOR),
		new Token("floor", TokenType.FUNCTION),
		new Token("(", TokenType.OPERATOR),
		new Token("x", TokenType.OPERAND),
		new Token("sin", TokenType.FUNCTION),
		new Token("(", TokenType.OPERATOR),
		new Token("x", TokenType.OPERAND),
		new Token(")", TokenType.OPERATOR),
		new Token(")", TokenType.OPERATOR),
		new Token("+", TokenType.OPERATOR),
		new Token("5", TokenType.CONSTANT),
		new Token(")", TokenType.OPERATOR),
		new Token("/", TokenType.OPERATOR),
		new Token("x", TokenType.OPERAND)
	};
	protected static final String assignTo = "y";
	protected static final String inFix = "a*(b+c*d)+a";
	protected static final String postFix = "abcd*+*a+";
	protected static final Double input = 5.0;
	
	@Test
	public void testSetFormula() {
		
		System.out.println("\nTesting formula parsing...");
		System.out.printf("Formula: %s\n", printArray(testFormula));
		this.setFormula(ParserTests.testFormula);
		this.parse();
		// Check the right variable is being assigned to.
		assertEquals(ParserTests.assignTo, this.getAssignTo());
		
		assertEquals(5, this.getEvalTree().size());
		
		StringBuilder output = new StringBuilder();
		Stack<Token> inFix = this.getInFix();
		Stack<Token> postFix = this.getPostFix();
		
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
		this.parse();
		HashMap<String, Double> vals = new HashMap<>();
		vals.put("x", input);
		try {
			double result = this.getResult(vals).doubleValue();
			System.out.printf("Formula result: %2.10f\n", result);
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
