package uk.ac.uea.mathsthing.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.junit.Test;

import uk.ac.uea.mathsthing.util.PluginSystem;

/**
 * Tests the {@link PluginSystem} to ensure that it finds at least one plugin.
 * 
 * @author Jordan Woerner
 * @version 1.0
 */
public class PluginSystemTests {

	@Test
	public void testGetPlugins()
	{		
		ArrayList<Class<?>> classes = null;
		
		try {
			classes = PluginSystem.getPlugins();
		} catch (NoSuchMethodException | InvocationTargetException
				| IllegalAccessException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		if (classes == null)
			fail("Classes failed to assign.");
		
		if (classes.size() == 0)
			fail("No classes returned by the PluginSystem.");
	}
}
