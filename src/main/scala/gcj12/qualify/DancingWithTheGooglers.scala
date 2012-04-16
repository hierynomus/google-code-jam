package gcj12.qualify

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}

object DancingWithTheGooglers {

  import GoogleCodeHelper.iteratorToHelper

  def solveProblem(reader: Iterator[String]) = {
    val line = reader.nextIntArray
    val Array(nrGooglers, surprises, bestScore) = Array(line(0), line(1), line(2))
    val contestants = for (i <- 1 to nrGooglers) yield line(2 + i)
    val filter = contestants.filter(x => (x >= (bestScore * 3) - 4) && x >= bestScore) // filter out below (bestScore, bestScore - 2, bestScore - 2) --> worst surprise
    val partition = filter.partition(_ < (bestScore * 3) - 2)
    (partition._2.size + (if (partition._1.size < surprises) partition._1.size else surprises)).toString
  }

  def name = "gcj12.qualify.DancingWithTheGooglers"

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
      println("Solving case " + i)
      results(i - 1) = "Case #" + i + ": " + solveProblem(reader)
      println(results(i - 1))
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