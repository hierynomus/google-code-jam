package nl.javadude.scala.africajam.acj10.qualify

import nl.javadude.scala.CodeJam
import nl.javadude.scala.utils.GoogleCodeHelper._

object T9Spelling extends CodeJam {

  def rainbowTable = Map(
    'a' -> "2",
    'b' -> "22",
    'c' -> "222",
    'd' -> "3",
    'e' -> "33",
    'f' -> "333",
    'g' -> "4",
    'h' -> "44",
    'i' -> "444",
    'j' -> "5",
    'k' -> "55",
    'l' -> "555",
    'm' -> "6",
    'n' -> "66",
    'o' -> "666",
    'p' -> "7",
    'q' -> "77",
    'r' -> "777",
    's' -> "7777",
    't' -> "8",
    'u' -> "88",
    'v' -> "888",
    'w' -> "9",
    'x' -> "99",
    'y' -> "999",
    'z' -> "9999",
    ' ' -> "0"
  )

  def solveProblem(reader: Iterator[String]) = {
    reader.nextCharArray.map(rainbowTable(_)).foldLeft("")((a: String, b: String) => a.endsWith(b.charAt(0).toString) match {
      case true => a + " " + b
      case false => a + b
    })
  }
}