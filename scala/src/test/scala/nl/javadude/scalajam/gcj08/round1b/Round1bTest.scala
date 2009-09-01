package nl.javadude.scalajam.gcj08.round1b

import org.scalatest._
import org.scalatest.junit.JUnit3Suite

class Round1bTest extends CodeJamTest {

	def testCropTriangles() {
		CropTriangles.main(Array("src/test/resources/gcj08/round1b/A-small-practice.in"))
		check("src/test/resources/gcj08/round1b/A-small-practice.in")
		CropTriangles.main(Array("src/test/resources/gcj08/round1b/A-large-practice.in"))
		check("src/test/resources/gcj08/round1b/A-large-practice.in")
	}

	def testNumberSets() {
		println("NumberSetsAlt")
		NumberSetsAlt.main(Array("src/test/resources/gcj08/round1b/B-small-practice.in"))
		check("src/test/resources/gcj08/round1b/B-small-practice.in")
		println("NumberSets")
		NumberSets.main(Array("src/test/resources/gcj08/round1b/B-small-practice.in"))
		check("src/test/resources/gcj08/round1b/B-small-practice.in")
//		NumberSetsAlt.main(Array("src/test/resources/gcj08/round1b/B-large-practice.in"))
		NumberSetsAlt.main(Array("src/test/resources/gcj08/round1b/B-test.in"))
		check("src/test/resources/gcj08/round1b/B-test.in")
	}
}
