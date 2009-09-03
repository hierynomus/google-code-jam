package nl.javadude.scalajam.gcj09.qualify

import scala.io._
import java.io._
import utils.FileHelper._
import utils.GoogleCodeHelper._

/**
 * Alien Language. Not extending from CodeJam because first line does not contain just nrProblems.
 * 
 * @athor Jeroen van Erp - jeroen at hierynomus.com
 */
object AlienLanguage {
  
	def main(args: Array[String]) = {
		if (args.length == 0) {
			throw new IllegalArgumentException("Could not find input file in arguments")
		}
  
		val input = args(0)
		val outputFile = new File((input substring(0, input lastIndexOf("."))) + ".out")
		val reader = Source fromFile(input) getLines
		val ar = reader nextIntArray
		val (tokens, nrWords, nrProblems) = (ar(0), ar(1), ar(2)) 
		val results = new Array[String](nrProblems)
  
		val words = (1 to nrWords).map(x => reader.trimmedLine).toList
		for (val i <- 1 to nrProblems) {
			results(i - 1) = "Case #" + i + ": " + solveProblem(reader, words)
		}
		outputFile write results
	}

	/**
	 * Convert the pattern received from the aliens to a regex and match it against the dictionary
	 */
	def solveProblem(reader : Iterator[String], words : List[String]) : String = {
		val pattern = reader.trimmedLine
		val regex = pattern.replaceAll("\\(", "[").replaceAll("\\)", "]").r
  
		matchWords(regex, words.head, words.tail).toString
	}

	/**
	 * Recursive method to sum up all the matches
     */
	def matchWords(regex : scala.util.matching.Regex, word: String, words : List[String]) : Int = {
		words.isEmpty match {
		  	case true => matchWord(regex, word)
		  	case false => matchWord(regex, word) + matchWords(regex, words.head, words.tail)
		}
	}

	/**
	 * Return 1 for a match, 0 for none
	 */
	def matchWord(regex : scala.util.matching.Regex, word: String) : Int = {
		regex.findFirstIn(word) match {
		  case Some(x) => 1
		  case None => 0
		}
	}
}
