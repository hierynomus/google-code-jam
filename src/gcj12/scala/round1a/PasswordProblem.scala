package round1a

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}

object PasswordProblem {

  import GoogleCodeHelper.iteratorToHelper

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

  def name = "PasswordProblem"

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

    def nextBigDecimalArray: Array[BigDecimal] = nextStringArray map (BigDecimal(_))

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
