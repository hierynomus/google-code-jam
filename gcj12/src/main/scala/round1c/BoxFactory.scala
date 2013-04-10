package round1c

import java.io.File
import scala.collection.mutable.{Map => MMap}
import scalax.io.{Output, Resource, Codec}
import scala.math._
import scala.BigDecimal
import scala.BigInt

object BoxFactory {

  import googlecodejam._

  case class Series(amount: BigInt, sort: Int)

  def solveProblem(reader: Iterator[String]) = {
    val Array(n, m) = reader.nextIntArray

    val boxesArray = reader.nextBigIntArray
    val toysArray = reader.nextBigIntArray
    val boxes = (for (i <- 0 until n) yield Series(boxesArray(2 * i), boxesArray(2 * i + 1).toInt)).toList
    val toys = (for (i <- 0 until m) yield Series(toysArray(2 * i), toysArray(2 * i + 1).toInt)).toList

    var boxedToys = 0

    val cache: MMap[(Int, Int), BigInt] = MMap()

    def rec(made: BigInt, b: List[Series], t: List[Series]): BigInt = {
      if (b.isEmpty || t.isEmpty) made
      else if (cache.contains((b.size, t.size))) cache((b.size, t.size))
      else {
        val box = b.head
        val toy = t.head
        if (box.sort == toy.sort) {
          if (box.amount == toy.amount) {
            rec(made + box.amount, b.tail, t.tail)
          } else if (box.amount < toy.amount) {
            val nowMade = made + box.amount
            rec(nowMade, b.tail, Series(toy.amount - box.amount, toy.sort) :: t.tail)
          } else {
            val nowMade = made + toy.amount
            rec(nowMade, Series(box.amount - toy.amount, box.sort) :: b.tail, t.tail)
          }

        } else {
          val discardBoxes = rec(made, b.dropWhile(x => t.head.sort != x.sort), t)
          val discardToys = rec(made, b, t.dropWhile(x => b.head.sort != x.sort))
          val max = if (discardBoxes < discardToys) discardToys else discardBoxes
          cache.put((b.size, t.size), max)
          max
        }
      }
    }

    val max = rec(0, boxes, toys)

    max.toString()
  }

  def name = "round1c.BoxFactory"

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
