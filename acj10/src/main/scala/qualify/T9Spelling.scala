package qualify

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}
import scalax.io.{Output, Resource, Codec}
import math._
import scala.BigInt
import scala.BigDecimal

object T9Spelling {
  import googlecodejam._

  val t9Map = Map('a'->2, 'b'->22, 'c'->222, 'd'->3, 'e'->33, 'f'->333, 'g'->4, 'h'->44, 'i'->444, 'j'->5, 'k'->55, 'l'->555, 'm'->6, 'n'->66, 'o'->666,
    'p'->7, 'q'->77, 'r'->777, 's'->7777, 't'->8, 'u'->88, 'v'->888, 'w'->9, 'x'->99, 'y'->999, 'z'->9999, ' '->0)

  def solveProblem(reader: Iterator[String]) = {
    reader.next().map(t9Map(_)).foldLeft("")((s: String, next: Int) => if (s.endsWith(next.toString.charAt(0).toString)) s + " " + next else s + next)
  }

  def name = "qualify.T9Spelling"

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
