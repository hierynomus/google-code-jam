package nl.javadude.scalajam.gcj08.round1b
import nl.javadude.scalajam.utils.GoogleCodeHelper._

/**
 * @author Jeroen van Erp - jeroen at hierynomus.com
 */
object CropTriangles extends CodeJam {
	def solveProblem(reader:Iterator[String]) : String = {
		val ar = reader.nextLongArray
		val (n, a, b, c, d, x0, y0, m) = (ar(0), ar(1), ar(2), ar(3), ar(4), ar(5), ar(6), ar(7))
		var x = x0
		var y = y0
		var trees : Array[Long] = Array.make(9, 0L)
		var i = 0
		while (i < n) {
			trees((((x % 3) * 3) + (y % 3)).toInt) += 1
			x = (a * x + b) % m
			y = (c * y + d) % m
			i += 1
		}
		val treesInSameClass = 0 until 9 map (x => trees(x) * (trees(x)-1) * (trees(x)-2) / 6) // N! / 3!

		val triples = for (val t1 <- 0 until 9; val t2 <- (t1 + 1) until 9; val t3 <- (t2 + 1) until 9) yield (t1, t2, t3)
		val filtered = triples.filter (t => (t._1/3 + t._2/3 + t._3/3) % 3 == 0 && (t._1%3 + t._2%3 + t._3%3) % 3 == 0)
		val nrTrees = filtered.map(t => trees(t._1) * trees(t._2) * trees(t._3))

		nrTrees.concat(treesInSameClass).reduceLeft(_+_).toString
	}
}

