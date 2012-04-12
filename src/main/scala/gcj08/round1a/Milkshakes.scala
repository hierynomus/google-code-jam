package gcj08.round1a

import io.Source
import java.io.{FileWriter, File}
import collection.immutable.{Map, IndexedSeq}

object Milkshakes {
  import GoogleCodeHelper.iteratorToHelper

  def name = "gcj08.round1a.Milkshakes"

  case class Customer(map: Map[Int, Boolean]) {
    def isSatisfiedBy(flavours: Array[Boolean]) = map.exists{ case (i, b) => flavours(i) == b}
    def maltedFlavour = map.find(_._2).map(_._1)
  }

  def satisfy(flavours: Array[Boolean], customer: Customer): Option[Array[Boolean]] = {
    customer.maltedFlavour match {
      case Some(x) => flavours(x) = true; Some(flavours)
      case None => None
    }
  }

  def solve(flavours: Array[Boolean], customers: IndexedSeq[Customer], unsatisfied: IndexedSeq[Customer]) : Option[Array[Boolean]] = unsatisfied.size match {
    case 0 => Some(flavours)
    case _ => satisfy(flavours, unsatisfied.head).flatMap { f => solve(f, customers, customers.filterNot{ c => c.isSatisfiedBy(f) })}
  }

  def solveProblem(reader: Iterator[String]) = {
    val nrFlavours = reader.nextInt
    val flavours : Array[Boolean] = new Array[Boolean](nrFlavours)
    val customers = (for (i <- 1 to reader.nextInt; c = reader.nextIntArray) yield (1 until(c.size, 2)).map(j => (c(j) - 1, c(j+1) != 0)).toMap).map(m => Customer(m))

    solve(flavours, customers, customers.filterNot(_.isSatisfiedBy(flavours))) match {
      case None => "IMPOSSIBLE"
      case Some(x) => x.map(if (_) 1 else 0).mkString(" ")
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
