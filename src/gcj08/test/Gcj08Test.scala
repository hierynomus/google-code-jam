import collection.immutable.List
import io.Source
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import qualify._
import round1a.{Milkshakes, MinimumScalarProduct}
import round1c.RopeIntranet

@RunWith(classOf[JUnitRunner])
class Gcj08Test extends Specification {

  val resourceDir = "src/gcj08/test-resources/"

  "GCJ08" should {
    createTestCases(SavingTheUniverse)
    createTestCases(TrainTimetable)
    createTestCases(MinimumScalarProduct)
    createTestCases(Milkshakes)
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
        (s, t) => s + format("expected [%s]; got [%s]", t._1, t._2)
      })
    outputLines must equalTo(expectedLines)
  }

}
