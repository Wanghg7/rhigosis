package wanghg.rhigosis

import java.io.{File, StringReader}
import java_cup.runtime.{ComplexSymbolFactory, SymbolFactory}

import wanghg.Utils

import scala.collection.mutable.ListBuffer

/**
  * Created by wanghg on 14/4/2017.
  */
object SGTrans {

  val fact = new ComplexSymbolFactory();

  def main(args: Array[String]): Unit = {
    val file = new File(args(0))
    require(file.exists())
    val g: Grammar = parse(file)
    val extracted = expandClause(g, ListBuffer.empty[Prod])
    println(extracted)
  }

  def expandClause(g: Grammar, acc: ListBuffer[Prod]): Grammar = {
    g match {
      case Grammar(Nil) => Grammar(acc.toList)
      case Grammar(p :: ps) => expandClause(p, acc); expandClause(Grammar(ps), acc)
    }
  }

  def expandClause(p: Prod, acc: ListBuffer[Prod]): Unit = {
    p match {
      case Prod(nont, Options(Nil), false) =>
        ()
      case Prod(nont, Options(Nil), true) =>
        acc += Prod(nont, Options(Nil), false)
      case Prod(nont, Options(o :: os), nullable) =>
        expandSequence(nont, o.asInstanceOf[Sequence], acc)
        expandClause(Prod(nont, Options(os), nullable), acc)
    }
  }

  def expandSequence(nont: Nonterminal, s: Sequence, acc: ListBuffer[Prod]): Unit = {
    acc += Prod(nont, Options(List(s)), false)
  }

  def parse(file: File): Grammar = {
    Utils.withReader(file) { reader =>
      val lexer = new SGLexer(reader, file.getPath, fact)
      val parser = new SGInputParser(lexer, fact)
      parser.parse().value.asInstanceOf[Grammar]
    }
  }

}

