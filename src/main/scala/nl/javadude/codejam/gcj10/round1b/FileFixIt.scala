package nl.javadude.codejam.gcj10.round1b

import nl.javadude.scala.CodeJam
import nl.javadude.scala.utils.GoogleCodeHelper._

object FileFixIt extends CodeJam{
  def solveProblem(reader: Iterator[String]) = {
    val arr = reader nextIntArray

    var existing = (for (i <- 1 to arr(0); directory = reader.next) yield directory).toList

    var mkdirs = 0

    for (i <- 1 to arr(1)) {
      val toCreate = reader.next
      val split: Array[String] = toCreate.split('/')
      var dir = ""
      for (j <- 1 until split.length) {
        dir += "/" + split(j)
        if (!existing.contains(dir)) {
          mkdirs+=1
          existing = dir :: existing
        }
      }
    }

    mkdirs.toString
  }
}