package nl.javadude.scalajam.gcj10.round1b
import nl.javadude.scalajam.CodeJamTest
import org.junit.Test

class Round1bTest extends CodeJamTest {
  @Test
	def fileFixIt() {
		FileFixIt.main(Array("src/test/resources/gcj10/round1b/A-test.in"))
		check("src/test/resources/gcj10/round1b/A-test.in")
		FileFixIt.main(Array("src/test/resources/gcj10/round1b/A-small-practice.in"))
		check("src/test/resources/gcj10/round1b/A-small-practice.in")
		FileFixIt.main(Array("src/test/resources/gcj10/round1b/A-large-practice.in"))
		check("src/test/resources/gcj10/round1b/A-large-practice.in")
	}
}
