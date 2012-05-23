package gcj12.qualify

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}

object SpeakingInTongues {

  import GoogleCodeHelper.iteratorToHelper

  def translation = Map[Char, Char](
    'a' -> 'y',
    'b' -> 'h',
    'c' -> 'e',
    'd' -> 's',
    'e' -> 'o',
    'f' -> 'c',
    'g' -> 'v',
    'h' -> 'x',
    'i' -> 'd',
    'j' -> 'u',
    'k' -> 'i',
    'l' -> 'g',
    'm' -> 'l',
    'n' -> 'b',
    'o' -> 'k',
    'p' -> 'r',
    'q' -> 'z',
    'r' -> 't',
    's' -> 'n',
    't' -> 'w',
    'u' -> 'j',
    'v' -> 'p',
    'w' -> 'f',
    'x' -> 'm',
    'y' -> 'a',
    'z' -> 'q',
    ' ' -> ' '
  )

  def solveProblem(reader: Iterator[String]) = {
    reader.next().map(translation(_)).mkString
  }

  def name = "gcj12.qualify.SpeakingInTongues"

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