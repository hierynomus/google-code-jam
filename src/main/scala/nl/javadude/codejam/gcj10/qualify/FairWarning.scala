package nl.javadude.codejam.gcj10.qualify

import nl.javadude.scala.CodeJam
import nl.javadude.scala.utils.GoogleCodeHelper._

object FairWarning extends CodeJam{
  def solveProblem(reader: Iterator[String]) = {
    val ar = reader nextBigIntArray
    // leave off the first one, this one only denotes the number of events
    val list = ar.toList.tail

    // for euclidian gcd take the first one, and subtract it from each of the other ones
    val first = list.head
    val g: BigInt = list.tail.map((x: BigInt) => (x - first).abs).reduceLeft(gcd(_, _))

    if (first % g == BigInt(0)) 0.toString else (g - (first % g)).toString
  }

  def gcd(a: BigInt, b: BigInt) : BigInt = {
   if (b == BigInt(0)) a else gcd(b, a % b)
  }
}