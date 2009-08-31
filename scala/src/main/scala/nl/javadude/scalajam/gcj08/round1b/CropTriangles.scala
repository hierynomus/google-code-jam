package nl.javadude.scalajam.gcj08.round1b
import nl.javadude.scalajam.utils.GoogleCodeHelper._

object CropTriangles extends CodeJam {
	def solveProblem(reader:Iterator[String]) : String = {
		val ar = reader.nextLongArray
		var x = ar(5)
		var y = ar(6)
		var trees = Array.make(9, 0L)
		var i = 0
		while (i < ar(0)) {
			trees(((x%3)*3).toInt + (y%3).toInt) += 1
			x = (ar(1) * x + ar(2)) % ar(7)
			y = (ar(3) * y + ar(4)) % ar(7)
			i += 1
		}
  
		val l = for (val t1 <- 0 until 9; val t2 <- t1+1 until 9; val t3 <- t2+1 until 9) yield (t1, t2, t3)
		val a = l.filter (t => (t._1/3 + t._2/3 + t._3/3) % 3 == 0 && (t._1%3 + t._2%3 + t._3%3) % 3 == 0)
		val b = a.map(t => trees(t._1.toInt) * trees(t._2.toInt) * trees(t._3.toInt))
		val c = b.reduceLeft(_+_).toString
		c
	}
}

//object CropTriangles extends CodeJam {
//	def solveProblem(reader:Iterator[String]) : String = {
//		val line = reader.nextLongArray
//		val tree1 = (1L, line(5), line(6))
//  
//		var triangles = 0
//		var loop1 = tree1
//		while (loop1._1 < line(0)) {
//			var loop2 = next(loop1, line)
//			while (loop2._1 < line(0)) {
//				var loop3 = next(loop2, line)
//				while (loop3._1 < line(0)) {
//					if ((loop1._2 + loop2._2 + loop3._2) % 3 == 0 && (loop1._3 + loop2._3 + loop3._3) % 3 == 0) {
//						triangles += 1
//					}
//					loop3 = next(loop3, line)
//				}
//			}
//		}
//		
//		""
//	}
//
//	def generateTree(tree: Tuple3[Long, Long, Long], t : Long, line : Array[Long]) : Tuple3[Long, Long, Long] = {
//	  var i = tree._1
//	  var tr = tree
//	  while (i <= t) {
//		  tr = next(tr, line)
//	  }
//	  tr
//	}
//
//	def next(tree: Tuple3[Long, Long, Long], line : Array[Long]) : Tuple3[Long, Long, Long] = {
//		(tree._1 + 1, (line(1) * tree._2 + line(2)) % line(7), (line(3) * tree._3 + line(4)) % line(7))
//	}
//}