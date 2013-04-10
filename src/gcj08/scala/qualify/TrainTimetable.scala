package qualify

import io.Source
import java.io.{File, FileWriter}
import collection.Seq
import collection.immutable.{List, IndexedSeq}

object TrainTimetable {

  import GoogleCodeHelper.iteratorToHelper

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
    if (args.length == 0) {
      throw new IllegalArgumentException("Could not find input file in arguments")
    }

    val input = args(0)
    val outputFile = new File((input.substring(0, input.lastIndexOf("."))) + ".out")
    val reader: Iterator[String] = Source.fromFile(input).getLines()
    val nrProblems = reader.nextInt

    val results = new Array[String](nrProblems)
    for (i <- 1 to nrProblems) {
//      println("Solving case " + i)
      results(i - 1) = "Case #" + i + ": " + solveProblem(reader)
//      println(results(i - 1))
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
