package nl.javadude.codejam.gcj10.qualify

import nl.javadude.scala.CodeJam
import nl.javadude.scala.utils.GoogleCodeHelper._


/**
 * n = number of snappers
 * k = number of fingersnaps
 *
 * n = 2; k = 0; off - off - Loff
 * n = 2; k = 1; on - off - Loff
 * n = 2; k = 2; off - on - Loff
 * n = 2; k = 3; on - on - Lon
 * n = 2; k = 4; off - off - Loff
 *
 * n = 3; k = 0; off - off - off - Loff
 * n = 3; k = 1; on - off - off - Loff
 * n = 3; k = 2; off - on - off - Loff
 * n = 3; k = 3; on - on - off - Loff
 * n = 3; k = 4; off - off - on - Loff
 * n = 3; k = 5; on - off - on - Loff
 * n = 3; k = 6; off - on - on - Loff
 * n = 3; k = 7; on - on - on - Lon
 * n = 3; k = 8; off - off - off - Loff
 */
object Snapper extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    val array: Array[BigInt] = reader nextBigIntArray
    val (n, k) = (array(0).toInt, array(1))

    (for (i <- 0.until(n)) yield k.testBit(i)).reduceLeft(_ && _) match {
      case true => "ON"
      case false => "OFF"
    }
  }
}