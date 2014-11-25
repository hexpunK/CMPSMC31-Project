package uk.ac.uea.mathsthing.plugins.functions

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin
import java.math.BigDecimal

class Ceil extends IFunctionPlugin {

  def getName : String = "ceil"
    
  def function(input: BigDecimal) : BigDecimal = {
  	val result:BigDecimal = new BigDecimal(Math.ceil(input.doubleValue))
    result
  }
}