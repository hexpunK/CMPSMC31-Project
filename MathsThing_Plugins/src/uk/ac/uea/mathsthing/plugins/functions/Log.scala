package uk.ac.uea.mathsthing.plugins.functions

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin
import java.math.BigDecimal
import uk.ac.uea.mathsthing.Functions
import uk.ac.uea.mathsthing.util.FormulaException

class Log extends IFunctionPlugin {

  def getName : String = "log"
    
  def function(input: BigDecimal) : BigDecimal = {
    
    if (input.doubleValue() == Double.NaN || input.doubleValue() <= 0)
      throw new FormulaException("log only accepts positive numbers.");
    
    val result:BigDecimal = new BigDecimal(Math.log10(input.doubleValue))
    result
  }
}