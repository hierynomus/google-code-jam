package acj10.qualify

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}
import scalax.io.{Output, Resource, Codec}
import math._
import scala.BigDecimal
import scala.BigInt

object ReverseWords extends App {
  import googlecodejam._

  def solveProblem(reader: Iterator[String]) = {
    reader.nextStringArray.reverse.mkString(" ")
  }

  def name = "acj10.qualify.ReverseWords"

  implicit val codec = Codec.UTF8
  val input: String = args(0)
  val iterator: Iterator[String] = Resource.fromFile(input).lines().toIterator
  val file = new File(input.substring(0, input.lastIndexOf(".")) + ".out")
  file.delete()
  val output: Output = Resource.fromFile(file)

  val nrProblems = iterator.nextInt

  output.writeStrings((1 to nrProblems).map(p => "Case #" + p + ": " + solveProblem(iterator)), "\n")

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
