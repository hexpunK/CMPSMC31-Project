package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import uk.ac.uea.mathsthing.gui.GUI;
import uk.ac.uea.mathsthing.util.FormulaException;
import uk.ac.uea.mathsthing.util.IFormula;
import uk.ac.uea.mathsthing.util.IFormulaLexer;
import uk.ac.uea.mathsthing.util.PluginSystem;

/**
 * {@link IPlugin} is the basest class for the plugin system. All plugin types 
 * must derive from this class to be seen by the {@link PluginSystem}.
 * 
 * @author Jordan Woerner
 * @version 1.1
 */
public interface IPlugin {
	
	/**
	 * Gets the name of this {@link IPlugin} to be used when evaluating a 
	 * formula.
	 * 
	 * @return A {@link String} containing the name of this {@link IPlugin}.
	 * @since 1.0
	 */
	public String getName();
	
	/**
	 * Describes a plugin that performs a mathematical function. Requires a name 
	 * to be defined, and the function itself.
	 * 
	 * @author Jordan Woerner
	 * @version 1.1
	 * @since 1.0
	 */
	public static abstract class IFunctionPlugin implements Callable<BigDecimal>, IPlugin {
		
		/** The value to run the function this plugin performs on. */
		private BigDecimal input = new BigDecimal(0);
		/** How long should a {@link IFunctionPlugin} be allowed to run. */
		protected static final long RUN_TIME_SECONDS = 10;
		
		/**
		 * Sets the value this {@link IFunctionPlugin} will work on in the 
		 * {@link IFunctionPlugin#call()} method.
		 * 
		 * @param input The {@link BigDecimal} to input into the function.
		 */
		final void setInput(BigDecimal input) { this.input = input; }
		
		/**
		 * The function to perform. Returns the result to an arbitrary precision 
		 * using {@link BigDecimal}.
		 * 
		 * @param input The number to work on in the function as a 
		 * {@link BigDecimal}.
		 * @return A {@link BigDecimal} containing the result from the function.
		 * @throws FormulaException Throw this Exception if there are any errors 
		 * during the execution of this {@link IFunctionPlugin}.
		 * @since 1.0
		 */
		public abstract BigDecimal function(BigDecimal input) 
				throws FormulaException;
		
		/**
		 * Runs the plugin code in a protected environment to prevent it from 
		 * having access to certain JVM functions that would risk the safety of 
		 * the program.
		 * 
		 * @return Returns the result of this {@link IFunctionPlugin} as a 
		 * {@link BigDecimal}.
		 * @throws FormulaException Thrown if there are any issues executing the 
		 * function.
		 * @throws SecurityException Thrown if the {@link SecurityManager} rejects 
		 * a request for a permission.
		 * @since 1.1
		 */
		public final BigDecimal call() throws FormulaException, SecurityException
		{
			// Run the plugin code.
			BigDecimal result = null;
			result = function(this.input);
			
			return result;
		}
	}
	
	/**
	 * Describes a plugin that contains a mathematical constant. Requires a name  
	 * to be defined, and the value of the constant to be returned.
	 * 
	 * @author Jordan Woerner
	 * @version 1.1
	 * @since 1.0
	 */
	public static abstract class IConstantPlugin implements Callable<BigDecimal>, IPlugin {
		
		/** How long should a {@link IConstantPlugin} be allowed to run. */
		protected static final long RUN_TIME_SECONDS = 2;
		
		/**
		 * The constant to return. Returns the result to an arbitrary precision  
		 * using {@link BigDecimal}.
		 * 
		 * @return A {@link BigDecimal} containing the result from the function.
		 * @since 1.0
		 */
		public abstract BigDecimal getValue();
		
		/**
		 * Runs the plugin code in a protected environment to prevent it from 
		 * having access to certain JVM functions that would risk the safety of 
		 * the program.
		 * 
		 * @return The result of {@link IConstantPlugin#getValue()}.
		 * @throws FormulaException Thrown if there are any issues executing the 
		 * function.
		 * @throws SecurityException Thrown if the {@link SecurityManager} rejects 
		 * a request for a permission.
		 * @since 1.1
		 */
		public final BigDecimal call() throws FormulaException, SecurityException
		{
			// Run the plugin code.
			BigDecimal result = null;
			result = getValue();
			
			return result;
		}
	}
	
	/**
	 * Describes a {@link IPlugin} that can hook into the {@link GUI} to add 
	 * itself to the menu system. 
	 * 
	 * @author Jordan Woerner
	 * @version 1.1
	 * @since 1.1
	 */
	public static abstract class IExtensionPlugin implements Callable<Void>, IPlugin {
		
