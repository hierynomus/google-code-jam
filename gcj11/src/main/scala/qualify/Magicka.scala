package qualify

import java.io.File
import collection.immutable.List
import scalax.io.{Output, Resource, Codec}
import scala.math._
import scala.BigInt
import scala.BigDecimal

object Magicka {
  import googlecodejam._

  def solveProblem(reader: Iterator[String]) = {
    val line = reader.nextStringArray
    val c = line(0).toInt
    val combinations = (for (i <- 1 to c) yield line(i)).map(s => List((s.substring(0, 2), s(2)), (s.substring(0, 2).reverse, s(2)))).flatten.toMap
    val d = line(c + 1).toInt
    val oppositions = (for (i <- 1 to d) yield line(c + 1 + i)).map(s => (s(0), s(1)))

    val invocation = line(c + d + 3)

    invocation.foldLeft(List[Char]())((i: List[Char], x: Char) =>
      if (i.length == 0) x :: i
      else {
        val p = i.head.toString + x
        if (combinations.contains(p)) combinations(p) :: i.tail
        else if (oppositions.exists(p => (x :: i).contains(p._1) && (x :: i).contains(p._2))) Nil
        else x :: i
      }).reverse.mkString("[", ", ", "]")

  }

  def name = "qualify.Magicka"

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
