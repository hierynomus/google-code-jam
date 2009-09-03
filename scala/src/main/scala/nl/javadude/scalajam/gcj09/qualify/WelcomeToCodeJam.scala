package nl.javadude.scalajam.gcj09.qualify;
import utils.GoogleCodeHelper._

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
//		val solution = (solve(line, expr.toArray, line.indexOf(expr(0)), 0) % 10000) formatted ("%04d")
		val solution = (solve(filteredLine, expr.toList) % 10000) formatted ("%04d")
		println(solution)
		solution
	}

	def makeHisto(line : List[Char]) : List[(Char, Int)] = {
		if (line.isEmpty) {
			List[(Char, Int)]()
		} else {
			val h = histoLetter(line.tail, line.head)
			val newLine = line.drop(h._2)
			newLine.isEmpty match {
			  case true => List(h)
			  case false => List(h) ++ makeHisto(newLine)
			}
		}
	}

	def histoLetter(line : List[Char], c : Char) : (Char, Int) = {
		(c, (line takeWhile (_ == c)).size + 1)
	}

	def solve(line : List[Char], expr : List[Char]) : Long = {
		line.isEmpty match {
		  case true => 0
		  case false => {
			  val t = line.head
			  expr.head match {
			    case `t` => expr.tail.isEmpty match {
			      case true => 1 + solve(line.tail, expr)
			      case false => solve(line.tail, expr.tail) + solve(line.tail, expr)
			    }
			    case _ => solve(line.tail, expr)
			  }
		  }
		}
	}
}
