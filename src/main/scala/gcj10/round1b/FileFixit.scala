package gcj10.round1b


import collection.Iterator
import io.Source
import java.io.{FileWriter, File}

object FileFixit {
  import GoogleCodeHelper.iteratorToHelper

  def name = "gcj10.round1b.FileFixit"

  def solveProblem(reader: Iterator[String]) = {
    val arr = reader.nextIntArray
    var existing = (for (i <- 1 to arr(0)) yield reader.next()).toList
    var mkdirs = 0

    for (i <- 1 to arr(1)) {
      val toCreate = reader.next()
      val split: Array[String] = toCreate.split('/')
      var dir = ""
      for (j <- 1 until split.length) {
        dir += "/" + split(j)
        if (!existing.contains(dir)) {
          mkdirs+=1
          existing = dir :: existing
        }
      }
    }

    mkdirs.toString
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