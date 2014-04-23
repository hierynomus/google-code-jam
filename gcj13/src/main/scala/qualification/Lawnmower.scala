package qualification

import java.io.File
import scalax.io.{Output, Resource, Codec}
import scala.math._

object Lawnmower {
  import googlecodejam._


  def solveProblem(reader: Iterator[String]) = {
    val Array(height, width) = reader.nextIntArray

    def findColMax(lawn: Array[Array[Int]], col: Int) = {
      (for (h <- 0 until height) yield lawn(h)(col)).max
    }

    val myLawn: Array[Array[Int]] = Array.fill(height, width)(100)
    val wantedLawn: Array[Array[Int]] = (for (i <- 0 until height) yield reader.nextIntArray).toArray

    0.until(height).foreach { row =>
      val rowMax = wantedLawn(row).max
      0.until(width).foreach(c => myLawn(row)(c) = rowMax)
    }

    0.until(width).foreach { c =>
      val colMax = findColMax(wantedLawn, c)
      0.until(height).foreach(row => if (myLawn(row)(c) > colMax) myLawn(row)(c) = colMax)
    }

    if (wrapIntArray(wantedLawn.flatten).zip(wrapIntArray(myLawn.flatten)) forall (t => t._1 == t._2)) "YES" else "NO"
  }

  def name = "qualification.Lawnmower"

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
