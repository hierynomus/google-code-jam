package nl.javadude.codejam.gcj09.qualify;
import nl.javadude.scala.utils.GoogleCodeHelper._
import nl.javadude.scala.CodeJam

/**
 * Welcome to Code Jam
 * 
 * Currently too slow for large, time expired :-(
 * @author Jeroen van Erp - ajvanerp at gmail.com
 */
object WelcomeToCodeJam extends CodeJam {
	def solveProblem(reader : Iterator[String]) : String = {
		val expr = "welcome to code jam"
		val line = reader.trimmedLine

		// first filter all characters not occurring in pattern
		val filteredLine = line.toList.filter(x => expr.contains(x))
		val exprList = expr.toList

    val lookup = scala.collection.mutable.Map[(Int, Int), Int]()

    def solve(a: Int, b: Int) : Int = {
      val pair = (a, b)
      if (lookup.contains(pair)) lookup(pair) else {
        if (b == expr.length) 1 else if (a == line.length) 0 else {
          val c = ((if (line(a) == expr(b)) solve(a + 1, b + 1) else 0) + solve(a + 1, b)) % 10000
          lookup.put(pair, c)
          c
        }
      }
    }

		(solve(0, 0) % 10000) formatted ("%04d")
	}

}
