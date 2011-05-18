package nl.javadude.codejam.gcj11.qualify

import nl.javadude.scala.CodeJam
import nl.javadude.scala.utils.GoogleCodeHelper._
import nl.javadude.scala.utils.FileHelper._

/**
 * Ohh, man... Ever so simple, but only discovered that after the contest analysis :-(...
 *
 */
object GoroSort extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    reader.skipLines(1)
    val array = reader.nextIntArray
    val sortedArray = array.sorted(Ordering.Int)

    (array zip sortedArray).map({ case (x,y) => if(x != y) 1 else 0 }).reduceLeft(_+_).toString + ".000000"
  }
}