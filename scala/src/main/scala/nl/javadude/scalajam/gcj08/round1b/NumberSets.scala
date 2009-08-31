package nl.javadude.scalajam.gcj08.round1b
import utils.GoogleCodeHelper._
import scala.collection.mutable
import scala.Math._

object NumberSets extends CodeJam {
	def solveProblem (reader : Iterator[String]) : String = {
		val ar = reader.nextIntArray
		var primes = 2.to(ar(1) / 2).toList
		var ints = (ar(0).to(ar(1))).toList
		val p = ar(2)
		while(!primes.isEmpty && primes.head < p) {
			primes = sieve(primes)
		}
  
		var groups = List[mutable.Set[Int]]()
		while (!primes.isEmpty && primes.head <= (ar(1) / 2)) {
			val group = ints.filter(x => x % primes.head == 0).foldLeft(mutable.Set[Int]()) { (s, e) => s + e}
			ints = ints.filter(x => !group.contains(x))
			groups += group
			primes = sieve(primes)
		}
  
		(groups.size + ints.size).toString
	}
 
	def sieve (primes : List[Int]) = (primes filter (x => (x % primes.head) != 0))
}
