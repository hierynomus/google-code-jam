package nl.javadude.scalajam.gcj08.round1a


import org.scalatest._
import org.scalatest.junit.JUnit3Suite

class Round1aTest extends CodeJamTest {

	def testMinimumScalarProduct() {
		MinimumScalarProduct.main(Array("src/test/resources/gcj08/round1a/A-small-practice.in"))
		check("src/test/resources/gcj08/round1a/A-small-practice.in")
		MinimumScalarProduct.main(Array("src/test/resources/gcj08/round1a/A-large-practice.in"))
		check("src/test/resources/gcj08/round1a/A-large-practice.in")
	}

	def testMilkshakes() {
		Milkshakes.main(Array("src/test/resources/gcj08/round1a/B-small-practice.in"))
		check("src/test/resources/gcj08/round1a/B-small-practice.in")
		Milkshakes.main(Array("src/test/resources/gcj08/round1a/B-large-practice.in"))
		check("src/test/resources/gcj08/round1a/B-large-practice.in")
	}
}
