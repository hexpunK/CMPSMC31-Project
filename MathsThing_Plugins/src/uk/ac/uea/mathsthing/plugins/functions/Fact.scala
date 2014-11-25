package uk.ac.uea.mathsthing.plugins.functions

import java.math.BigDecimal

import uk.ac.uea.mathsthing.IPlugin.IFunctionPlugin

class Fact extends IFunctionPlugin {

  def getName : String = "fact"
    
  def function(input: BigDecimal) : BigDecimal = {
  	val result:BigDecimal = new BigDecimal(fact(input.intValue))
    result
  }
  
  def fact(n: Int) : Int = {
    if (n <= 1)
	  1
	else
	  n * fact (n - 1)
  }
}