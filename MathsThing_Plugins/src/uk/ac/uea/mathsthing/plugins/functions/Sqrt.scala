package uk.ac.uea.mathsthing.plugins.functions

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin
import java.math.BigDecimal
import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin
import uk.ac.uea.mathsthing.util.FormulaException

class Sqrt extends IFunctionPlugin {

  def getName() : String = "sqrt"
    
  def function(input : BigDecimal) : BigDecimal = {
    
    if (input.doubleValue() == Double.NaN || input.doubleValue() <= 0)
      throw new FormulaException("sqrt only accepts positive numbers.");
    
    val result:BigDecimal = new BigDecimal(Math.sqrt(input.doubleValue))
    result
  }
}