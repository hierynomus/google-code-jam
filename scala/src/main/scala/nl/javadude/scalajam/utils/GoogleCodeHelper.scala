package nl.javadude.scalajam.utils

import _root_.scala.collection.mutable.ArrayBuffer

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

 	def nextStringArray : Array[String] = {
		trimmedLine split " "  
	}

	def readLines(nr:Int) {
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