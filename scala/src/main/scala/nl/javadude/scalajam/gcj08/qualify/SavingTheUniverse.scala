package nl.javadude.scalajam.gcj08.qualify
import nl.javadude.scalajam.utils.GoogleCodeHelper._
import nl.javadude.scalajam.CodeJam

/**
 * VERIFIED against Google Code Jam
 * @author Jeroen van Erp - jeroen at hierynomus.com
 */
object SavingTheUniverse extends CodeJam {

  override def solveProblem(reader: Iterator[String]) : String = {
	  val nrEngines = reader.nextInt
	  reader skipLines nrEngines
	  var enginesEncountered = Set[String]()
	  var switches = 0
	  for (val s <- 1 to reader.nextInt) {
		  val read = reader.next
		  enginesEncountered += read
		  if (enginesEncountered.size == nrEngines) {
			  enginesEncountered = Set(read)
			  switches += 1
		  }
	  }
	  switches.toString
  }
}
