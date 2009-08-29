package nl.javadude.scalajam.gcj08.round1c

import org.scalatest._
import org.scalatest.junit.JUnit3Suite

class Round1cTest extends CodeJamTest {

	def testTextMessagingOutrage() {
		TextMessagingOutrage.main(Array("src/test/resources/gcj08/round1c/A-small-practice.in"))
		check("src/test/resources/gcj08/round1a/A-small-practice.in")
		TextMessagingOutrage.main(Array("src/test/resources/gcj08/round1c/A-large-practice.in"))
		check("src/test/resources/gcj08/round1a/A-large-practice.in")
	}
}
