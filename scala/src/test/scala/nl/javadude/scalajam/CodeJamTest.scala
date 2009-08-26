package nl.javadude.scalajam

import org.scalatest._
import org.scalatest.junit.JUnit3Suite
import gcj08.qualify._
import gcj08.round1a._

class CodeJamTest extends JUnit3Suite {

	def notestSavingTheUniverse() {
		SavingTheUniverse.main(Array("src/test/resources/gcj08/qualify/A-small-practice.in"))
		SavingTheUniverse.main(Array("src/test/resources/gcj08/qualify/A-large-practice.in"))
	}
 
	def notestTrainTimetable() {
		TrainTimetable.main(Array("src/test/resources/gcj08/qualify/B-small-practice.in"))
	}
 
	def testMinimumScalarProduct() {
		MinimumScalarProduct.main(Array("src/test/resources/gcj08/round1a/A-small-practice.in"))
		MinimumScalarProduct.main(Array("src/test/resources/gcj08/round1a/A-large-practice.in"))
	}

	def testMilkshakes() {
		Milkshakes.main(Array("src/test/resources/gcj08/round1a/B-small-practice.in"))
		Milkshakes.main(Array("src/test/resources/gcj08/round1a/B-large-practice.in"))
	}
}
