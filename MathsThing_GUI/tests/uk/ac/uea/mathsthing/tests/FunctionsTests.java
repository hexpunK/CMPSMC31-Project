package uk.ac.uea.mathsthing.tests;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.Test;

import uk.ac.uea.mathsthing.Functions;

public class FunctionsTests {

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
			Functions.processFunction("", null);
			assertEquals(0.0, Functions.processFunction("sin", "0.0"), 0.0);
		} catch (InvalidParameterException parEx) {
			param = true;
		} finally {
			try {
				Functions.processFunction("pow", "0.0");
			}
			catch (UnsupportedOperationException opEx) {
				op = true;
			}
		}
		
		assertTrue(param);
		assertTrue(op);
	}

	@Test
	public final void testFact() {
		
		assertEquals(1.0, Functions.fact(1), 0.0);
		assertEquals(120.0, Functions.fact(5), 0.0);
	}

}
