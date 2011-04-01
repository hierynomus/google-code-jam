package nl.javadude.scalajam.gcj10.round1c

import nl.javadude.scalajam.CodeJam
import nl.javadude.scalajam.utils.GoogleCodeHelper._


object RopeIntranet extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    val nrWindows = reader nextInt

    val lines = for (i <- 1 to nrWindows; line = new Line(reader nextIntArray)) yield line

    (for (i <- 0 until lines.size; j <- i+1 until lines.size) yield lines(i).intersect(lines(j))).filter(x => x).size.toString
  }



  class Line(val a : Int, val b : Int) {

    def this(arr : Array[Int]) = this(arr(0), arr(1))

    def intersect(line : Line) : Boolean = {
      (line.a < a && line.b > b) || (line.a > a && line.b < b)
    }
  }
}