package uk.ac.uea.mathsthing.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.HashMap;

import org.junit.Test;

import uk.ac.uea.mathsthing.Functions;

public class FunctionsTests {

	private static final HashMap<String, Double> params;
	static {
		params = new HashMap<>();
		params.put("x", 5.0);
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
		BigDecimal expec = new BigDecimal(0.0);
		
		try {
			Functions.processFunction("", new BigDecimal(0.0));
			assertEquals(expec, Functions.processFunction("log", new BigDecimal(-1)));
		} catch (Exception e) {
			param = true;
		}
		
		assertTrue(param);
	}

	@Test
	public final void testFact() {
		
		try {
			assertEquals(new BigDecimal(1), Functions.processFunction("fact", new BigDecimal(1)));
			assertEquals(new BigDecimal(120), Functions.processFunction("fact", new BigDecimal(5)));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
