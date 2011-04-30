package nl.javadude.scala

import scala.io._
import java.io._
import utils.FileHelper._
import utils.GoogleCodeHelper._

/**
 * Trait for the Google Code Jam problems.
 * Reads from input file and collects all output from all problems before writing that to a file.
 * @author Jeroen van Erp - jeroen at hierynomus.com
 */
trait CodeJam {

	def main(args: Array[String]) = {
		if (args.length == 0) {
			throw new IllegalArgumentException("Could not find input file in arguments")
		}
  
		val input = args(0)
		val outputFile = new File((input substring(0, input lastIndexOf("."))) + ".out")
		val reader = Source fromFile(input) getLines
		val nrProblems = reader nextInt
		val results = new Array[String](nrProblems) 
		for (val i <- 1 to nrProblems) {
			results(i - 1) = "Case #" + i + ": " + solveProblem(reader)
		}
		outputFile write results
	}
 
	def solveProblem(reader : Iterator[String]) : String
}
