package nl.javadude.scala.africajam.acj10.qualify

import nl.javadude.scala.CodeJam
import nl.javadude.scala.utils.GoogleCodeHelper._

object StoreCredit extends CodeJam {
  def solveProblem(reader: Iterator[String]) = {
    val credit = reader.trimmedLine.toInt
    reader.next
    val products = reader nextIntArray

    val sortedProducts = products sorted(Ordering.Int)

    val (a, b) = findProducts(sortedProducts, 0, sortedProducts.length - 1, credit)

    val producti = products.indexOf(sortedProducts(a)) + 1
    val productj = products.indexOf(sortedProducts(b)) + 1

    if (producti == productj) producti + " " + (producti + 1) else
    if (producti < productj) producti + " " + productj else productj + " " + producti
  }

  def findProducts(sortedProducts : Array[Int], i : Int, j : Int, credit : Int) : (Int, Int) = {
    val sum: Int = sortedProducts(i) + sortedProducts(j)
    if (sum == credit) {
      (i, j)
    } else if (sum < credit) {
      findProducts(sortedProducts, i + 1, j, credit)
    } else {
      findProducts(sortedProducts, i, j - 1, credit)
    }
  }
}