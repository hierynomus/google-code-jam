package nl.javadude.scalajam.gcj09.qualify

class QualifyTest extends CodeJamTest {
	def testAlienLanguage() {
		AlienLanguage.main(Array("src/test/resources/gcj09/qualify/A-test.in"))
		check("src/test/resources/gcj09/qualify/A-test.in")
		AlienLanguage.main(Array("src/test/resources/gcj09/qualify/A-small.in"))
		check("src/test/resources/gcj09/qualify/A-small.in")
		AlienLanguage.main(Array("src/test/resources/gcj09/qualify/A-large.in"))
		check("src/test/resources/gcj09/qualify/A-large.in")
	}
 
	def testWatersheds() {
		Watersheds.main(Array("src/test/resources/gcj09/qualify/B-test.in"))
		check("src/test/resources/gcj09/qualify/B-test.in")
		Watersheds.main(Array("src/test/resources/gcj09/qualify/B-small.in"))
		check("src/test/resources/gcj09/qualify/B-small.in")
		Watersheds.main(Array("src/test/resources/gcj09/qualify/B-large.in"))
		check("src/test/resources/gcj09/qualify/B-large.in")
	}

	def testWelcomeToCodeJam() {
		WelcomeToCodeJam.main(Array("src/test/resources/gcj09/qualify/C-test.in"))
		check("src/test/resources/gcj09/qualify/C-test.in")
		WelcomeToCodeJam.main(Array("src/test/resources/gcj09/qualify/C-small.in"))
		check("src/test/resources/gcj09/qualify/C-small.in")
//		WelcomeToCodeJam.main(Array("src/test/resources/gcj09/qualify/C-large.in"))
//		check("src/test/resources/gcj09/qualify/C-large.in")
	}
}
