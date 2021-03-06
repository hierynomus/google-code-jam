package nl.javadude.codejam.gcj08.round1b
import scala.collection.mutable
import nl.javadude.scala.CodeJam

/**
 * @author Jeroen van Erp - jeroen at hierynomus.com
 */
object NumberSets extends CodeJam {
	def solveProblem (reader : Iterator[String]) : String = {
		import nl.javadude.scala.utils.GoogleCodeHelper._
		val ar = reader.nextLongArray
		val (a, b, p) = (ar(0), ar(1), ar(2))
		val prob = "(" + a + ", " + b + ", " + p + ")"
		val start = System.currentTimeMillis
		val width = (b-a).toInt
		var primes = 2.to(width + 1).toList

		while(!primes.isEmpty && primes.head < p) {
			primes = sieve(primes)
		}
		val ds = new DisjointSet[Long](a, width)
		var c = 0
		while (c <= ds.size) {
			ds.make(c)
			c += 1
		}

		val r = solve(ds, primes).count.toString
		println(prob + " " + (System.currentTimeMillis - start) + " ms")
		r
	}

	def solve(ds : DisjointSet[Long], primes : List[Int]) : DisjointSet[Long] = {
		primes.size match {
		  case 0 => ds
		  case _ => solve(merge(ds, primes.head), sieve(primes))
		}
	}

	def merge(ds : DisjointSet[Long], prime : Int) : DisjointSet[Long] = {
		val group = ds.elements.filter(x => (ds.base + x) % prime == 0).toList
		group.tail.foreach(n => ds.union(group.head, n))
		ds
	}

	def sieve (primes : List[Int]) = (primes filter (x => (x % primes.head) != 0))
}

class DisjointSet[A](val base : Long, val size : Int) {
	val disjoint : mutable.Map[A, Node[A]] = mutable.Map[A, Node[A]]()

	def make(i : A) {
	  	val n = new Node[A](i)
	  	n.parent = n
		disjoint += (i -> n)
	}

	def find(i : A) : Node[A] = {
		val x = disjoint get i
		x match {
		  case Some(x) => findRoot(x)
		  case None => null
		}
	}

 	def union(i : A, j : A) {
		val iRoot = find(i)
		val jRoot = find(j)
		if (iRoot.rank > jRoot.rank) {
			jRoot.parent = iRoot
			disjoint += (jRoot.value -> jRoot)
		} else if (iRoot.rank < jRoot.rank) {
			iRoot.parent = jRoot
			disjoint += (iRoot.value -> iRoot)
		} else if (iRoot != jRoot) {
			jRoot.parent = iRoot
			iRoot.rank += 1
			disjoint += (jRoot.value -> jRoot)
		}
 	}

	def findRoot(x : Node[A]) : Node[A] = {
		x.parent.value match {
			case x.value => x
			case _ => x.parent = findRoot(x.parent); x.parent
		}
	}
 
	def count : Int = {
		val count = for ((k, v) <- disjoint if v.parent == v) yield v
		count.toList.size
	}
 
	def elements = disjoint.keys
}

class Node[A](val value : A) {
 
	var parent : Node[A] = null
	var rank = 0
 
	override def toString : String = { "Node[" + value + ", " + (if (parent == this) "self" else parent) + "]"}

	override def equals(o : Any) = o match {
	  case x : Node[A] => x.value == value
	  case _ => false
	}
 
}