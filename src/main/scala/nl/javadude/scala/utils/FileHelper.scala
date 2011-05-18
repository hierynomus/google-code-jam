package nl.javadude.scala.utils

import java.io._
import _root_.scala.collection.mutable._

/**
 * Helper class and object that implicitly converts a File to a FileHelper
 * so that we can easily write a number of lines separated by line-ends to a file.
 * @author Jeroen van Erp - jeroen at hierynomus.com
 */
class FileHelper(file : File) {
 	def write(text : Iterable[String]) {
		val fw = new FileWriter(file)
		try{ fw.write(text.toList.tail.foldLeft(text.toList.head) {_+"\n"+_}) }
		finally{ fw.close() }
	}

}
object FileHelper{
  implicit def file2helper(file : File) = new FileHelper(file)
}
