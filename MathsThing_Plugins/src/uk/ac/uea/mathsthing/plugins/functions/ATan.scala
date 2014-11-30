package uk.ac.uea.mathsthing.plugins.functions

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin
import java.math.BigDecimal

class ATan extends IFunctionPlugin {

  def getName : String = "atan"
    
  def function(input: BigDecimal) : BigDecimal = {
  	if (input.doubleValue == Double.NaN 
  	    || input.doubleValue <= Double.NegativeInfinity 
  	    || input.doubleValue >= Double.PositiveInfinity)
      null
    
    try {
      val result = new BigDecimal(Math.atan(input.doubleValue))
      
      if (result.doubleValue == Double.NaN 
  	      || result.doubleValue <= Double.NegativeInfinity 
  	      || result.doubleValue >= Double.PositiveInfinity 
  	      || result.doubleValue > 1000 || result.doubleValue < -1000)
        null;
  	  else
        result
    } catch {
      case ex:NumberFormatException => null
    }
  }
}