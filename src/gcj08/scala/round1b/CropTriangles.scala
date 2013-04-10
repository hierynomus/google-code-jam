package round1b

import collection.Iterator
import io.Source
import java.io.{FileWriter, File}

object CropTriangles {

  import GoogleCodeHelper.iteratorToHelper



  def solveProblem(reader: Iterator[String]) = {
    val Array(n, a, b, c, d, x0, y0, m) = reader.nextLongArray
    var (x, y) = (x0, y0)

    def bucket(x: Long, y: Long) = ((x % 3) * 3 + (y % 3)).toInt

    val trees = (bucket(x, y) :: (for (i <- 1 to (n - 1).toInt) yield {
      x = (a * x + b) % m; y = (c * y + d) % m; bucket(x, y)
    }).toList).groupBy(x => x).map(t => (t._1, t._2.size)).withDefaultValue(0)

    def isCenterAtGrid(i: Int, j: Int, k: Int) : Boolean = {
      ((((i / 3) + (j / 3) + (k / 3)) % 3) == 0) && ((((i % 3) + (j % 3) + (k % 3)) % 3) == 0)
    }

    (for (i <- 0 until 9) yield trees(i) * trees(i) - 1 * trees(i) - 2 / 6).reduce(_+_) +
    (for (i <- 0 until 9; j <- i + 1 until 9; k <- j + 1 until 9; if (isCenterAtGrid(i, j, k))) yield trees(i)*trees(j)*trees(k)).reduce(_+_)
  }

  def main(args: Array[String]) {
    if (args.length == 0) {
      throw new IllegalArgumentException("Could not find input file in arguments")
    }

    val input = args(0)
    val outputFile = new File((input.substring(0, input.lastIndexOf("."))) + ".out")
    val reader: Iterator[String] = Source.fromFile(input).getLines()
    val nrProblems = reader.nextInt
    val results = new Array[String](nrProblems)
    for (i <- 1 to nrProblems) {
      println("Solving case " + i)
      results(i - 1) = "Case #" + i + ": " + solveProblem(reader)
      println(results(i - 1))
    }
    val fw = new FileWriter(outputFile)
    try {
      fw.write(results.mkString("\n"))
    } finally {
      fw.close()
    }
  }

  class GoogleCodeHelper(val iterator: Iterator[String]) {

    def trimmedLine = iterator.next().trim()

    def nextInt: Int = trimmedLine.toInt

    def nextIntArray: Array[Int] = nextStringArray map (_.toInt)

    def nextCharArray: Array[Char] = iterator.next().toCharArray

    def nextLongArray: Array[Long] = nextStringArray map (_.toLong)

    def nextDoubleArray: Array[Double] = nextStringArray map (_.toDouble)

    def nextBigDecimalArray: Array[BigDecimal] = nextLongArray map (BigDecimal(_))

    def nextBigIntArray: Array[BigInt] = nextStringArray map (BigInt(_))

    def nextStringArray: Array[String] = trimmedLine split " "

    def skipLines(nr: Int) {
      for (i <- 1 to nr) {
        iterator.next()
      }
    }
  }

  object GoogleCodeHelper {
    implicit def iteratorToHelper(x: Iterator[String]): GoogleCodeHelper = new GoogleCodeHelper(x)
  }

}
