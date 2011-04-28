package nl.javadude.scala.gcj08.round1a
import nl.javadude.scala.utils.GoogleCodeHelper._
import nl.javadude.scala.CodeJam

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
