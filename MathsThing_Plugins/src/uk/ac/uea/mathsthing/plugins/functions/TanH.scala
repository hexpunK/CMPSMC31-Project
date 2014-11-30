package uk.ac.uea.mathsthing.plugins.functions

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin
import java.math.BigDecimal

class TanH extends IFunctionPlugin {

  def getName : String = "tanh"
    
  def function(input: BigDecimal) : BigDecimal = {
  	val result:BigDecimal = new BigDecimal(Math.tanh(input.doubleValue))
  	
  	if (result.doubleValue() > 100 || result.doubleValue() < -100)
  	  return null;
  	else
      result
  }
}