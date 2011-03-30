package nl.javadude.scalajam.gcj08.round1b
import scala.collection.mutable
import scala.Math._
import nl.javadude.scalajam.CodeJam

/**
 * @author Jeroen van Erp - jeroen at hierynomus.com
 */
object NumberSetsAlt extends CodeJam {
	def solveProblem (reader : Iterator[String]) : String = {
		import nl.javadude.scalajam.utils.GoogleCodeHelper._
		val ar = reader.nextLongArray
		val (a, b, p) = (ar(0), ar(1), ar(2))
		val prob = "(" + a + ", " + b + ", " + p + ")"
		val start = System.currentTimeMillis
		val width = (b - a).toInt
		var primes = 2.to(width + 1).toList
		while(!primes.isEmpty && primes.head < p) {
			primes = sieve(primes)
		}

		val r = solve(new AltDisjointSet(width, a), primes).count.toString
  		println(prob + " " + (System.currentTimeMillis - start) + " ms")
		r
	}

	def solve(ds : AltDisjointSet, primes : List[Int]) : AltDisjointSet = {
		primes.size match {
			case 0 => ds
			case _ => solve(merge(ds, primes.head), sieve(primes))
		}
	}
 
	def merge(ds : AltDisjointSet, prime : Int) : AltDisjointSet = {
		val group = (0 to ds.size).filter(x => (ds.base + x) % prime == 0).toList
		group.tail.foreach(n => ds.union(group.head, n))
		ds
	}

	def sieve (primes : List[Int]) = (primes filter (x => (x % primes.head) != 0))
}

class AltDisjointSet(val size : Int, val base : Long) {
	val parents = (0 to size).toArray
	val rank = Array.make(size + 1, 0)

	def find(i : Int) : Int = {
		val x = parents(i)
		x match {
			case `i` => x
			case z @ _ => parents(x) = find(z); parents(x)
		}
	}

 	def union(i : Int, j : Int) {
		val iRoot = find(i)
		val jRoot = find(j)
  
		val iRank = rank(iRoot)
		val jRank = rank(jRoot)
		if (iRank > jRank) {
			parents(jRoot) = iRoot
		} else if (iRank < jRank) {
			parents(iRoot) = jRoot
		} else if (iRoot != jRoot) {
			parents(jRoot) = iRoot
			rank(iRoot) += 1
		}
 	}
 
	def count : Int = {
		parents.zipWithIndex.filter(t => t._1 == t._2).size
	}
 
}
