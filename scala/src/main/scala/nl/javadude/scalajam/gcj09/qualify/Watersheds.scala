package nl.javadude.scalajam.gcj09.qualify

import scala.io._
import java.io._
import nl.javadude.scalajam.utils.FileHelper._
import nl.javadude.scalajam.utils.GoogleCodeHelper._
import nl.javadude.scalajam.CodeJam

/**
 * Watersheds
 * @author Jeroen van Erp - jeroen @ hierynomus.com
 */
object Watersheds extends CodeJam {
 
	def solveProblem(reader : Iterator[String]) : String = {
		val ar = reader.nextIntArray
		val (h, w) = (ar(0), ar(1))
  
		val theMap = (1 to h).map(x => reader.nextIntArray).toArray
  
		val shed = new Shed(h, w, theMap)
		val basins : Array[String] = shed.solve
		printRecursive(basins, w)
	}

	def printRecursive(basins : Array[String], length : Int) : String = {
		basins.size match {
		  	case 0 => ""
		  	case _ => "\n" + basins.take(length).reduceLeft(_+" "+_) + printRecursive(basins.drop(length), length)
		}
	}
}

class Shed(val h : Int, val w : Int, val theMap : Array[Array[Int]]) {
	val flowMap = (for (hPos <- 0 until h; wPos <- 0 until w) yield determineFlowTo(hPos, wPos)).toArray
	val basins : Array[Int] = Array.make(h * w, -1)

	def determineFlowTo(hPos : Int, wPos : Int) : Int = {
		val north = determineAltitude(hPos - 1, wPos)
		val west = determineAltitude(hPos, wPos - 1)
		val east = determineAltitude(hPos, wPos + 1)
		val south = determineAltitude(hPos + 1, wPos)
		val me = determineAltitude(hPos, wPos)
		val minAltitude = List(me, north, west, east, south).sort(_<_).head
  
		if (me > north && north == minAltitude) {
			convertToIndex(hPos - 1, wPos)
		} else if (me > west && west == minAltitude) {
			convertToIndex(hPos, wPos - 1)
		} else if (me > east && east == minAltitude) {
			convertToIndex(hPos, wPos + 1)
		} else if (me > south && south == minAltitude) {
			convertToIndex(hPos + 1, wPos)
		} else {
			convertToIndex(hPos, wPos)
		}
	}

 	def determineAltitude(hPos : Int, wPos : Int) : Int = {
		if (hPos == h || hPos < 0) {
			10001 // more than max altitude in large map because out of range
		} else if (wPos == w || wPos < 0) {
			10001 // more than max altitude in large map because out of range
		} else {
			theMap(hPos)(wPos)
		}
	}

	def convertToIndex(hPos : Int, wPos : Int) = {
		hPos * w + wPos
	}

	/*
     * Solve methods	
	 */
	def solve() : Array[String] = {
		val basinString = "abcdefghijklmnopqrstuvwxyz".split("")
		var currentHighestBasin = -1
		(0 until h * w).foreach(i => if (basins(i) == -1) currentHighestBasin = fillFlow(i, currentHighestBasin))
		basins.map(i => basinString(i + 1)).toArray
	}

  	def fillFlow(index : Int, currentHighestBasin : Int) : Int = {
		val flowTo = flowMap(index)
		if (basins(flowTo) != -1) {
			basins(index) = basins(flowTo)
			currentHighestBasin
		} else {
			val basinValue = findBasinValue(index)
			if (basinValue != -1) {
				fillTheFlow(index, basinValue)
				currentHighestBasin
			} else {
				fillTheFlow(index, currentHighestBasin + 1)
				currentHighestBasin + 1
			}
		}
	}

	def findBasinValue(index : Int) : Int = {
		flowMap(index) match {
		  	case `index` => basins(index)
		  	case _ => findBasinValue(flowMap(index))
		}
	}

	def fillTheFlow(index : Int, basin : Int) {
		basins(index) = basin
		val flowTo = flowMap(index)
		if (flowTo != index && basins(flowTo) == -1) fillTheFlow(flowTo, basin)
	}
}