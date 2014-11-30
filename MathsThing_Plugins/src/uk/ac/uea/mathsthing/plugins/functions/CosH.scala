package uk.ac.uea.mathsthing.plugins.functions

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin
import java.math.BigDecimal

class CosH extends IFunctionPlugin {

  def getName : String = "cosh"
    
  def function(input: BigDecimal) : BigDecimal = {
  	val result:BigDecimal = new BigDecimal(Math.cosh(input.doubleValue))
    result
  }
}