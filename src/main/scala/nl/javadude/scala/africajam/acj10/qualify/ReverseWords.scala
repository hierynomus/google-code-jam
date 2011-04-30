package nl.javadude.scala.africajam.acj10.qualify

import nl.javadude.scala.CodeJam
import nl.javadude.scala.utils.GoogleCodeHelper._


object ReverseWords extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    reader.nextStringArray.reduceLeft((a:String, b:String) => b + " " + a)
  }
}