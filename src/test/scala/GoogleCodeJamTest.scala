import acj10.qualify.{T9Spelling, ReverseWords, StoreCredit}
import collection.immutable.List
import gcj08.qualify.{TrainTimetable, SavingTheUniverse}
import gcj08.round1a.{Milkshakes, MinimumScalarProduct}
import gcj09.qualify.{Watersheds, AlienLanguage}
import gcj10.qualify.{FairWarning, Snapper}
import gcj10.round1b.FileFixit
import gcj10.round1c.RopeIntranet
import gcj11.qualify.{BotTrust, GoroSort, CandySplitting, Magicka}
import gcj12.qualify.{RecycledNumbers, DancingWithTheGooglers, SpeakingInTongues}
import gcj12.round1a.{KingdomRush, PasswordProblem}
import gcj12.round1c.{BoxFactory, DiamondInheritance}
import io.Source
import org.specs2.mutable.Specification

class GoogleCodeJamTest extends Specification {

  "GCJ08" should {
    createTestCases(SavingTheUniverse)
    createTestCases(TrainTimetable)
    createTestCases(MinimumScalarProduct)
    createTestCases(Milkshakes)
  }

  "GCJ09" should {
    createTestCases(AlienLanguage)
    createTestCases(Watersheds)
  }

  "Africa Code Jam 2010" should {
    createTestCases(StoreCredit)
    createTestCases(ReverseWords)
    createTestCases(T9Spelling)
  }

  "GCJ10" should {
    createTestCases(Snapper)
    createTestCases(FairWarning)
    createTestCases(FileFixit)
    createTestCases(RopeIntranet)
  }

  "GCJ11" should {
    createTestCases(BotTrust)
    createTestCases(Magicka)
    createTestCases(CandySplitting)
    createTestCases(GoroSort)
  }

  "GCJ12" should {
    createTestCase(SpeakingInTongues, "small")
    createTestCases(DancingWithTheGooglers)
    // Works but takes long and needs heap, more optimal solution was not necessary for qualify
//    createTestCases(RecycledNumbers)
    createTestCases(PasswordProblem)
    createTestCases(KingdomRush)
    createTestCases(DiamondInheritance)
    createTestCase(BoxFactory, "small")
  }

  def createTestCase(program: {def main(args: Array[String]); def name: String}, x: String) = {
    val exampleName = program.name.replace('.', ' ')
    new InExample(exampleName + " - " + x).>>(testInput(program, x))
  }

  private def createTestCases(program: { def main(args: Array[String]); def name: String}) = {
    List("small", "large").map { x =>
      createTestCase(program, x)
    }.head
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
