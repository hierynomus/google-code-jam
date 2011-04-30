package nl.javadude.scala.gcj08.round1c

import nl.javadude.scala.CodeJamTest
import org.junit.Test

class Round1cTest extends CodeJamTest {

  @Test
	def textMessagingOutrage() {
		TextMessagingOutrage.main(Array("src/test/resources/gcj08/round1c/A-small-practice.in"))
		check("src/test/resources/gcj08/round1a/A-small-practice.in")
		TextMessagingOutrage.main(Array("src/test/resources/gcj08/round1c/A-large-practice.in"))
		check("src/test/resources/gcj08/round1a/A-large-practice.in")
	}
 
	def testIncreasingSpeedLimits() {
//		IncreasingSpeedLimits.main(Array("src/test/resources/gcj08/round1c/C-test.in"))
//		check("src/test/resources/gcj08/round1c/C-test.in")
	}
}
