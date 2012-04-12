package acj10.qualify


import collection.Iterator
import io.Source
import java.io.{FileWriter, File}

object T9Spelling {
  import GoogleCodeHelper.iteratorToHelper

  val t9Map = Map('a'->2, 'b'->22, 'c'->222, 'd'->3, 'e'->33, 'f'->333, 'g'->4, 'h'->44, 'i'->444, 'j'->5, 'k'->55, 'l'->555, 'm'->6, 'n'->66, 'o'->666,
                  'p'->7, 'q'->77, 'r'->777, 's'->7777, 't'->8, 'u'->88, 'v'->888, 'w'->9, 'x'->99, 'y'->999, 'z'->9999, ' '->0)

  def solveProblem(reader: Iterator[String]) = {
    reader.next().map(t9Map(_)).foldLeft("")((s: String, next: Int) => if (s.endsWith(next.toString.charAt(0).toString)) s + " " + next else s + next)
  }

  def name = "acj10.qualify.T9Spelling"
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