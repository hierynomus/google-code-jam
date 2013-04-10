package round1c

import java.io.File
import scala.collection.mutable.{Map => MMap}
import scalax.io.{Output, Resource, Codec}
import scala.math._
import scala.BigDecimal
import scala.BigInt

object DiamondInheritance {

  import googlecodejam._

  def solveProblem(reader: Iterator[String]) = {
    val nrClasses = reader.nextInt
    val seq: IndexedSeq[Array[Int]] = for (i <- 1 to nrClasses) yield reader.nextIntArray

    val map: MMap[Int, List[Int]] = MMap()

    var roots: List[Int] = Nil
    for (i <- 1 to nrClasses) {
      val seq1: Array[Int] = seq(i - 1)
      val tail = seq1.tail
      if (tail.isEmpty) roots = i :: roots
      else {
        for (j <- tail) {
          if (map.contains(j)) map(j) = i :: map(j) else map(j) = i :: Nil
        }
      }
    }

    var cycle = false
    for (r <- roots; if !cycle) {
      val seen: Array[Boolean] = Array.fill(nrClasses)(false)
      def trace(r: Int): Boolean = {
        val map1: List[Int] = if (map.contains(r)) map(r) else Nil
        map1.foreach(x => if (seen(x - 1)) cycle = true else seen(x - 1) = true)
        if (cycle) true
        else map1.map(trace(_)).exists(x => x)
      }

      trace(r)
    }

    if (cycle) "Yes" else "No"
  }

  def name = "round1c.DiamondInheritance"

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
