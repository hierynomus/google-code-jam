package qualify

import java.io.File
import scala.math._
import scalax.io._
import scala._
import scala.math.BigDecimal
import scala.Some
import scala.Iterator
import scala.math.BigInt

object BotTrust {

  import googlecodejam._

  case class Move(bot: String, button: Int)

  case class RobotPos(pos: Int, allRemainingMoves: IndexedSeq[Move])

  def solveProblem(reader: Iterator[String]) = {
    val moves = reader.nextStringArray
    val moveSeq = 1.to(moves.size - 1, 2).map(x => Move(moves(x), moves(x + 1).toInt))

    def simulate(time: Int, allMoves: IndexedSeq[Move], posOrange: Int, posBlue: Int) : Int = {
      if (allMoves.isEmpty) time
      else {
        val orangeMove : RobotPos = moveBot(posOrange, "O", allMoves)
        val blueMove = moveBot(posBlue, "B", allMoves)
        simulate(time + 1, if (orangeMove.allRemainingMoves.size < blueMove.allRemainingMoves.size) orangeMove.allRemainingMoves else blueMove.allRemainingMoves, orangeMove.pos, blueMove.pos)
      }
    }

    def moveBot(pos: Int, bot: String, allMoves: IndexedSeq[Move]): RobotPos = allMoves.find(x => x.bot == bot) match {
      case Some(botMove) =>
        if (botMove.button == pos) {
          if (botMove == allMoves.head) RobotPos(pos, allMoves.tail) else RobotPos(pos, allMoves)
        } else
          RobotPos(if (botMove.button < pos) pos - 1 else pos + 1, allMoves)
      case None => RobotPos(pos, allMoves)
    }

    simulate(0, moveSeq, 1, 1)
  }

  def name = "qualify.BotTrust"

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
