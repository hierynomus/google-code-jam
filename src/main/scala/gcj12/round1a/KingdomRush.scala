package gcj12.round1a

import collection.immutable.IndexedSeq
import collection.Iterator
import io.Source
import java.io.{FileWriter, File}

object KingdomRush {

  import GoogleCodeHelper.iteratorToHelper

  def solveProblem(reader: Iterator[String]) = {
    val nrLevels = reader.nextInt
    val levels: IndexedSeq[(Int, (Int, Int))] = (for (i <- 1 to nrLevels) yield {
      val Array(a, b) = reader.nextIntArray; i -> (a -> b)
    })
    //println(levels)

    val levelMap = levels.toMap

    def rec(completionsNeeded: Int, currentStars: Int, levelsOn1: IndexedSeq[Int], levelsOn2: IndexedSeq[Int]): String = {
      val canBeCompletedOn1 = levelsCompletableOn1(levelsOn1, currentStars)
      if (levelsOn2.isEmpty) {
        completionsNeeded.toString
      } else if (canBeCompletedOn1.isEmpty && currentStars < levelMap(levelsOn2.head)._2) {
        "Too Bad"
      } else if (currentStars >= levelMap(levelsOn2.head)._2) {
        val level2Completed = levelsOn2.head
        val completedLevel1Before = !levelsOn1.contains(level2Completed)
        val newStars = currentStars + (if (completedLevel1Before) 1 else 2)
        rec(completionsNeeded + 1, newStars, levelsOn1.filter(_ != level2Completed), levelsOn2.tail)
      } else {
        if (!canBeCompletedOn1.isEmpty) {
          val level1Completed = canBeCompletedOn1.head
          val newStars = currentStars + 1
          rec(completionsNeeded + 1, newStars, levelsOn1.filter(_ != level1Completed), levelsOn2)
        } else {
          println("missed condition")
          "Too Bad"
        }
      }
    }

    def levelsCompletableOn1(allLevels: IndexedSeq[Int], currentStars: Int): IndexedSeq[Int] = allLevels.filter(levelMap(_)._1 <= currentStars)

    val sortedOn2Star = levels.sortBy(_._2._2)
    val sortedOn1Star = sortedOn2Star.reverse

    rec(0, 0, sortedOn1Star.map(_._1), sortedOn2Star.map(_._1))
  }

  def name = "gcj12.round1a.KingdomRush"

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