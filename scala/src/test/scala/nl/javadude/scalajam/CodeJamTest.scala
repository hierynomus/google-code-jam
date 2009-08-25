package nl.javadude.scalajam

import org.scalatest._
import org.scalatest.junit.JUnit3Suite
import gcj08.qualify._

class CodeJamTest extends JUnit3Suite {

	def testSavingTheUniverse() {
		SavingTheUniverse.main(Array("src/test/resources/gcj08/qualify/A-small-practice.in"))
		SavingTheUniverse.main(Array("src/test/resources/gcj08/qualify/A-large-practice.in"))
	}
 
}
