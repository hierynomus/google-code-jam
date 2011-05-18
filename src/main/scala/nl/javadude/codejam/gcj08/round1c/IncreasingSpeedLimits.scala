package nl.javadude.codejam.gcj08.round1c
import nl.javadude.scala.utils.GoogleCodeHelper._
import nl.javadude.scala.CodeJam

class IncreasingSpeedLimits extends CodeJam {
	def solveProblem(reader : Iterator[String]) : String = {
		val ar = reader.nextIntArray
		val (n, m, x, y, z) = (ar(0), ar(1), ar(2), ar(3), ar(4))
		val gen = 0 until m map (x => reader.nextInt)
		""
	}
}
