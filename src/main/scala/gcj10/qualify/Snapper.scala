package gcj10.qualify


import collection.Iterator
import io.Source
import java.io.{FileWriter, File}

object Snapper {
  import GoogleCodeHelper.iteratorToHelper

  def name = "gcj10.qualify.Snapper"

  def solveProblem(reader: Iterator[String]) = {
    val array: Array[BigInt] = reader.nextBigIntArray
    val (n, k) = (array(0).toInt, array(1))

    (for (i <- 0.until(n)) yield k.testBit(i)).reduceLeft(_ && _) match {
      case true => "ON"
      case false => "OFF"
    }
  }

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