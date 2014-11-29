package uk.ac.uea.mathsthing;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin;
import uk.ac.uea.mathsthing.util.FormulaException;
import uk.ac.uea.mathsthing.util.PluginSystem;
import uk.ac.uea.mathsthing.util.StringLengthComparator;

/**
 * Provides methods to handle mathematical functions in a provided formula.
 * 
 * @author Jordan Woerner
 * @version 2.1
 */
public final class Functions {

	/** 
	 * A collection of the functions the class currently understands.
	 * 
	 * @since 2.0
	 */
	static HashMap<String, Class<?>> SUPPORTED_FUNCTIONS;
	
	/** 
	 * A {@link String} containing a Regex pattern of all supported 
	 * functions. Allows for known functions to be captured by {@link Matcher}.
	 * 
	 *  @since 1.0
	 */
	static final String functionRegex;
	
	/** Thread pool for the constants to run them concurrently. */
	private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
	
	static {
		// Load all the plugins.
		ArrayList<Class<?>> plugins = new ArrayList<>();
		Functions.SUPPORTED_FUNCTIONS = new HashMap<>();
		try {
			plugins.addAll(PluginSystem.getPlugins());
		} catch (NoSuchMethodException | InvocationTargetException
				| IllegalAccessException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		// Store the plugins that this class cares about.
		ArrayList<String> names = new ArrayList<>();
		for (Class<?> clazz : plugins) {
			if (IFunctionPlugin.class.isAssignableFrom(clazz)) {
				try {
					String name = (String)clazz.getMethod("getName").invoke(clazz.newInstance());
					names.add(name);
					Functions.SUPPORTED_FUNCTIONS.put(name, clazz);
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
		functionRegex = sb.toString();
	}
	
	/**
	 * Checks to see if the specified function is supported before it is 
	 * processed.
	 * 
	 * @param funcName The function name as a {@link String}.
	 * @return Returns true if the function is supported, false otherwise.
	 * @throws InvalidParameterException Thrown if an empty or null function 
	 * name is passed.
	 * @since 1.0
	 */
	public static final boolean isSupported(final String funcName) 
			throws InvalidParameterException
	{		
		if (funcName == null || funcName.isEmpty())
			throw new InvalidParameterException("A function name must be provided.");
		
		return SUPPORTED_FUNCTIONS.containsKey(funcName);
	}
	
	/**
	 * Processes a provided function and returns the output from the function. 
	 * If the parameter is a formula itself rather than a constant or number it
	 *  will be calculated before it is used.
	 * 
	 * @param funcName The name of the function to call as a String.
	 * @param input The result from the evaluation tree to process.
	 * @return Returns a double containing the result of the function.
	 * @throws FormulaException Thrown if there is an error evaluating the 
	 * formula this function works on.
	 * @since 1.0
	 */
	public static final BigDecimal processFunction(final String funcName, 
			final BigDecimal input) throws FormulaException
	{		
		double dResult = input.doubleValue();
		
		if (dResult >= Double.POSITIVE_INFINITY || dResult <= Double.NEGATIVE_INFINITY) {
			throw new FormulaException("Provided value is too large to use in functions.");
		}
		
		// Execute the function specified.
		IFunctionPlugin plugin = null;
		Class<?> clazz = Functions.SUPPORTED_FUNCTIONS.get(funcName);
		try {
			plugin = (IFunctionPlugin)clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		plugin.setInput(input);
		BigDecimal result = null;
		
		try {
			if (executor == null || executor.isShutdown())
				executor = Executors.newScheduledThreadPool(2);
			
			// Run the IFormulaPlugin in a seperate thread.
			final Future<BigDecimal> output = executor.submit(plugin);
			// Schedule another thread to kill the plugin if it runs too long.
			executor.schedule(new Runnable(){
			     public void run(){
			         output.cancel(true);
			     }      
			 }, IFunctionPlugin.RUN_TIME_SECONDS, TimeUnit.SECONDS);
			result = output.get();
		} catch (CancellationException cEx) { 
			String msg = String.format(
					"Function '%s' stopped processing. Exceeded allowed run time.",
					plugin.getName());
			throw new FormulaException(msg);
		} catch (InterruptedException | ExecutionException exEx) {			
			if (exEx.getCause() != null)
				// Security exceptions need a bit more detail in their message.
				if (exEx.getCause() instanceof SecurityException) {
					throw new FormulaException(String.format(
							"Plugin '%s' performed a restricted action!\n%s",
							plugin.getName(), exEx.getCause().getMessage()));
				} else
					throw new FormulaException(exEx.getCause().getMessage());
			
			throw new FormulaException(exEx.getMessage());
		}
		
		return result;
	}
	
	/**
	 * Cancels any active executions of plugins.
	 * 
	 * @since 2.1
	 */
	public static final void reset()
	{
		if (!executor.isTerminated())
			executor.shutdownNow();
	}
}
