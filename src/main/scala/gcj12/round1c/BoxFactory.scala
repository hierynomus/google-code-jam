package gcj12.round1c

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}
import scala.collection.mutable.{Map => MMap}

object BoxFactory {

  import GoogleCodeHelper.iteratorToHelper

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

  def name = "gcj12.round1c.BoxFactory"

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

    def nextDoubleArray: Array[Double] = nextStringArray map (_.toDouble)

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