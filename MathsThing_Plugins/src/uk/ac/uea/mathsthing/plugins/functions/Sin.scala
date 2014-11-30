package uk.ac.uea.mathsthing.plugins.functions

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin
import java.math.BigDecimal

class Sin extends IFunctionPlugin {

  def getName : String = "sin"
    
  def function(input: BigDecimal) : BigDecimal = {
  	val result:BigDecimal = new BigDecimal(Math.sin(input.doubleValue))
    result
  }
}