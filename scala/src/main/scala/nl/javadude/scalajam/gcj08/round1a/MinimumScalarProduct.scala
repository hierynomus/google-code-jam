package nl.javadude.scalajam.gcj08.round1a
import nl.javadude.scalajam.utils.GoogleCodeHelper._

object MinimumScalarProduct extends CodeJam {
	def solveProblem(reader : Iterator[String]) = {
		reader next
		val ar1 = reader.nextLongArray.toList.sort (_ < _).map (BigDecimal(_))
		val ar2 = reader.nextLongArray.toList.sort (_ > _).map (BigDecimal(_))
		(for (x <- ar1; y <- ar2) yield (x * y)) reduceLeft (_ + _) toString
	}
}
