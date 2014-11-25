package uk.ac.uea.mathsthing.plugins.constants

import java.math.BigDecimal

import uk.ac.uea.mathsthing.IPlugin.IConstantPlugin

class Tau extends IConstantPlugin {

  def getName : String = "tau"
    
  def getValue : BigDecimal = new BigDecimal(Math.PI*2)
}