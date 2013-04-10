package qualify

import java.io.File
import scalax.io.{Output, Codec, Resource}
import scala.math._
import scala.BigInt
import scala.BigDecimal
import scala.Iterator

object RecycledNumbers {

  import googlecodejam._

  val combinationsOf2 = Map(0 -> 0, 1 -> 0, 2 -> 1, 3 -> 3, 4 -> 6, 5 -> 10, 6 -> 15)
  val largestMap = scala.collection.mutable.Map[Int, Int]()

  def findLargest(i: Int): Int = {
    if (largestMap.contains(i)) largestMap(i)
    else {
      val s = i.toString
      val doubleS = (s + s).toList
      val largest = (for (i <- 0 until s.size) yield doubleS.slice(i, i + s.size).mkString).max.toInt
      largestMap.put(i, largest)
      largest
    }
  }

  def solveProblem(reader: Iterator[String]) = {
    val Array(a, b) = reader.nextIntArray
    val array = Array.fill(10000000)(0)
    (for (i <- a to b) yield i).foreach(x => array(findLargest(x)) += 1)
    array.map(combinationsOf2(_)).sum.toString
  }

  def name = "qualify.RecycledNumbers"

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
