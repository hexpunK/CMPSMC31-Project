package uk.ac.uea.mathsthing.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	FunctionsTests.class, 
	ParserTests.class, 
	LexerTests.class, 
	PluginSystemTests.class})
public class AllTests { }