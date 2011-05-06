package nl.javadude.codejam.gcj09.round1c

import nl.javadude.scala.CodeJam
import nl.javadude.scala.utils.GoogleCodeHelper._

object BribeThePrisoners extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    val Array(p, q) = reader nextIntArray
    val toBeReleased = reader.nextIntArray.toList

    val lookup = scala.collection.mutable.Map[(Int, Int), Int]()

    def solve(a : Int, b : Int) : Int = {
      val pair = (a, b)
      if (lookup.contains(pair)) lookup(pair) else {
        var r = 0
        toBeReleased.foreach((x : Int) =>
          if (x >= a && x <= b) {
            val tmp = (b - a) + solve(a, x - 1) + solve(x + 1, b)
            if (r == 0 || tmp < r) r = tmp
          }
        )
        lookup.put(pair, r)
        r
      }
    }
    solve(1, p).toString()
  }
}