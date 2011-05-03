package nl.javadude.codejam.gcj10.qualify

import nl.javadude.scala.CodeJam
import nl.javadude.scala.utils.GoogleCodeHelper._

object ThemePark extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    val ar = reader nextIntArray
    val (r, k, n) = (ar(0), ar(1), ar(2))

    val groups = reader nextIntArray

    // Map of beginIndex to Tuple[endIndex, euros]
    val table = scala.collection.mutable.Map[Int, Tuple2[Int, BigInt]]()

    var beginIndex = 0
    var trips = 0
    var euros : BigInt = BigInt(0)
    while (trips < r) {
      val tuple = if (table.contains(beginIndex)) table(beginIndex) else findEndIndexAndEurosMadeForTrip(beginIndex, groups, k)
      table(beginIndex) = tuple
      beginIndex = tuple._1
      euros = euros + tuple._2
      trips = trips + 1
    }

    euros.toString
  }

  def findEndIndexAndEurosMadeForTrip(beginIndex: Int, groups: Array[Int], k: Int): Tuple2[Int, BigInt] = {
    var capacityLeft = k
    var endIndex = beginIndex
    var eurosMade = BigInt(0)
    var groupsFitted = 0
    while (capacityLeft - groups(endIndex) >= 0 && groupsFitted < groups.length) {
      capacityLeft = capacityLeft - groups(endIndex)
      eurosMade = eurosMade + groups(endIndex)
      endIndex = (endIndex + 1) % groups.length
      groupsFitted = groupsFitted + 1
    }

    (endIndex, eurosMade)
  }
}