		/** The {@link IFormula} this {@link IExtensionPlugin} can use. */
		protected IFormula formula;
		/** Used to flag the {@link Callable#call()} execution path. */
		private boolean isReset;
		/** How long should a {@link IExtensionPlugin} be allowed to run. */
		protected static final long RUN_TIME_SECONDS = 2;
		
		/**
		 * Creates the {@link JMenuItem} to be added to the {@link GUI} menu.
		 * 
		 * @return Returns a {@link JMenuItem} entry for a {@link JMenuBar}.
		 * @since 1.0
		 */
		public abstract JMenuItem getMenuEntry();
		
		/**
		 * Provides the specified {@link IExtensionPlugin} a {@link IFormula} 
		 * to work with. This will be called by the GUI when it is returned a 
		 * valid {@link IFormula} from the {@link IFormulaLexer} being used.
		 * 
		 * @param formula An {@link IFormula} instance.
		 * @since 1.1
		 */
		public final static void runExtension(final IExtensionPlugin plugin, IFormula formula)
		{
			plugin.isReset = false;
			plugin.formula = formula;
			plugin.execute();
		}
		
		/**
		 * Resets the specified plugin.
		 * 
		 * @param plugin The {@link IExtensionPlugin} to call 
		 * {@link IExtensionPlugin#onReset()} on.
		 */
		public static final void reset(final IExtensionPlugin plugin)
		{
			plugin.isReset = true;
			plugin.execute();
		}
		
		/**
		 * Contains the main operation for this {@link IExtensionPlugin}. 
		 * The plugin will be aware of any {@link IFormula} currently being 
		 * processed by the {@link GUI}.
		 * 
		 * @throws FormulaException Thrown if there are any issues when 
		 * interacting with the {@link IFormula} stored in this 
		 * {@link IExtensionPlugin}.
		 * @since 1.0
		 */
		public abstract void processFormula() throws FormulaException;
		
		/**
		 * Called when the {@link GUI} handles an error or the user presses the
		 *  "reset" button on the GUI. 
		 * 
		 * @since 1.1
		 */
		public abstract void onReset();
		
		/**
		 * Schedules the execution of this {@link IExtensionPlugin} and a task 
		 * to prevent the plugin from running indefinitely.
		 * 
		 * @since 1.1
		 */
		private void execute()
		{
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
			// Reset the plugin and launch a cancellation task.
			final Future<Void> output = executor.submit(this);
			executor.schedule(new Runnable(){
				public void run(){
					String msg = String.format(
			    			"Extension %s stopped during reset. Exceeded allowed run time.", 
			    			IExtensionPlugin.this.getName());
					output.cancel(true);
					throw new CancellationException(msg);
				}      
			}, IExtensionPlugin.RUN_TIME_SECONDS, TimeUnit.SECONDS);

			// Catch any exceptions thrown.
			new ExceptionLogger(output).start();
		}
		
		/**
		 * Begins executing this {@link IExtensionPlugin}. This method will 
		 * either process the stored data in the plugin or reset the plugin 
		 * based on which method called it. 
		 * 
		 * @throws FormulaException Thrown if there are any problems processing
		 *  the stored {@link IFormula}.
		 * @since 1.0
		 */
		@Override
		public final Void call() throws FormulaException
		{
			if (!this.isReset)
				processFormula();
			else
				onReset();
			
			return null; // We need to return something...
		}
	}
	
	/**
	 * Logs exceptions from {@link Future} instances in the background. Allows 
	 * a {@link IExtensionPlugin} to report failures to the console without 
	 * interrupting the {@link GUI} or crashing the application.
	 * 
	 * @author Jordan Woerner
	 * @version 1.0
	 * @since 1.1
	 */
	final class ExceptionLogger extends Thread
	{
		/** The {@link Future} to check for {@link Exception}s in.  */
		private Future<?> plugin;
		
		/**
		 * Creates a new {@link ExceptionLogger} with the specified 
		 * {@link Future} being monitored.
		 * 
		 * @param plugin The {@link Future} for a {@link IPlugin} to watch 
		 * for exceptions.
		 * @since 1.0
		 */
		public ExceptionLogger(Future<?> plugin)
		{
			this.plugin = plugin;
		}
		
		@Override
		public void run()
		{
			try {
				plugin.get();
			} catch (CancellationException 
					| InterruptedException ex) {
				System.err.println(ex.getMessage());
			} catch (ExecutionException exEx) {
				System.err.println(exEx.getCause().getMessage());
			}
		}		
	}
}
