package uk.ac.uea.mathsthing.plugins.functions

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin
import java.math.BigDecimal
import uk.ac.uea.mathsthing.Functions

class Round extends IFunctionPlugin {

  def getName : String = "round"
    
  def function(input: BigDecimal) : BigDecimal = {
    val result:BigDecimal = new BigDecimal(Math.round(input.doubleValue))
    result
  }
}