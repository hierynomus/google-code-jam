package nl.javadude.scalajam.utils

import java.io._
import _root_.scala.collection.mutable._

class FileHelper(file : File) {
	def write(text : String) : Unit = {
		val fw = new FileWriter(file)
		try{ fw.write(text) }
		finally{ fw.close }
	}

 	def write(text : Iterable[String]) : Unit = {
		val fw = new FileWriter(file)
		try{ fw.write(text.toList.tail.foldLeft(text.toList.head) {_+"\n"+_}) }
		finally{ fw.close }
	}

}
object FileHelper{
  implicit def file2helper(file : File) = new FileHelper(file)
}
