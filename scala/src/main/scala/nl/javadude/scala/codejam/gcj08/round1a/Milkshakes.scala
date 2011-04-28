package nl.javadude.scala.gcj08.round1a
import nl.javadude.scala.utils.GoogleCodeHelper._
import nl.javadude.scala.CodeJam

/**
 * VERIFIED against Google Code Jam
 * @author Jeroen van Erp - jeroen at hierynomus.com
 */
object Milkshakes extends CodeJam {
	def solveProblem(reader : Iterator[String]) : String = {
		val flavours : Array[Boolean] = new Array[Boolean](reader nextInt)
		val cs = (for (i <- 1 to reader.nextInt; c = reader.nextIntArray) yield
              (1 until c.size).filter(_ % 2 == 1).map(j => (c(j), c(j+1))).foldLeft(Map[Int, Boolean]()) { (m, t) => m(t._1) = t._2 != 0 }).toList

        solve(flavours, cs, unSatisfiedCustomers(flavours, cs)) match {
        		case None => "IMPOSSIBLE"
        		case Some(x) => x.map(if (_) "1" else "0").reduceLeft(_ + " " + _)
		}
	}
 
	def isSatisfied (flavours : Array[Boolean], customer : Map[Int, Boolean]) : Boolean = {
		(for ((key, value) <- customer) yield flavours(key - 1) == value) exists (s => s)
	}

	def unSatisfiedCustomers(flavours : Array[Boolean], customers : List[Map[Int, Boolean]]) : List[Map[Int, Boolean]] = {
		customers filter (!isSatisfied(flavours, _)) 
	}

	def solve(flavours : Array[Boolean], customers : List[Map[Int, Boolean]], unsatisfied : List[Map[Int, Boolean]]) : Option[Array[Boolean]] = {
		unsatisfied.size match {
		  case 0 => Some(flavours)
		  case _ => satisfyCustomer(flavours, unsatisfied.head).flatMap { solve(_, customers, unSatisfiedCustomers(flavours, customers)) }
		}
	}

	def satisfyCustomer(flavours : Array[Boolean], customer : Map[Int, Boolean]) : Option[Array[Boolean]] = {
		val k = (for ((key, value) <- customer if value) yield key).toList
		if (k.size != 0) {
			flavours(k.head - 1) = true
			Some(flavours)
		} else {
			None
		}
		
	}
 }
