package round1b

import collection.Iterator
import java.io.File
import scalax.io.{Output, Resource, Codec}
import scala.math._
import scala.BigDecimal
import scala.BigInt

object CropTriangles {

  import googlecodejam._

  def solveProblem(reader: Iterator[String]) = {
    val Array(n, a, b, c, d, x0, y0, m) = reader.nextLongArray
    var (x, y) = (x0, y0)

    def bucket(x: Long, y: Long) = ((x % 3) * 3 + (y % 3)).toInt

    val trees = (bucket(x, y) :: (for (i <- 1 to (n - 1).toInt) yield {
      x = (a * x + b) % m; y = (c * y + d) % m; bucket(x, y)
    }).toList).groupBy(x => x).map(t => (t._1, t._2.size)).withDefaultValue(0)

    def isCenterAtGrid(i: Int, j: Int, k: Int) : Boolean = {
      ((((i / 3) + (j / 3) + (k / 3)) % 3) == 0) && ((((i % 3) + (j % 3) + (k % 3)) % 3) == 0)
    }

    (for (i <- 0 until 9) yield trees(i) * trees(i) - 1 * trees(i) - 2 / 6).reduce(_+_) +
    (for (i <- 0 until 9; j <- i + 1 until 9; k <- j + 1 until 9; if (isCenterAtGrid(i, j, k))) yield trees(i)*trees(j)*trees(k)).reduce(_+_)
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
