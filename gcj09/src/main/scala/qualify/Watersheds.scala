package qualify

import java.io.File
import scalax.io.{Output, Resource, Codec}
import scala.math._
import scala.BigInt
import scala.BigDecimal


object Watersheds {

  import googlecodejam._
  def name = "qualify.Watersheds"

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
    implicit val codec = Codec.UTF8
    val input: String = args(0)
    val iterator: Iterator[String] = Resource.fromFile(input).lines().toIterator
    val file = new File(input.substring(0, input.lastIndexOf(".")) + ".out")
    file.delete()
    val output: Output = Resource.fromFile(file)

    val nrProblems = iterator.nextInt

    output.writeStrings((1 to nrProblems).map(p => "Case #" + p + ": " + solveProblem(iterator)), "\n")
  }

  object googlecodejam {
    case class Point(x: Int, y: Int) {
      def dist(p: Point) = sqrt(pow(x - p.x, 2) + pow(y - p.y, 2))
    }
    case class FPoint(x: Double, y: Double) {
      def dist(p: FPoint) = sqrt(pow(x - p.x, 2) + pow(y - p.y, 2))
    }

    class RichIterator(val iterator: Iterator[String]) {

      def trimmedLine = iterator.next().trim()
      def nextInt: Int = trimmedLine.toInt
      def nextIntArray: Array[Int] = nextStringArray map (_.toInt)
      def nextCharArray: Array[Char] = iterator.next().toCharArray
      def nextLongArray: Array[Long] = nextStringArray map (_.toLong)
      def nextDoubleArray: Array[Double] = nextStringArray map (_.toDouble)
      def nextBigDecimalArray: Array[BigDecimal] = nextStringArray map (BigDecimal(_))
      def nextBigIntArray: Array[BigInt] = nextStringArray map (BigInt(_))
      def nextStringArray: Array[String] = trimmedLine split " "
      def nextIntGrid(y: Int): Array[Array[Int]] = (for (i <- 0 until y) yield nextIntArray).toArray
      def nextCharGrid(y: Int): Array[Array[Char]] = (for (i <- 0 until y) yield nextCharArray).toArray

      def skipLines(nr: Int) {
        for (i <- 1 to nr) {
          iterator.next()
        }
      }
    }

    implicit def iteratorToHelper(x: Iterator[String]): RichIterator = new RichIterator(x)
  }
}
