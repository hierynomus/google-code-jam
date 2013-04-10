package round1a

import java.io.File
import scalax.io.{Output, Resource, Codec}
import scala.math._
import scala.BigDecimal
import scala.BigInt

object PasswordProblem {

  import googlecodejam._

  def solveProblem(reader: Iterator[String]) = {
    val Array(charactersTyped, totalChars) = reader.nextIntArray
    val chances = reader.nextBigDecimalArray

    /**
     * a = typed
     * b = total
     * i = characters left
     * k = a - i (nr backspaces)
     * when correct: b - (a - k) + k + 1 == b - a + 2k + 1
     * when incorrect: (b - a + 2k + 1) + b + 1 == 2b - a + 2k + 2
     * @param i
     * @return
     */
    def calc(i: Int) = {
      val x = chances(i - 1)
      val a = charactersTyped
      val b = totalChars
      val k = charactersTyped - i
      val charsNeededToFinish = b - a + 2 * k + 1
      val charsNeededWhenWrong = 2 * b - a + 2 * k + 2
      x * charsNeededToFinish + (1 - x) * charsNeededWhenWrong
    }


    for (i <- 1 until chances.size) {
      chances(i) = chances(i - 1) * chances(i)
    }

    val seq = for (i <- 1 to chances.size) yield calc(i)

    val head = seq.sorted.head

    val charsNeededWhenEnter = BigDecimal(totalChars + 2)
    (if (head < charsNeededWhenEnter) "%.6f".format(head) else "%.6f".format(charsNeededWhenEnter)).replace(',','.')
  }

  def name = "round1a.PasswordProblem"

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
