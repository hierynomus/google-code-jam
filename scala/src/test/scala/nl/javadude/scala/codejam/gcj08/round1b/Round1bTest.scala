package nl.javadude.scala.gcj08.round1b

import nl.javadude.scala.CodeJamTest
import org.junit.Test

class Round1bTest extends CodeJamTest {

  @Test
	def cropTriangles() {
		CropTriangles.main(Array("src/test/resources/gcj08/round1b/A-small-practice.in"))
		check("src/test/resources/gcj08/round1b/A-small-practice.in")
		CropTriangles.main(Array("src/test/resources/gcj08/round1b/A-large-practice.in"))
		check("src/test/resources/gcj08/round1b/A-large-practice.in")
	}

  @Test
	def numberSets() {
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
