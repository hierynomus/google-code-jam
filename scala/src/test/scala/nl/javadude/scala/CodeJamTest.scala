package nl.javadude.scala

import java.io._
import _root_.scala.io._

trait CodeJamTest {

	def check(input : String) : Boolean = {
		val outputFile = new File((input substring(0, input lastIndexOf("."))) + ".out")
		val expectedFile = new File((input substring(0, input lastIndexOf("."))) + ".expected")
  
		val outputLines = Source.fromFile(outputFile).getLines
		val expectedLines = Source.fromFile(expectedFile).getLines
		if ((outputLines.toList, expectedLines.toList).zipped.forall (_.trim == _.trim)) {
			true
		} else {
			throw new RuntimeException()
		}
	}
}
