package nl.javadude.scalajam.gcj08.round1a
import nl.javadude.scalajam.utils.GoogleCodeHelper._

/**
 * VERIFIED against Google Code Jam
 * @author Jeroen van Erp - jeroen at hierynomus.com
 */
object MinimumScalarProduct extends CodeJam {
	def solveProblem(reader : Iterator[String]) = {
		reader next
		val ar1 = reader.nextBigDecimalArray.toList.sort (_ < _)
		val ar2 = reader.nextBigDecimalArray.toList.sort (_ > _)
		val xx = (ar1 zip ar2) map (t => t._1 * t._2)
		xx reduceLeft (_ + _) toString
	}
}
