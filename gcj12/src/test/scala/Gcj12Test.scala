import collection.immutable.List
import io.Source
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import qualify._
import round1a.{KingdomRush, PasswordProblem}
import round1c.{BoxFactory, DiamondInheritance}

@RunWith(classOf[JUnitRunner])
class Gcj12Test extends Specification {
  val resourceDir = "src/test/resources/"

  "GCJ12" should {
    createTestCase(SpeakingInTongues, "small")
    createTestCases(DancingWithTheGooglers)
    // Works but takes long and needs heap, more optimal solution was not necessary for qualify
//    createTestCases(RecycledNumbers)
    createTestCases(PasswordProblem)
    createTestCases(KingdomRush)
    createTestCases(DiamondInheritance)
//    createTestCase(BoxFactory, "small")
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
    program.main(Array(resourceDir + inputFile))
    check(resourceDir + inputFile)
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
        (s, t) => "%s\texpected [%s]; got [%s]\n".format(s, t._2, t._1)
      })
    outputLines must equalTo(expectedLines)
  }

}
