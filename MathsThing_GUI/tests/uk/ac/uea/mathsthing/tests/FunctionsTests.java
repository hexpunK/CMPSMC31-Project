package uk.ac.uea.mathsthing.tests;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.uea.mathsthing.Functions;
import uk.ac.uea.mathsthing.tests.stubs.StubbedFormulaLexer;
import uk.ac.uea.mathsthing.tests.stubs.StubbedFormulaParser;

public class FunctionsTests {

	@BeforeClass
	public static void setupLexerParser() {		
		Functions.setFormulaLexer(new StubbedFormulaLexer());
		Functions.setFormulaParser(new StubbedFormulaParser());
	}
	
	@Test
	public final void testIsSupported() {
		
		boolean op = false;
		
		try {
			assertTrue(Functions.isSupported("sin"));
			assertFalse(Functions.isSupported("pow"));
			Functions.isSupported(null);
		} catch (InvalidParameterException parEx) {
			op = true;
		}
		
		assertTrue(op);
	}

	@Test
	public final void testProcessFunction() {
		
		boolean param = false;
		boolean op = false;
		
		try {
			Functions.processFunction("");
		} catch (InvalidParameterException parEx) {
			param = true;
		} finally {
			try {
				Functions.processFunction("pow(0.0)");
			}
			catch (UnsupportedOperationException opEx) {
				op = true;
			}
		}
		
		assertEquals(Math.sin(1.0), Functions.processFunction("sin(50.0)"), 0.0);
		
		assertTrue(param);
		assertTrue(op);
	}

	@Test
	public final void testFact() {
		
		assertEquals(1.0, Functions.fact(1), 0.0);
		assertEquals(120.0, Functions.fact(5), 0.0);
	}

}
