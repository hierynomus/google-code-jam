package nl.javadude.scalajam.gcj08.round1a
import nl.javadude.scalajam.utils.GoogleCodeHelper._

object Milkshakes extends CodeJam {
	def solveProblem(reader : Iterator[String]) : String = {
		val flavours : Array[Boolean] = new Array[Boolean](reader nextInt)
		val cs = (for (i <- 1 to reader.nextInt; c = reader.nextIntArray.toList.tail) yield
		(for (j <- 0 until c.size if j % 2 == 0) yield (c(j), c(j+1))).foldLeft(Map[Int, Boolean]()) { (m, t) => m(t._1) = t._2 != 0}).toList

		var recheck = true
		var impossible = false
		while (recheck && !impossible) {
			recheck = false
			val ucs = (for (c <- cs if (!isSatisfied(flavours, c))) yield c).toList
			if (ucs.size != 0) {
				recheck = true
				if ((for (c <- ucs if (!satisfy(flavours, c))) yield c).toList.size != 0) {
					impossible = true
				}
			}
		}

		if (impossible) "IMPOSSIBLE"
		else flavours.map(if (_) "1" else "0").reduceLeft(_ + " " + _)
	}
 
	def isSatisfied (flavours : Array[Boolean], customer : Map[Int, Boolean]) : Boolean = {
		(for ((key, value) <- customer) yield flavours(key - 1) == value) exists (s => s)
	}
 
	def satisfy (flavours : Array[Boolean], customer : Map[Int, Boolean]) : Boolean = {
		val k = (for ((key, value) <- customer if value) yield key).toList
		if (k.size == 0) {
		  false
		} else {
		  flavours(k.head - 1) = true
		  true
		}
	}
}