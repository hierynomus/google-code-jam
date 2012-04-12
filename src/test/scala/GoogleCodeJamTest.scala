import acj10.qualify.{T9Spelling, ReverseWords, StoreCredit}
import collection.immutable.List
import gcj08.qualify.{TrainTimetable, SavingTheUniverse}
import gcj08.round1a.{Milkshakes, MinimumScalarProduct}
import gcj09.qualify.{Watersheds, AlienLanguage}
import gcj10.qualify.{FairWarning, Snapper}
import gcj10.round1b.FileFixit
import gcj10.round1c.RopeIntranet
import gcj11.qualify.Magicka
import io.Source
import org.specs2.mutable.Specification

class GoogleCodeJamTest extends Specification {

  "GCJ08" should {
    createTestCase(SavingTheUniverse)
    createTestCase(TrainTimetable)
    createTestCase(MinimumScalarProduct)
    createTestCase(Milkshakes)
  }

  "GCJ09" should {
    createTestCase(AlienLanguage)
    createTestCase(Watersheds)
  }

  "Africa Code Jam 2010" should {
    createTestCase(StoreCredit)
    createTestCase(ReverseWords)
    createTestCase(T9Spelling)
  }

  "GCJ10" should {
    createTestCase(Snapper)
    createTestCase(FairWarning)
    createTestCase(FileFixit)
    createTestCase(RopeIntranet)
  }

  "GCJ11" should {
    createTestCase(Magicka)
  }
  
  private def createTestCase(program: { def main(args: Array[String]); def name: String}) = {
    val exampleName = program.name.replace('.', ' ')
    new InExample(exampleName + " - small").>>(testInput(program, "small"))
    new InExample(exampleName + " - large").>>(testInput(program, "large"))
  }

  private def testInput(program: { def main(args: Array[String]); def name: String}, which: String) = {
    val inputFile = program.name.replace('.', '/') + "-" + which + ".in"
    program.main(Array("src/test/resources/" + inputFile))
    check("src/test/resources/" + inputFile)
  }

  private def check(input: String) = {
    val outputFile = (input substring(0, input lastIndexOf ("."))) + ".out"
    val expectedFile = (input substring(0, input lastIndexOf ("."))) + ".expected"

    val outputLines = Source.fromFile(outputFile).getLines().toList
    val expectedLines = Source.fromFile(expectedFile).getLines().toList
    val lines: List[(String, String)] = outputLines.zip(expectedLines).toList
    val filtered = lines.filterNot(t => t._1 == t._2)
    if (!filtered.isEmpty)
      failure(filtered.foldLeft("Failed: \n") {
        (s, t) => s + format("expected [%s]; got [%s]", t._1, t._2)
      })
    outputLines must equalTo(expectedLines)
  }

}
