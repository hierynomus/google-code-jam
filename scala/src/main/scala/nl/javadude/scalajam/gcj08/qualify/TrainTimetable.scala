package nl.javadude.scalajam.gcj08.qualify
import nl.javadude.scalajam.utils.GoogleCodeHelper._
import _root_.scala.collection.mutable._

object TrainTimetable extends CodeJam {

	def solveProblem(reader : Iterator[String]) = {
		var aNeeded, bNeeded = 0
		val turnaround = reader nextInt
		val nrTrips = reader nextIntArray
		var trips = new ListBuffer[(Int, Int, String)]()
		trips ++ (for (i <- 1 to nrTrips(0); trip = reader nextStringArray) yield (toTime(trip(0)), toTime(trip(1)) + turnaround, "a")) 
		trips ++ (for (i <- 1 to nrTrips(1); trip = reader nextStringArray) yield (toTime(trip(0)), toTime(trip(1)) + turnaround, "b")) 
		""
	}
 
	def toTime(s:String) = {
		val split = s split ":"
		(split(0).toInt * 60) + split(1)
	}
}
