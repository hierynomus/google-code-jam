package gcj12.qualify

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}

object RecycledNumbers {

  import GoogleCodeHelper.iteratorToHelper

  val combinationsOf2 = Map(0 -> 0, 1 -> 0, 2 -> 1, 3 -> 3, 4 -> 6, 5 -> 10, 6 -> 15)
  val largestMap = scala.collection.mutable.Map[Int, Int]()

  def findLargest(i: Int): Int = {
    if (largestMap.contains(i)) largestMap(i)
    else {
      val s = i.toString
      val doubleS = (s + s).toList
      val largest = (for (i <- 0 until s.size) yield doubleS.slice(i, i + s.size).mkString).max.toInt
      largestMap.put(i, largest)
      largest
    }
  }

  def solveProblem(reader: Iterator[String]) = {
    val Array(a, b) = reader.nextIntArray
    val array = Array.fill(10000000)(0)
    (for (i <- a to b) yield i).foreach(x => array(findLargest(x)) += 1)
    array.map(combinationsOf2(_)).sum.toString
  }

  def name = "gcj12.qualify.RecycledNumbers"

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