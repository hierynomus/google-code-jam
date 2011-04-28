package nl.javadude.scala.africajam.acj10.qualify

import nl.javadude.scala.CodeJamTest
import org.junit.Test


class QualifyTest extends CodeJamTest {

  @Test
  def reverseWords() {
    ReverseWords.main(Array("src/test/resources/acj10/qualify/B-test.in"))
    check("src/test/resources/acj10/qualify/B-test.in")
    ReverseWords.main(Array("src/test/resources/acj10/qualify/B-small-practice.in"))
    check("src/test/resources/acj10/qualify/B-small-practice.in")
    ReverseWords.main(Array("src/test/resources/acj10/qualify/B-large-practice.in"))
    check("src/test/resources/acj10/qualify/B-large-practice.in")
  }
}