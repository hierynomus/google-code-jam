package nl.javadude.codejam.gcj09.round1c

import nl.javadude.scala.CodeJam
import nl.javadude.scala.utils.GoogleCodeHelper._
import java.awt.Point

/**
 * Not correct yet :-(
 */
object CenterOfMass extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    val numberOfFlies = reader.nextInt

    val (dC, dV) = (for {
      i <- 0 until numberOfFlies
      val ar = reader.nextIntArray
    } yield ((ar(0), ar(1), ar(2)), (ar(3), ar(4), ar(5))))
      .reduceLeft((sum: ((Int, Int, Int), (Int, Int, Int)), adder: ((Int, Int, Int), (Int, Int, Int)))
        => ((sum._1._1 + adder._1._1, sum._1._2 + adder._1._2, sum._1._3 + adder._1._3),
            (sum._2._1 + adder._2._1, sum._2._2 + adder._2._2, sum._2._3 + adder._2._3)))

//    val dC = (center._1.toDouble / numberOfFlies, center._2.toDouble / numberOfFlies, center._3.toDouble / numberOfFlies)
//    val dV = (velocity._1.toDouble / numberOfFlies, velocity._2.toDouble / numberOfFlies, velocity._3.toDouble / numberOfFlies)

    println(dC + ", " + dV)

    // maths:
    // point x on line dC + t*dV = (dC._1 + t*dV._1, dC._2 + t*dV._2, dC._3 + t*dV._3)
    // because we are located on origin, the vector to point x is x
    // the minimum distance is when vector X and dV are perpendicular (dot product):
    // x.dV = dC._1 * dV._1 + t*dV._1*dV._1 + dC._2*dV._2 + t*dV._2*dV._2 + dC._3 *dV._3 + t*dV._3*dV._3 = 0
    // dC._1 * dV._1 + dC._2 * dV._2 + dC._3 * dV._3 = - t * dV._1^2 - t * dV._2^2 -t * dV._3^2
    // dC._1 * dV._1 + dC._2 * dV._2 + dC._3 * dV._3 = - t * (dV._1^2 + dV._2^2 + dV._3^2)
    // dC._1 * dV._1 + dC._2 * dV._2 + dC._3 * dV._3 / (dV._1^2 + dV._2^2 + dV._3^2) = - t

    val a = sqr(dV._1) + sqr(dV._2) + sqr(dV._3)
    val b = 2 * (dC._1 * dV._1 + dC._2 * dV._2 + dC._3 * dV._3)
    val c = sqr(dC._1) + sqr(dC._2) + sqr(dC._3)

    println(a + ", " + b + ", " + c)
    var t = 0.0
    if (a != 0) {
      t = -b / (2 * a)
    }
    println("t = " + t)
    if (t < 0) t = 0

    val d = sqrt(a * t * t + b * t + c) / numberOfFlies

    println("d = " + d)

    format("%.8f", d) + " " + t.toString
  }

  def sqr(d: Double) : Double = d*d
  def sqrt(d: Double) : Double = math.sqrt(d)
}
