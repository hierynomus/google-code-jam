package qualify

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}
import collection.immutable.{List, IndexedSeq}

object Magicka {
  import GoogleCodeHelper.iteratorToHelper

  def solveProblem(reader: Iterator[String]) = {
    val line = reader.nextStringArray
    val c = line(0).toInt
    val combinations = (for (i <- 1 to c) yield line(i)).map(s => List((s.substring(0, 2), s(2)), (s.substring(0, 2).reverse, s(2)))).flatten.toMap
    val d = line(c + 1).toInt
    val oppositions = (for (i <- 1 to d) yield line(c + 1 + i)).map(s => (s(0), s(1)))

    val invocation = line(c + d + 3)

    invocation.foldLeft(List[Char]())((i: List[Char], x: Char) =>
      if (i.length == 0) x :: i
      else {
        val p = i.head.toString + x
        if (combinations.contains(p)) combinations(p) :: i.tail
        else if (oppositions.exists(p => (x :: i).contains(p._1) && (x :: i).contains(p._2))) Nil
        else x :: i
      }).reverse.mkString("[", ", ", "]")

  }

  def name = "qualify.Magicka"

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
