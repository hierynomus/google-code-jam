package nl.javadude.scala.gcj08.round1a

import nl.javadude.scala.CodeJamTest
import org.junit.Test

class Round1aTest extends CodeJamTest {

  @Test
	def minimumScalarProduct() {
		MinimumScalarProduct.main(Array("src/test/resources/gcj08/round1a/A-small-practice.in"))
		check("src/test/resources/gcj08/round1a/A-small-practice.in")
		MinimumScalarProduct.main(Array("src/test/resources/gcj08/round1a/A-large-practice.in"))
		check("src/test/resources/gcj08/round1a/A-large-practice.in")
	}

  @Test
	def milkshakes() {
		Milkshakes.main(Array("src/test/resources/gcj08/round1a/B-small-practice.in"))
		check("src/test/resources/gcj08/round1a/B-small-practice.in")
		Milkshakes.main(Array("src/test/resources/gcj08/round1a/B-large-practice.in"))
		check("src/test/resources/gcj08/round1a/B-large-practice.in")
	}
}
