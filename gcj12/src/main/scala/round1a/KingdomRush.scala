package round1a

import java.io.File
import scalax.io.{Output, Resource, Codec}
import scala.math._
import scala.BigDecimal
import scala.BigInt

object KingdomRush {

  import googlecodejam._

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
          "Too Bad"
        }
      }
    }

    def levelsCompletableOn1(allLevels: IndexedSeq[Int], currentStars: Int): IndexedSeq[Int] = allLevels.filter(levelMap(_)._1 <= currentStars)

    val sortedOn2Star = levels.sortBy(_._2._2)
    val sortedOn1Star = sortedOn2Star.reverse

    rec(0, 0, sortedOn1Star.map(_._1), sortedOn2Star.map(_._1))
  }

  def name = "round1a.KingdomRush"

  def main(args: Array[String]) {
    implicit val codec = Codec.UTF8
    val input: String = args(0)
    val iterator: Iterator[String] = Resource.fromFile(input).lines().toIterator
    val file = new File(input.substring(0, input.lastIndexOf(".")) + ".out")
    file.delete()
    val output: Output = Resource.fromFile(file)

    val nrProblems = iterator.nextInt

    output.writeStrings((1 to nrProblems).map(p => "Case #" + p + ": " + solveProblem(iterator)), "\n")
  }

  object googlecodejam {
    case class Point(x: Int, y: Int) {
      def dist(p: Point) = sqrt(pow(x - p.x, 2) + pow(y - p.y, 2))
    }
    case class FPoint(x: Double, y: Double) {
      def dist(p: FPoint) = sqrt(pow(x - p.x, 2) + pow(y - p.y, 2))
    }

    class RichIterator(val iterator: Iterator[String]) {

      def trimmedLine = iterator.next().trim()
      def nextInt: Int = trimmedLine.toInt
      def nextIntArray: Array[Int] = nextStringArray map (_.toInt)
      def nextCharArray: Array[Char] = iterator.next().toCharArray
      def nextLongArray: Array[Long] = nextStringArray map (_.toLong)
      def nextDoubleArray: Array[Double] = nextStringArray map (_.toDouble)
      def nextBigDecimalArray: Array[BigDecimal] = nextStringArray map (BigDecimal(_))
      def nextBigIntArray: Array[BigInt] = nextStringArray map (BigInt(_))
      def nextStringArray: Array[String] = trimmedLine split " "
      def nextIntGrid(y: Int): Array[Array[Int]] = (for (i <- 0 until y) yield nextIntArray).toArray
      def nextCharGrid(y: Int): Array[Array[Char]] = (for (i <- 0 until y) yield nextCharArray).toArray

      def skipLines(nr: Int) {
        for (i <- 1 to nr) {
          iterator.next()
        }
      }
    }

    implicit def iteratorToHelper(x: Iterator[String]): RichIterator = new RichIterator(x)
  }
}
