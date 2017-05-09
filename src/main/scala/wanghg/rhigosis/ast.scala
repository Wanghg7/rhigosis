package wanghg.rhigosis

sealed abstract class Symbol {
  val start: Int
  val end: Int
}

sealed abstract class Terminal extends Symbol

sealed abstract class Literal extends Terminal

case class IntLiteral(value: Int, start: Int, end: Int) extends Literal

case class LongLiteral(value: Long, start: Int, end: Int) extends Literal

case class BooleanLiteral(value: Boolean, start: Int, end: Int) extends Literal

case class CharacterLiteral(value: Char, start: Int, end: Int) extends Literal

case class StringLiteral(value: String, start: Int, end: Int) extends Literal

case class SymbolLiteral(value: scala.Symbol, start: Int, end: Int) extends Literal

case class NULL(start: Int, end: Int) extends Literal

case class QualId(value: List[String], start: Int, end: Int) extends Terminal

case class Ids(value: List[String], start: Int, end: Int) extends Terminal

