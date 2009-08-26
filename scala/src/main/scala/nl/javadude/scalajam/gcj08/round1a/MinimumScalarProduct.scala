package nl.javadude.scalajam.gcj08.round1a
import nl.javadude.scalajam.utils.GoogleCodeHelper._

object MinimumScalarProduct extends CodeJam {
	def solveProblem(reader : Iterator[String]) = {
		reader next
		val ar1 = reader.nextLongArray.toList.sort (_ < _).map (BigDecimal(_))
		val ar2 = reader.nextLongArray.toList.sort (_ > _).map (BigDecimal(_))
		val xx = (ar1 zip ar2) map (t => t._1 * t._2)
		xx reduceLeft (_ + _) toString
	}
}
