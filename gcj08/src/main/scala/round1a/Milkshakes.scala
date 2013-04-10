package round1a

import collection.immutable.{Map, IndexedSeq}
import scalax.io.{Output, Resource, Codec}
import scala.Iterator
import scala.math._
import scala.BigDecimal
import scala.Some
import scala.BigInt
import java.io.File

object Milkshakes {
  import googlecodejam._

  def name = "round1a.Milkshakes"

  case class Customer(map: Map[Int, Boolean]) {
    def isSatisfiedBy(flavours: Array[Boolean]) = map.exists{ case (i, b) => flavours(i) == b}
    def maltedFlavour = map.find(_._2).map(_._1)
  }

  def satisfy(flavours: Array[Boolean], customer: Customer): Option[Array[Boolean]] = {
    customer.maltedFlavour match {
      case Some(x) => flavours(x) = true; Some(flavours)
      case None => None
    }
  }

  def solve(flavours: Array[Boolean], customers: IndexedSeq[Customer], unsatisfied: IndexedSeq[Customer]) : Option[Array[Boolean]] = unsatisfied.size match {
    case 0 => Some(flavours)
    case _ => satisfy(flavours, unsatisfied.head).flatMap { f => solve(f, customers, customers.filterNot{ c => c.isSatisfiedBy(f) })}
  }

  def solveProblem(reader: Iterator[String]) = {
    val nrFlavours = reader.nextInt
    val flavours : Array[Boolean] = new Array[Boolean](nrFlavours)
    val customers = (for (i <- 1 to reader.nextInt; c = reader.nextIntArray) yield (1 until(c.size, 2)).map(j => (c(j) - 1, c(j+1) != 0)).toMap).map(m => Customer(m))

    solve(flavours, customers, customers.filterNot(_.isSatisfiedBy(flavours))) match {
      case None => "IMPOSSIBLE"
      case Some(x) => x.map(if (_) 1 else 0).mkString(" ")
    }
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
