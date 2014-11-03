package uk.ac.uea.mathsthing.tests.stubs;

import java.util.HashMap;

import uk.ac.uea.mathsthing.IFormulaLexer;
import uk.ac.uea.mathsthing.Token;
import uk.ac.uea.mathsthing.TokenType;

public class StubbedFormulaLexer implements IFormulaLexer {

	@Override
	public String getUserFormula() {
		return "y = mx^2";
	}

	@Override
	public String getProccessedFormula() {
		return "y=m*x^2";
	}

	@Override
	public Token[] tokenize(String formula) {
		return new Token[] {
				new Token("y", TokenType.OPERAND), 
				new Token("=", TokenType.OPERATOR), 
				new Token("m", TokenType.OPERAND), 
				new Token("*", TokenType.OPERATOR), 
				new Token("x", TokenType.OPERAND),
				new Token("^", TokenType.OPERATOR), 
				new Token("2", TokenType.CONSTANT)
			};
	}

	@Override
	public Token[] getTokens() {
		return new Token[] {
				new Token("y", TokenType.OPERAND), 
				new Token("=", TokenType.OPERATOR), 
				new Token("m", TokenType.OPERAND), 
				new Token("*", TokenType.OPERATOR), 
				new Token("x", TokenType.OPERAND),
				new Token("^", TokenType.OPERATOR), 
				new Token("2", TokenType.CONSTANT)
			};
	}

	@Override
	public HashMap<String, Double> getParameters() {
		return new HashMap<>();
	}

}
