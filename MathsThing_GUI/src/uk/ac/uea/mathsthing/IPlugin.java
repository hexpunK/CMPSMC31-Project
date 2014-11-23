package uk.ac.uea.mathsthing;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import uk.ac.uea.mathsthing.gui.GUI;
import uk.ac.uea.mathsthing.util.FormulaException;
import uk.ac.uea.mathsthing.util.PluginSystem;

/**
 * {@link IPlugin} is the basest class for the plugin system. All plugin types 
 * must derive from this class to be seen by the {@link PluginSystem}.
 * 
 * @author Jordan Woerner
 * @version 1.0
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
	 * @version 1.0
	 * @since 1.0
	 */
	public static abstract class IExtensionPlugin implements IPlugin {
		
		/**
		 * Creates the {@link JMenuItem} to be added to the {@link GUI} menu.
		 * 
		 * @return Returns a {@link JMenuItem} entry for a {@link JMenuBar}.
		 */
		public abstract JMenuItem getMenuEntry();
	}
}
