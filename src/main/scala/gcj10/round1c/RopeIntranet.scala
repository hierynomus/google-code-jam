package gcj10.round1c


import collection.Iterator
import io.Source
import java.io.{FileWriter, File}

object RopeIntranet {
  import GoogleCodeHelper.iteratorToHelper

  def name = "gcj10.round1c.RopeIntranet"

  def solveProblem(reader: Iterator[String]) = {
    val nrWindows = reader.nextInt
    val lines = for (i <- 1 to nrWindows; line = new Line(reader nextIntArray)) yield line
    (for (i <- 0 until lines.size; j <- i+1 until lines.size) yield lines(i).intersect(lines(j))).filter(x => x).size.toString
  }

  case class Line(a : Int, b : Int) {
    def this(arr : Array[Int]) = this(arr(0), arr(1))
    def intersect(line : Line) : Boolean = {
      (line.a < a && line.b > b) || (line.a > a && line.b < b)
    }
  }

  def main(args: Array[String]) {
    if (args.length == 0) {
      throw new IllegalArgumentException("Could not find input file in arguments")
    }

    val input = args(0)
    val outputFile = new File((input.substring(0, input.lastIndexOf("."))) + ".out")
    val reader: Iterator[String] = Source.fromFile(input).getLines()
    val nrProblems = reader.nextInt
    val results = new Array[String](nrProblems)
    for (i <- 1 to nrProblems) {
      results(i - 1) = "Case #" + i + ": " + solveProblem(reader)
    }
    val fw = new FileWriter(outputFile)
    try {
      fw.write(results.mkString("\n"))
    } finally {
      fw.close()
    }
  }

  class GoogleCodeHelper(val iterator: Iterator[String]) {

    def trimmedLine = iterator.next().trim()
    def nextInt: Int = trimmedLine.toInt
    def nextIntArray: Array[Int] = nextStringArray map (_.toInt)
    def nextCharArray: Array[Char] = iterator.next().toCharArray
    def nextLongArray: Array[Long] = nextStringArray map (_.toLong)
    def nextBigDecimalArray: Array[BigDecimal] = nextLongArray map (BigDecimal(_))
    def nextBigIntArray: Array[BigInt] = nextStringArray map (BigInt(_))
    def nextStringArray: Array[String] = trimmedLine split " "

    def skipLines(nr: Int) {
      for (i <- 1 to nr) {
        iterator.next()
      }
    }
  }

  object GoogleCodeHelper {
    implicit def iteratorToHelper(x: Iterator[String]): GoogleCodeHelper = new GoogleCodeHelper(x)
  }
}