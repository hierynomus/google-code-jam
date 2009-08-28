package nl.javadude.scalajam.gcj08.round1b
import nl.javadude.scalajam.utils.GoogleCodeHelper._

object CropTriangles extends CodeJam {
	def solveProblem(reader:Iterator[String]) : String = {
		val ar = reader.nextLongArray
		var x = ar(5)
		var y = ar(6)
		var trees = Array.make(10, 0L)
		var i = 0
		while (i < ar(0)) {
			trees(((x%3)*3).toInt + (y%3).toInt) += 1
			x = (ar(1) * x + ar(2)) % ar(7)
			y = (ar(3) * y + ar(4)) % ar(7)
			i += 1
		}
  
		((for (val t1 <- 0 to 9; val t2 <- 0 to 9 if t2 > t1; val t3 <- 0 to 9 if t3 > t2)
			yield (t1, t2, t3))
			filter (t => (t._1/3 + t._2/3 + t._3/3) % 3 == 0 && (t._1%3 + t._2%3 + t._3%3) % 3 == 0)).
			map(t => trees(t._1.toInt) + trees(t._2.toInt) + trees(t._3.toInt)).reduceLeft(_+_).toString
	}
}
