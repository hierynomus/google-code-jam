package qualification

import java.io.File
import scala.collection.immutable.IndexedSeq
import scalax.io.{Output, Resource, Codec}
import scala.math._

object TicTacTomek {
  import googlecodejam._

  def solveProblem(reader: Iterator[String]): String = {
    val board: Array[Array[Char]] = (for (i <- 1.to(4)) yield reader.nextCharArray).toArray
    if (reader.hasNext) reader.next()

    def checkBoard(y: Int, x: Int, piece: Char): Boolean = board(y)(x) == piece || board(y)(x) == 'T'

    val rows: IndexedSeq[Char] = for (y <- 0.to(3)) yield {
      def check(piece: Char): Char = if (0.to(3).forall(checkBoard(y, _, piece))) piece else '_'
      if (check('X') == 'X') 'X' else check('O')
    }

    val row: Option[Char] = rows.find(_ != '_')
    if (row.isDefined) return row.get + " won"

    val cols: IndexedSeq[Char] = for (x <- 0.to(3)) yield {
      def check(piece: Char): Char = if (0.to(3).forall(checkBoard(_, x, piece))) piece else '_'
      if (check('X') == 'X') 'X' else check('O')
    }
    val col: Option[Char] = cols.find(_ != '_')
    if (col.isDefined) return col.get + " won"


    val diag1 = if (0.to(3).forall(x => checkBoard(x, x, 'X'))) 'X' else if (0.to(3).forall(x => checkBoard(x, x, 'O'))) 'O' else '_'
    if (diag1 != '_') return diag1 + " won"
    val diag2 = if (0.to(3).forall(x => checkBoard(x, 3 - x, 'X'))) 'X' else if (0.to(3).forall(x => checkBoard(x, 3 - x, 'O'))) 'O' else '_'
    if (diag2 != '_') return diag2 + " won"

    if (board.flatten.contains('.')) "Game has not completed" else "Draw"
  }

  def check(c: Char, h: Char) = if (c == 'T') h else if (c == h) h else if (h == '.') h else '_'

  def name = "qualification.TicTacTomek"

  def main(args: Array[String]) {
    implicit val codec = Codec.UTF8
    val input: String = args(0)
    val iterator: Iterator[String] = Resource.fromFile(input).lines().toIterator
    val file = new File(input.substring(0, input.lastIndexOf(".")) + ".out")
    file.delete()
    val output: Output = Resource.fromFile(file)

    val nrProblems = iterator.nextInt

    output.writeStrings((1 to nrProblems).map(p => "Case #" + p + ": " + solveProblem(iterator)), "\n")
  }

  object googlecodejam {
    case class Point(x: Int, y: Int) {
      def dist(p: Point) = sqrt(pow(x - p.x, 2) + pow(y - p.y, 2))
    }
    case class FPoint(x: Double, y: Double) {
      def dist(p: FPoint) = sqrt(pow(x - p.x, 2) + pow(y - p.y, 2))
    }

    class RichIterator(val iterator: Iterator[String]) {

      def trimmedLine = iterator.next().trim()
      def nextInt: Int = trimmedLine.toInt
      def nextIntArray: Array[Int] = nextStringArray map (_.toInt)
      def nextCharArray: Array[Char] = iterator.next().toCharArray
      def nextLongArray: Array[Long] = nextStringArray map (_.toLong)
      def nextDoubleArray: Array[Double] = nextStringArray map (_.toDouble)
      def nextBigDecimalArray: Array[BigDecimal] = nextStringArray map (BigDecimal(_))
      def nextBigIntArray: Array[BigInt] = nextStringArray map (BigInt(_))
      def nextStringArray: Array[String] = trimmedLine split " "
      def nextIntGrid(y: Int): Array[Array[Int]] = (for (i <- 0 until y) yield nextIntArray).toArray
      def nextCharGrid(y: Int): Array[Array[Char]] = (for (i <- 0 until y) yield nextCharArray).toArray

      def skipLines(nr: Int) {
        for (i <- 1 to nr) {
          iterator.next()
        }
      }
    }

    implicit def iteratorToHelper(x: Iterator[String]): RichIterator = new RichIterator(x)
  }
}
