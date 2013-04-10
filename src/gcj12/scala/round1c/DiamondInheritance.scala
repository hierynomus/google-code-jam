package round1c

import collection.immutable.IndexedSeq
import collection.Iterator
import io.Source
import java.io.{FileWriter, File}
import scala.collection.mutable.{Map => MMap}

object DiamondInheritance {

  import GoogleCodeHelper.iteratorToHelper


  def solveProblem(reader: Iterator[String]) = {
    val nrClasses = reader.nextInt
    val seq: IndexedSeq[Array[Int]] = for (i <- 1 to nrClasses) yield reader.nextIntArray

    val map: MMap[Int, List[Int]] = MMap()

    var roots: List[Int] = Nil
    for (i <- 1 to nrClasses) {
      val seq1: Array[Int] = seq(i - 1)
      val tail = seq1.tail
      if (tail.isEmpty) roots = i :: roots
      else {
        for (j <- tail) {
          if (map.contains(j)) map(j) = i :: map(j) else map(j) = i :: Nil
        }
      }
    }

    var cycle = false
    for (r <- roots; if !cycle) {
      val seen: Array[Boolean] = Array.fill(nrClasses)(false)
      def trace(r: Int): Boolean = {
        val map1: List[Int] = if (map.contains(r)) map(r) else Nil
        map1.foreach(x => if (seen(x - 1)) cycle = true else seen(x - 1) = true)
        if (cycle) true
        else map1.map(trace(_)).exists(x => x)
      }

      trace(r)
    }

    if (cycle) "Yes" else "No"
  }

  def name = "DiamondInheritance"

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
