package nl.javadude.scala.gcj10.round1c

import nl.javadude.scala.CodeJamTest
import org.junit.Test

class Round1cTest extends CodeJamTest {
  @Test
	def ropeIntranet() {
		RopeIntranet.main(Array("src/test/resources/gcj10/round1c/A-test.in"))
		check("src/test/resources/gcj10/round1c/A-test.in")
		RopeIntranet.main(Array("src/test/resources/gcj10/round1c/A-small-practice.in"))
		check("src/test/resources/gcj10/round1c/A-small-practice.in")
		RopeIntranet.main(Array("src/test/resources/gcj10/round1c/A-large-practice.in"))
		check("src/test/resources/gcj10/round1c/A-large-practice.in")
	}
}
