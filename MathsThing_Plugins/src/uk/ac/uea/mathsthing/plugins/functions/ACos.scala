package uk.ac.uea.mathsthing.plugins.functions

import java.math.BigDecimal

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin

class ACos extends IFunctionPlugin {

  def getName : String = "acos"
    
  def function(input: BigDecimal) : BigDecimal = {
    if (input.doubleValue == Double.NaN 
  	    || input.doubleValue <= Double.NegativeInfinity 
  	    || input.doubleValue >= Double.PositiveInfinity)
      null
    
    try {
      val result = new BigDecimal(Math.acos(input.doubleValue))
      
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