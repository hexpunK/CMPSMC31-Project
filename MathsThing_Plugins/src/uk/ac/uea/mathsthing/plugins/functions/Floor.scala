package uk.ac.uea.mathsthing.plugins.functions

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin
import java.math.BigDecimal
import uk.ac.uea.mathsthing.Functions

class Floor extends IFunctionPlugin {

  def getName : String = "floor"
    
  def function(input: BigDecimal) : BigDecimal = {
    val result:BigDecimal = new BigDecimal(Math.floor(input.doubleValue))
    result
  }
}