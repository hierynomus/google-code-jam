package nl.javadude.scalajam.gcj08.qualify


import org.scalatest._
import org.scalatest.junit.JUnit3Suite

class CodeJamTest extends JUnit3Suite {

	def testSavingTheUniverse() {
		SavingTheUniverse.main(Array("src/test/resources/gcj08/qualify/A-small-practice.in"))
		SavingTheUniverse.main(Array("src/test/resources/gcj08/qualify/A-large-practice.in"))
	}
 
	def testTrainTimetable() {
		TrainTimetable.main(Array("src/test/resources/gcj08/qualify/B-small-practice.in"))
		TrainTimetable.main(Array("src/test/resources/gcj08/qualify/B-large-practice.in"))
	}
 
}
