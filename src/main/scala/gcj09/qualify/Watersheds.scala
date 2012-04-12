package gcj09.qualify

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}


object Watersheds {

  import GoogleCodeHelper.iteratorToHelper
  def name = "gcj09.qualify.Watersheds"

  case class Shed(y: Int, x: Int, geo: Array[Array[Int]]) {
    def altitude =
      if (y < 0 || y == geo.length || x < 0 || x == geo(0).length) 10001 else geo(y)(x)
  }

  def solveProblem(reader: Iterator[String]) = {
    val dimensions = reader.nextIntArray
    val (h, w) = (dimensions(0), dimensions(1))

    val geo: Array[Array[Int]] = (for (i <- 1 to h) yield reader.nextIntArray).toArray
    val heightMap = Array.fill(h)(Array.fill(w)('0'))

    var sink = 'a'

    def findSink(x: Int, y: Int) : Char = {
      val me = Shed(y, x, geo)
      val list = List(Shed(y - 1, x, geo), Shed(y, x - 1, geo), Shed(y, x + 1, geo), Shed(y + 1, x, geo)) // n, w, e, s
      val lowest = list.find(_.altitude == list.sortWith(_.altitude < _.altitude).head.altitude).get // The first one with lowest alt
      if (lowest.altitude < me.altitude) {
        heightMap(y)(x) = findSink(lowest.x, lowest.y)
      } else { // I'm a sink
        if (heightMap(y)(x) == '0') {
          heightMap(y)(x) = sink
          sink = (sink + 1).toChar
        }
      }
      heightMap(y)(x)
    }
    for (y <- 0 until h; x <- 0 until w; if heightMap(y)(x) == '0') {
      findSink(x, y)
    }

    "\n" + (for (i <- 0 until h) yield heightMap(i).mkString(" ")).mkString("\n")
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