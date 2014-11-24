package uk.ac.uea.mathsthing.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.uea.mathsthing.Lexer;
import uk.ac.uea.mathsthing.Token;

/**
 * Tests the {@link Lexer} class to ensure it works as intended. Makes sure the
 *  lexical analysis actually breaks a {@link String} up as expected, and that 
 * it is possible to return the correct representations when they are needed.  
 * 
 * @author Jordan Woerner
 */
public class LexerTests {

	public static final String input = "y = 2x + 1";
	public static final String outputString = "y=2x+1";
	public static final String[] tokens = {"y", "=", "2", "x", "+", "1"};
	
	@Test
	public final void testGetUserFormula() {
		
		Lexer lex = new Lexer(input);
		assertEquals(input, lex.getUserFormula());
	}

	@Test
	public final void testGetProccessedFormula() {
		
		Lexer lex = new Lexer(input);
		
		assertEquals("", lex.getProccessedFormula());
		lex.setFormula(input);
		lex.tokenize();
		assertEquals(outputString, lex.getProccessedFormula());
	}

	@Test
	public final void testTokenize() {
		
		Lexer lex = new Lexer();
		lex.setFormula(input);
		Token[] outputT = lex.tokenize();
		
		String[] output = new String[outputT.length];
		for(int i=0; i< output.length; i++)
		{
			output[i] = outputT[i].toString();
		}
		
		assertArrayEquals(tokens, output);
	}

}
