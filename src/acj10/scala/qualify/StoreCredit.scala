package qualify

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}
import scalax.io.{Output, Resource, Codec}
import math._
import scala.BigInt
import scala.BigDecimal

object StoreCredit extends App {
  import googlecodejam._

  case class Product(price: Int, index: Int)
  def solveProblem(reader: Iterator[String]) = {
    val credit = reader.nextInt
    reader.skipLines(1)
    val prices = reader.nextIntArray
    val sortedProducts = (for (i <- 0 until prices.length) yield Product(prices(i), i + 1)).sortBy(_.price)

    def findProducts(i: Int, j: Int) : List[Product] = {
      val productTotal = sortedProducts(i).price + sortedProducts(j).price
      if (productTotal < credit) findProducts(i+1, j)
      else if (productTotal > credit) findProducts(i, j - 1)
      else List(sortedProducts(i), sortedProducts(j)).sortBy(_.index)
    }

    findProducts(0, sortedProducts.length - 1).map(_.index).mkString(" ")
  }

  def name = "qualify.StoreCredit"

  implicit val codec = Codec.UTF8
  val input: String = args(0)
  val iterator: Iterator[String] = Resource.fromFile(input).lines().toIterator
  val file = new File(input.substring(0, input.lastIndexOf(".")) + ".out")
  file.delete()
  val output: Output = Resource.fromFile(file)

  val nrProblems = iterator.nextInt

  output.writeStrings((1 to nrProblems).map(p => "Case #" + p + ": " + solveProblem(iterator)), "\n")

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
