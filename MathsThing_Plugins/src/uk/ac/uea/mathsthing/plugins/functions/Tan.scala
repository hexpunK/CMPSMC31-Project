package uk.ac.uea.mathsthing.plugins.functions

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin
import java.math.BigDecimal

class Tan extends IFunctionPlugin {

  def getName : String = "tan"
    
  def function(input: BigDecimal) : BigDecimal = {
  	val result:BigDecimal = new BigDecimal(Math.tan(input.doubleValue))
  	
  	if (result.doubleValue() > 100 || result.doubleValue() < -100)
  	  return null;
  	else
      result
  }
}