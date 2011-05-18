package nl.javadude.codejam.gcj11.qualify

import _root_.scala.collection.mutable._
import nl.javadude.scala.utils.GoogleCodeHelper._
import nl.javadude.scala.CodeJam

object Magicka extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    val array = reader.nextStringArray
    println(array.deep)
    val c = array(0).toInt
    val d = array(c + 1).toInt
    val invocation = array(c + d + 3).toCharArray.map(_.toString)

    val combine = Map[String, String]()
    for (i <- 1 to c) {
      val Array(a, b, c) = array(i).toCharArray
      combine.put(a.toString + b, c.toString)
      combine.put(b.toString + a, c.toString)
    }

    var oppose: Array[(String, String)] = Array.fill(d) {
      ("", "")
    }
    for (i <- 1 to d) {
      val Array(a, b) = array(i + c + 1).toCharArray
      oppose(i - 1) = (a.toString, b.toString)
    }

    invocation.reduceLeft((inv: String, x: String) => {
      if (inv.length == 0) x else {
        val pair = inv.last.toString + x
        if (combine.contains(pair)) {
          inv.substring(0, inv.length - 1) + combine(pair)
        } else {
          val newInv = inv + x
          if (oppose.exists((p: (String, String)) => newInv.contains(p._1) && newInv.contains(p._2))) {
            ""
          } else {
            inv + x
          }
        }
      }
    }).toCharArray.deep.mkString("[", ", ", "]")
  }
}