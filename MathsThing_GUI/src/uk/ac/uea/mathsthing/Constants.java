package uk.ac.uea.mathsthing;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import uk.ac.uea.mathsthing.IPlugin.IConstantPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;
import uk.ac.uea.mathsthing.util.PluginSandbox;
import uk.ac.uea.mathsthing.util.PluginSystem;
import uk.ac.uea.mathsthing.util.StringLengthComparator;

/**
 * Provides methods to handle mathematical constants in a provided formula.
 * 
 * @author Jordan Woerner
 * @version 2.0
 */
public class Constants {

	/** 
	 * A collection of the constants the class currently understands.
	 * 
	 * @since 2.0
	 */
	public static HashMap<String, Class<?>> SUPPORTED_CONSTANTS;
	
	/** 
	 * A {@link String} containing a Regex pattern of all supported 
	 * constants. Allows for known constant to be captured by {@link Matcher}.
	 * 
	 *  @since 1.0
	 */
	public static String constantRegex;
	
	/** Thread pool for the functions to run them concurrently. */
	private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
	
	static {
		// Load all the plugins.
		ArrayList<Class<?>> plugins = new ArrayList<>();
		Constants.SUPPORTED_CONSTANTS = new HashMap<>();
		try {
			plugins.addAll(PluginSystem.getPlugins());
		} catch (NoSuchMethodException | InvocationTargetException
				| IllegalAccessException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		// Store the plugins that this class cares about.
		ArrayList<String> names = new ArrayList<>();
		for (Class<?> clazz : plugins) {
			if (IConstantPlugin.class.isAssignableFrom(clazz)) {
				try {
					String name = (String)clazz.getMethod("getName").invoke(clazz.newInstance());
					names.add(name);
					Constants.SUPPORTED_CONSTANTS.put(name, clazz);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException
						| SecurityException | InstantiationException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		// Initialise the regex.
		Collections.sort(names, new StringLengthComparator());
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (int i = 0; i < names.size(); i++) {
			sb.append(names.get(i));
			if (i < (names.size()-1)) {
				sb.append("|");
			}
		}
		sb.append(")");
		Constants.constantRegex = sb.toString();
	}
	
	/**
	 * Checks to see if the specified constant is supported before it is 
	 * processed.
	 * 
	 * @param constant The constant name as a {@link String}.
	 * @return Returns true if the constant is supported, false otherwise.
	 * @throws InvalidParameterException Thrown if an empty or null constant 
	 * name is passed.
	 * @since 1.0
	 */
	public static final boolean isSupported(final String constant) 
			throws InvalidParameterException
	{		
		if (constant == null || constant.isEmpty())
			throw new InvalidParameterException("A constant name must be provided.");
		
		return Constants.SUPPORTED_CONSTANTS.containsKey(constant);
	}
	
	/**
	 * Processes a provided constant and returns the approximated value of it.
	 * 
	 * @param constant The name of the constant to evaluate as a String.
	 * @return Returns a {@link BigDecimal} containing the value of the 
	 * constant.
	 * @throws FormulaException Thrown if there is an error evaluating this 
	 * constant.
	 * @throws SecurityException Thrown if the plugin attempts to do anything 
	 * the {@link PluginSandbox} does not allow.
	 * @since 1.0
	 */
	public static final BigDecimal processConstant(final String constant)
			throws FormulaException, SecurityException 
	{		
		// Retrieve the constant specified.
		IConstantPlugin plugin = null;
		Class<?> clazz = Constants.SUPPORTED_CONSTANTS.get(constant);
		try {
			plugin = (IConstantPlugin)clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		BigDecimal result = null;
				
		try {
			final Future<BigDecimal> output = executor.submit(plugin);
			executor.schedule(new Runnable(){
			     public void run(){
			         output.cancel(true);
			     }      
			 }, 10000, TimeUnit.MILLISECONDS);
			result = output.get();
		} catch (InterruptedException | ExecutionException exEx) {
			throw new FormulaException(exEx);
		} catch (SecurityException sEx) {
			throw sEx;
		}
		
		return result;
	}
}
