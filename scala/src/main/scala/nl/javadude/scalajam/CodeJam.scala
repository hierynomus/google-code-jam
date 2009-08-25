package nl.javadude.scalajam

import scala.io._
import java.io._
import utils.FileHelper._
import _root_.scala.collection.mutable._
import utils.GoogleCodeHelper._

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
