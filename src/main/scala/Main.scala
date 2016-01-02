import scala.io._
import scala.swing._
import java.io._
import java.lang.NumberFormatException


// Glavni objekt z vsemi impliciti in kljucnimi besedami
object CSVLang {
  // ----------------- Kljucne besede -------------------------------
  trait ActionKeyword
  case object drop extends ActionKeyword
  case object swap extends ActionKeyword
  case object revrow extends ActionKeyword

  trait DataKeyword
  case object ask extends DataKeyword
  case object out extends DataKeyword
  case object screen extends DataKeyword

  trait FormatKeyword
  case object int extends FormatKeyword
  case object dbl extends FormatKeyword
  case object ign extends FormatKeyword

  trait ExpressionKeyword
  case object acc extends ExpressionKeyword
  case object col extends ExpressionKeyword

  // ----------------- Impliciti za CsvData -------------------------------

  // --------------------- Impliciti za CsvAction ----------------------

  // ----------------------  Impliciti za preverjanje formata ----------------

  // -------------------- Impliciti za izraze -------------

}


//  -------------------- Razredi za Nalogo 1. -------------

// Podatkovni viri
case class CsvData(data: Stream[Stream[String]], separator: Char) {
  def >>>(action: CsvAction): CsvData = ???
}

//Akcije
trait CsvAction {
  def apply(data: CsvData): CsvData
  def ->(action: CsvAction): CsvAction = ???
}

// ta razred uporabite za zaporedje akcij
case class SeqAction(firstA: CsvAction, secondA: CsvAction) extends CsvAction {
  def apply(data: CsvData): CsvData = ???
}

case class RevAction(revCol: Boolean) extends CsvAction {
  def apply(data: CsvData): CsvData = ???
}

case class SwapCol(e1: Int, e2: Int) extends CsvAction {
  def apply(csv: CsvData): CsvData = ???
}

case class DropCol(i: Int) extends CsvAction {
  def apply(csv: CsvData): CsvData = ???
}

case class DataMerge(other: CsvData) extends CsvAction {
  def apply(csv: CsvData): CsvData = ???
}

case class DataOut(filename: String) extends CsvAction {
  def apply(csv: CsvData): CsvData = ???
}



//  -------------------- Razredi za Nalogo 2. -------------

case class FormatAction(f: CsvFormat) extends CsvAction {
  def apply(csv: CsvData): CsvData = ???
}

case class ExpressionAction(expr: CsvExpr) extends CsvAction {
  def apply(csv: CsvData): CsvData = ???
}

trait CsvFormat {
  // pomozni funkciji - uporabno za preverjanje formata
  def safeStringToInt(str: String): Option[Int] = {
    import scala.util.control.Exception._
    catching(classOf[NumberFormatException]) opt str.toInt
  }
  def safeStringToDouble(str: String): Option[Double] = {
    import scala.util.control.Exception._
    catching(classOf[NumberFormatException]) opt str.toDouble
  }

  def |(other: CsvFormat): CsvFormat
  def checkAndMap(l: Stream[String]): Stream[String]
}

trait CsvExpr {
  def |+(other: CsvExpr): CsvExpr = ???
  def |*(other: CsvExpr): CsvExpr = ???
  def |/(other: CsvExpr): CsvExpr = ???
  def eval(line: Stream[String], accum: Double): Double = ???
}
