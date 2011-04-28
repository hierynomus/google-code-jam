package nl.javadude.scala.utils

import _root_.scala.collection.mutable.ArrayBuffer

/**
 * Helper class that adds a number of useful methods to an Iterator[String]
 * @author Jeroen van Erp - jeroen at hierynomus.com
 */
class GoogleCodeHelper(val iterator : Iterator[String]) {
  
	def trimmedLine = {
		iterator next() trim()
	}
	def nextInt : Int = {
		trimmedLine toInt
	}
 
	def nextIntArray : Array[Int] = {
		nextStringArray map (_.toInt)  
	}

  def nextCharArray : Array[Char] = {
    iterator next() toCharArray
  }
	def nextLongArray : Array[Long] = {
		nextStringArray map (_.toLong)  
	}

	def nextBigDecimalArray : Array[BigDecimal] = {
		nextLongArray map (BigDecimal(_))
	}

 	def nextBigIntArray : Array[BigInt] = {
		nextLongArray map (BigInt(_))
	}

 	def nextStringArray : Array[String] = {
		trimmedLine split " "  
	}

	def skipLines(nr:Int) {
		for (val i <- 1 to nr) {
			iterator next
		}
	}
}

object GoogleCodeHelper {
	implicit def iteratorToHelper(x:Iterator[String]) = {
		new GoogleCodeHelper(x)
	}
}