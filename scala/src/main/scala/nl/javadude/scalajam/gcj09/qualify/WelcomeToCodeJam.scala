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
		val filteredLine = makeHisto(line.toList.filter(x => expr.contains(x)))
		val exprList = expr.toList
		(solve(filteredLine, exprList) % 10000) formatted ("%04d")
	}

	def makeHisto(line : List[Char]) : List[(Char, Long)] = {
		if (line.isEmpty) {
			List[(Char, Long)]()
		} else {
			val h = histoLetter(line.tail, line.head)
			val newLine = line.drop(h._2.toInt)
			newLine.isEmpty match {
			  case true => List(h)
			  case false => List(h) ++ makeHisto(newLine)
			}
		}
	}

	def histoLetter(line : List[Char], c : Char) : (Char, Long) = {
		(c, (line takeWhile (_ == c)).size + 1)
	}

	def solve(line : List[(Char, Long)], expr : List[Char]) : Long = {
		var count : Long = 0L
		if (!expr.isEmpty) {
			var restLine = findNext(line, expr)
			while (!restLine.isEmpty) {
				count += (restLine.head._2 * solve(restLine.tail, expr.tail))
				restLine = findNext(restLine.tail, expr) 
			}
		} else {
		  count = 1
		}
		count
	}

	def findNext(line : List[(Char, Long)], expr : List[Char]) = {
		line.dropWhile(_._1 != expr.head)
	}

// 	def solve(line : List[Char], expr : List[Char]) : Long = {
//		line.isEmpty match {
//		  case true => 0
//		  case false => {
//			  val t = line.head
//			  expr.head match {
//			    case `t` => expr.tail.isEmpty match {
//			      case true => 1 + solve(line.tail.dropWhile(_ != expr.head), expr)
//			      case false => solve(line.tail.dropWhile(_ != expr.tail.head), expr.tail) + solve(line.tail.dropWhile(_ != expr.head), expr)
//			    }
//			    case _ => solve(line.tail.dropWhile(_ != expr.head), expr)
//			  }
//		  }
//		}
//	}

}
