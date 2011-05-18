package nl.javadude.codejam.gcj10.round1a

import _root_.scala.collection.mutable._
import scala.io._
import java.io._
import nl.javadude.scala.utils.GoogleCodeHelper._
import nl.javadude.scala.CodeJam

object Rotate extends CodeJam {
  def readBoard(iterator: Iterator[String], size: Int): List[scala.List[Char]] = {
    (for (i <- 0 until size) yield iterator.nextCharArray.toList).toList
  }

  def cycle(list: List[Char]): scala.List[Char] = {
    val size = list.size
    val rotated = (for (elem <- list; if (elem != '.')) yield elem).toList
    val empty = (for (i <- 0 until size - rotated.size) yield '.').toList
    List.concat(empty, rotated)
  }

  def rotateBoard(list: List[List[Char]]): scala.List[scala.List[Char]] = {
    list.map(cycle(_))
  }

  def checkHorizontal(list: List[List[Char]], c: Char, k : Int) = {
    val slice = List.fill(k)(c)
    (for (line <- list) yield line.containsSlice(slice)).reduceLeft(_||_)
  }

  def checkVertical(list: List[List[Char]], c: Char, k: Int): Boolean = {
    (for { x <- 0 until list.size
          y <- 0 to list.size - k
          if (list(y)(x) == c)
    } yield ((for (y2 <- y until y + k) yield (list(y2)(x) == c)).foldLeft(true) (_&&_))).foldLeft(false)(_||_)
  }

  def checkDiagonal(list: List[List[Char]], c: Char, k: Int): Boolean = {
    ((for { x <- 0 to list.size - k
          y <- 0 to list.size - k
          if (list(y)(x) == c)
    } yield ((for (i <- 0 until k) yield (list(y+i)(x+i) == c)).foldLeft(true)(_&&_))).foldLeft(false)(_||_)) ||
    ((for { x <- k - 1 until list.size
          y <- 0 to list.size - k
          if (list(y)(x) == c)
    } yield ((for (i <- 0 until k) yield (list(y+i)(x-i) == c)).foldLeft(true)(_&&_))).foldLeft(false)(_||_))
  }

  def checkWin(board: List[List[Char]], value: Char, k: Int) : Boolean = {
    checkHorizontal(board, value, k) || checkVertical(board, value, k) || checkDiagonal(board, value, k)
  }

  def solveProblem(reader: Iterator[String]) = {
    val Array(n, k) = reader.nextIntArray
    println("n = " + n + ", k = " + k)

    val board : List[List[Char]] = readBoard(reader, n)

    val rotated : List[List[Char]] = rotateBoard(board)
    println(rotated)

    val rWon = checkWin(rotated, 'R', k)
    val bWon = checkWin(rotated, 'B', k)
    if (rWon && bWon) "Both" else
    if (rWon) "Red" else
    if (bWon) "Blue" else "Neither"
  }
}
