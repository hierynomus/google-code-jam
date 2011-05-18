package nl.javadude.codejam.gcj11.qualify

import _root_.scala.collection.mutable._
import nl.javadude.scala.utils.GoogleCodeHelper._
import scala.io._
import java.io._
import nl.javadude.scala.CodeJam

object BotTrust extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    val array = reader.nextStringArray
    val buttonPresses = (for (i <- 1.until(array.length, 2)) yield (array(i), array(i+1).toInt)).toList

    val map: Map[String, List[Int]] = Map[String, List[Int]]()
    map.put("O", List[Int]())
    map.put("B", List[Int]())

    buttonPresses.foreach((x : (String, Int)) => {map.put(x._1, map.get(x._1).get ::: List(x._2))})
    println(map)

    def solve(iO : Int, iB : Int, posO : Int, posB : Int, buttonPresses : List[(String, Int)]) : Int = {
      if (buttonPresses.isEmpty) 0 else {
        val objectiveO: Int = if (iO == map("O").length) posO else map("O")(iO)
        val objectiveB: Int = if (iB == map("B").length) posB else map("B")(iB)

        val travelDistanceO = math.abs(objectiveO - posO)
        val travelDistanceB = math.abs(objectiveB - posB)

        val buttonPress = buttonPresses.head

        if (buttonPress._1 == "O") {
          val travelledB = math.min(travelDistanceO + 1, travelDistanceB)
          val nPosB = if (posB < objectiveB) posB + travelledB else posB - travelledB
          travelDistanceO + 1 + solve(iO + 1, iB, objectiveO, nPosB, buttonPresses.tail)
        } else {
          val travelledO = math.min(travelDistanceO, travelDistanceB + 1)
          val nPosO = if (posO < objectiveO) posO + travelledO else posO - travelledO
          travelDistanceB + 1 + solve(iO, iB + 1, nPosO, objectiveB, buttonPresses.tail)
        }
      }
    }

    solve(0, 0, 1, 1, buttonPresses).toString
  }
}
