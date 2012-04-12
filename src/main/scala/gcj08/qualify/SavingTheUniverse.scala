package gcj08.qualify

import io.Source
import java.io.{File, FileWriter}

object SavingTheUniverse {

  import GoogleCodeHelper.iteratorToHelper

  def solveProblem(reader: Iterator[String]) = {
    val nrEngines = reader.nextInt
    reader.skipLines(nrEngines)
    ((for (i <- 1 to reader.nextInt) yield reader.next()).foldLeft((0, Set[String]())) {
      (t, s) =>
        if ((t._2 + s).size == nrEngines) (t._1 + 1, Set(s)) else (t._1, t._2 + s)
    })._1
  }

  def name = "gcj08.qualify.SavingTheUniverse"

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
