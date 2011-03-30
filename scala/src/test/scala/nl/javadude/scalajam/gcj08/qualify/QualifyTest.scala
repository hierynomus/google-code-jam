package nl.javadude.scalajam.gcj08.qualify

import nl.javadude.scalajam.CodeJamTest
import org.junit.Test

class QualifyTest extends CodeJamTest {

  @Test
	def savingTheUniverse() {
		SavingTheUniverse.main(Array("src/test/resources/gcj08/qualify/A-small-practice.in"))
		check("src/test/resources/gcj08/qualify/A-small-practice.in")
		SavingTheUniverse.main(Array("src/test/resources/gcj08/qualify/A-large-practice.in"))
		check("src/test/resources/gcj08/qualify/A-large-practice.in")
	}
 
  @Test
	def trainTimetable() {
		TrainTimetable.main(Array("src/test/resources/gcj08/qualify/B-small-practice.in"))
		TrainTimetable.main(Array("src/test/resources/gcj08/qualify/B-large-practice.in"))
	}
 
}
