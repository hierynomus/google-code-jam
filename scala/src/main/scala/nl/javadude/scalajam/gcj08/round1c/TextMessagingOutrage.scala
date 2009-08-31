package nl.javadude.scalajam.gcj08.round1c

import nl.javadude.scalajam.utils.GoogleCodeHelper._

/**
 * VERIFIED against Google Code Jam
 * @author Jeroen van Erp - jeroen at hierynomus.com
 */
object TextMessagingOutrage extends CodeJam {
	def solveProblem(reader:Iterator[String]) : String = {
		val keys = reader.nextIntArray(1)
		val x = reader.nextBigDecimalArray.toList.sort(_>_).zipWithIndex
		x.map(t => t._1 * ((t._2 / keys) + 1)).reduceLeft(_+_).toString
	}
}
