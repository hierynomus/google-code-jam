package nl.javadude.codejam.gcj09.round1c

import nl.javadude.scala.CodeJam
import nl.javadude.scala.utils.GoogleCodeHelper._

object AllYourBase extends CodeJam{
  def solveProblem(reader: Iterator[String]) = {
    val array = reader.nextCharArray

    val base = determineBase(array)

    val map = scala.collection.mutable.Map[Char, BigInt]()
    map.put(array(0), BigInt(1))

    var currentVal = BigInt(0)
    val mapped = array.map((x: Char) => if (map.contains(x)) map(x) else {
      map.put(x, currentVal)
      if (currentVal == BigInt(0)) {
        currentVal = BigInt(2)
      } else {
        currentVal = currentVal + 1
      }
      map(x)
    })

    println(array.deep + " => " + mapped.deep)
    
    mapped.reduceLeft((sum: BigInt, value: BigInt) => sum * base + value).toString()
  }

  def determineBase(array: Array[Char]) : Int = {
    val differentChars: Int = array.toSet.size
    if (differentChars > 2) differentChars else 2
  }
}