package nl.javadude.codejam.gcj11.qualify

import _root_.scala.collection.mutable._
import nl.javadude.scala.utils.GoogleCodeHelper._
import nl.javadude.scala.CodeJam

/**
 * After analysis, this is actually the code really needed...
 *
 */
object CandySplitting_2 extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    val n = reader.nextInt
    val candies = reader.nextIntArray

    if (candies.reduceLeft(_^_) == 0) {
      candies.toList.sorted(Ordering.Int).tail.reduceLeft(_+_).toString
    } else {
      "NO"
    }
  }
}

/**
 * Implementation written during the Qualification round, works, though unable to solve the large set...
 */
object CandySplitting extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    val n = reader.nextInt
    val candies = reader.nextIntArray.toList

    val lookup = Map[(Int, Int), Option[Int]]()

    def solve(pile1: Pile, pile2: Pile, candies : List[Int]) : Option[Int] = {
      val pair = (pile1.value, pile2.value)
      val reversedPair = (pile2.value, pile1.value)
      if (lookup.contains(pair)) lookup(pair) else
      if (lookup.contains(reversedPair)) lookup(reversedPair) else {
        if (candies.isEmpty) {
          if (patrickSum(pile1) == patrickSum(pile2) && !pile1.isEmpty && !pile2.isEmpty) Some(math.max(seanSum(pile1), seanSum(pile2))) else None
        } else {
          val opt = whichOpt(solve(Pile(pile1, candies.head), pile2, candies.tail), solve(Pile(pile2, candies.head), pile1, candies.tail))
          lookup.put((pile1.value, pile2.value), opt)
          opt
        }
      }
    }

    val option: Option[Int] = solve(Pile(), Pile(), candies)

    option match {
      case Some(x) => option.get.toString
      case None => "NO"
    }
  }

  def whichOpt(a : Option[Int], b : Option[Int]) : Option[Int] = {
    a match {
      case Some(x) => b match {
        case Some(x) => Some(math.max(a.get, b.get))
        case None => a
      }
      case None => b
    }
  }

  def patrickSum(c : Pile) : Int = {
    c.pile.foldLeft(0)(_^_)
  }

  def seanSum(c : Pile) : Int = {
    c.pile.foldLeft(0)(_+_)
  }

  def patrickSum(i : Int, j : Int) : Int = {
    i ^ j
  }

  def seanSum(i : Int, j : Int) : Int = {
    i + j
  }

  class Pile(val value : Int, val pile: List[Int]) {
    def isEmpty : Boolean = pile.isEmpty
  }

  object Pile {
    def apply() = new Pile(0, List[Int]())
    def apply(pile : Pile, c : Int) = {
      new Pile(pile.value + c, c :: pile.pile)
    }
  }
}