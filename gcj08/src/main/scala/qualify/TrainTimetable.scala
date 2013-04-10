package qualify

import java.io.File
import collection.immutable.List
import scalax.io.{Output, Resource, Codec}
import scala.math._
import scala.BigDecimal
import scala.BigInt

object TrainTimetable {

  import googlecodejam._

  def name = "qualify.TrainTimetable"

  case class Train(readyTime: Int) {
    def isReadyAt(time: Int) = readyTime <= time
  }

  case class Trip(departure: Int, arrival: Int, station: Int, turnAroundTime: Int) {
    def newTrain = Train(arrival + turnAroundTime)
  }

  def toTime(time: String): Int = {
    val t = time.split(':'); t(0).toInt * 60 + t(1).toInt
  }

  def solveProblem(reader: Iterator[String]) = {
    val turnAroundTime = reader.nextInt
    val intArray = reader.nextIntArray
    val (numberA, numberB) = (intArray(0), intArray(1))
    val tripsA = (for (i <- 1 to numberA) yield reader.nextStringArray).map {
      t => Trip(toTime(t(0)), toTime(t(1)), 0, turnAroundTime)
    }
    val tripsB = (for (i <- 1 to numberB) yield reader.nextStringArray).map {
      t => Trip(toTime(t(0)), toTime(t(1)), 1, turnAroundTime)
    }
    val sortedTrips = (tripsA ++ tripsB).sortBy(_.departure)

    val start = Array(0,0)

    def runTrips(trips: Seq[Trip], stations: (List[Train], List[Train])) {
      trips.size match {
        case 0 => return
        case _ => {
          val t = trips.head
//          printf("Trip: %s --> %s - [%s]\n", t, stations, start.mkString)
          if (t.station == 0) {
            if (stations._1.isEmpty || !stations._1.head.isReadyAt(t.departure)) {
              start(t.station) += 1
              runTrips(trips.tail, (stations._1, (t.newTrain :: stations._2).sortBy(_.readyTime)))
            } else {
              runTrips(trips.tail, (stations._1.tail, (t.newTrain :: stations._2).sortBy(_.readyTime)))
            }
          } else {
            if (stations._2.isEmpty || !stations._2.head.isReadyAt(t.departure)) {
              start(t.station) += 1
              runTrips(trips.tail, ((t.newTrain :: stations._1).sortBy(_.readyTime), stations._2))
            } else {
              runTrips(trips.tail, ((t.newTrain :: stations._1).sortBy(_.readyTime), stations._2.tail))
            }
          }
        }
      }
    }

    runTrips(sortedTrips, (List[Train](), List[Train]()))
    start.mkString(" ")
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
