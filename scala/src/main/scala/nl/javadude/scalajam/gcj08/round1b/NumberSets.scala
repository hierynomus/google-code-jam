package nl.javadude.scalajam.gcj08.round1b
import scala.collection.mutable
import scala.Math._

/**
 * @author Jeroen van Erp - jeroen at hierynomus.com
 */
object NumberSets extends CodeJam {
	def solveProblem (reader : Iterator[String]) : String = {
		import utils.GoogleCodeHelper._
		val ar = reader.nextIntArray
		val (a, b, p) = (ar(0), ar(1), ar(2))
		var primes = 2.to((b - a) + 1).toList
		while(!primes.isEmpty && primes.head < p) {
			primes = sieve(primes)
		}

		var ds = new DisjointSet()
		val list = for (x <- (a to b)) yield ds.make(x)
  
		while (!primes.isEmpty) {
			val group = list.filter(x => x.value % primes.head == 0).toList
			group.tail.foreach(n => ds.union(group.head, n))
			println(ds.disjoint)
//			val group = ints.filter(x => x % primes.head == 0).foldLeft(mutable.Set[Int]()) { (s, e) => s + e}
//			ints = ints.filter(x => !group.contains(x))
//			groups += group
			primes = sieve(primes)
		}
//		list.filter(x => x.parent == x).size.toString
		ds.count.toString
//		(groups.size + ints.size).toString
	}
 
	def sieve (primes : List[Int]) = (primes filter (x => (x % primes.head) != 0))
}

class DisjointSet {
	val disjoint : mutable.Map[Int, Node] = mutable.Map[Int, Node]()

	def make(i : Int) : Node = {
	  	val n = new Node(i)
	  	n.parent = n
		disjoint += (i -> n)
		n
	}

	def find(i : Int) : Node = {
		val x = disjoint get i
		x match {
		  case Some(x) => findRoot(x)
		  case None => null
		}
	}

 	def union(i : Int, j : Int) {
		val iRoot = find(i)
		val jRoot = find(j)
		union(iRoot, jRoot)
 	}

  	def union(x : Node, y : Node) {
  		val iRoot = findRoot(x)
  		val jRoot = findRoot(y)
		if (iRoot.rank > jRoot.rank)
			jRoot.parent = iRoot
		else if (iRoot.rank < jRoot.rank)
			iRoot.parent = jRoot
		else if (iRoot != jRoot) {
			jRoot.parent = iRoot
			iRoot.rank += 1
		}
  	}
  
	def findRoot(x : Node) : Node = {
		x.parent.value match {
			case x.value => x
			case _ => x.parent = findRoot(x); x.parent
		}
	}
 
	def count : Int = {
		val count = for ((k, v) <- disjoint if v.parent == v) yield v
		count.toList.size
	}
}

class Node(val value : Int) {
 
	var parent : Node = null
	var rank = 0
 
	override def toString : String = { "Node[" + value + ", " + (if (parent == this) "self" else parent) + "]"}
}