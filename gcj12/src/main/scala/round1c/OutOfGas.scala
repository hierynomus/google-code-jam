package round1c

import java.io.File
import scalax.io.{Output, Resource, Codec}
import scala.math._
import scala.BigInt
import scala.BigDecimal

object OutOfGas {

  import googlecodejam._

  case class Position(time: BigDecimal, pos: BigDecimal)

  def solveProblem(reader: Iterator[String]) = {
    val r = reader.nextStringArray
    val (d, n, a) = (BigDecimal(r(0)), r(1).toInt, r(2).toInt)

    val otherCarPos: IndexedSeq[Position] = (for (i <- 1 to n) yield reader.nextBigDecimalArray).map(a => Position(a(0), a(1)))

    val accelerations = reader.nextBigDecimalArray.toList

    println("d, a: " + (d, a))
    println(otherCarPos)
    println(accelerations)

    val where = otherCarPos.indexWhere(p => p.pos >= d)
    val tx1: Position = otherCarPos(where)
    val otherPassesHome = if (tx1.pos == d) tx1.time else {
      val tx0 = otherCarPos(where - 1)
      val travelled = tx1.pos - tx0.pos
      val elapsed = tx1.time - tx0.time
      val speed = travelled / elapsed
      tx0.time + ((d - tx0.pos) / speed)
    }

    println(otherPassesHome)

    def distTravelled(v0: BigDecimal, accel: BigDecimal, t: BigDecimal) = v0 * t + BigDecimal(0.5) * accel * t * t

    def calc(accel: BigDecimal, positions: List[Position]) : BigDecimal = {
      val atHomeUnhindered = BigDecimal(scala.math.sqrt((d / (BigDecimal(0.5) * accel)).toDouble))
      println(atHomeUnhindered)
      if (atHomeUnhindered > otherPassesHome) atHomeUnhindered else {
        BigDecimal(0)
      }
    }

    def calc2(accel: BigDecimal, positions: List[Position]) : BigDecimal = {
      var elapsed = BigDecimal(0)
      var v = BigDecimal(0)
      var travelled = BigDecimal(0)
      var timeReachedHome = BigDecimal(0)
      for (p <- positions; if timeReachedHome == BigDecimal(0)) {
        val travelledAtT = distTravelled(v, accel, p.time - elapsed)
        val totalTravelled = travelled + travelledAtT
        if (totalTravelled <= p.pos) {
          if (totalTravelled < d) {
            // keep on accelerating
          } else if (totalTravelled > d) {
            // we raced past home, traceback
          } else if (totalTravelled == d) {
            // at home
            timeReachedHome = p.time
          }
        } else {
          // we passed other car, can't happen

        }
        println("%f, %f, %f: ".format(elapsed, v, travelled))
      }

      timeReachedHome
    }


    "\n" + accelerations.map(a => calc2(a, otherCarPos.toList)).mkString("\n")
  }

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
