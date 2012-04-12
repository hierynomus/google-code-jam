package acj10.qualify

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}

object StoreCredit {
  import GoogleCodeHelper.iteratorToHelper

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

  def name = "acj10.qualify.StoreCredit"
  def main(args: Array[String]) {
    if (args.length == 0) {
      throw new IllegalArgumentException("Could not find input file in arguments")
    }

    val input = args(0)
    val outputFile = new File((input.substring(0, input.lastIndexOf("."))) + ".out")
    val reader: Iterator[String] = Source.fromFile(input).getLines()
    val nrProblems = reader.nextInt
    val results = new Array[String](nrProblems)
    for (i <- 1 to nrProblems) {
      results(i - 1) = "Case #" + i + ": " + solveProblem(reader)
    }
    val fw = new FileWriter(outputFile)
    try {
      fw.write(results.mkString("\n"))
    } finally {
      fw.close()
    }
  }

  class GoogleCodeHelper(val iterator: Iterator[String]) {

    def trimmedLine = iterator.next().trim()
    def nextInt: Int = trimmedLine.toInt
    def nextIntArray: Array[Int] = nextStringArray map (_.toInt)
    def nextCharArray: Array[Char] = iterator.next().toCharArray
    def nextLongArray: Array[Long] = nextStringArray map (_.toLong)
    def nextBigDecimalArray: Array[BigDecimal] = nextLongArray map (BigDecimal(_))
    def nextBigIntArray: Array[BigInt] = nextStringArray map (BigInt(_))
    def nextStringArray: Array[String] = trimmedLine split " "

    def skipLines(nr: Int) {
      for (i <- 1 to nr) {
        iterator.next()
      }
    }
  }

  object GoogleCodeHelper {
    implicit def iteratorToHelper(x: Iterator[String]): GoogleCodeHelper = new GoogleCodeHelper(x)
  }
}