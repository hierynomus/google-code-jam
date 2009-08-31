package nl.javadude.scalajam

import org.scalatest._
import org.scalatest.junit.JUnit3Suite
import utils.FileHelper._
import utils.GoogleCodeHelper._
import java.io._
import _root_.scala.io._

abstract class CodeJamTest extends JUnit3Suite {

	def check(input : String) : Boolean = {
		val outputFile = new File((input substring(0, input lastIndexOf("."))) + ".out")
		val expectedFile = new File((input substring(0, input lastIndexOf("."))) + ".expected")
  
		val outputLines = Source.fromFile(outputFile).getLines
		val expectedLines = Source.fromFile(expectedFile).getLines
		if (List.forall2(outputLines.toList, expectedLines.toList) (_.trim == _.trim)) {
			true
		} else {
			fail()
		}
	}
}
