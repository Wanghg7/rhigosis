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
    val extracted = expandOptions(g, ListBuffer.empty[Prod])
    println(extracted)
  }

  def expandOptions(g: Grammar, acc: ListBuffer[Prod]): Grammar = {
    g match {
      case Grammar(Nil) => Grammar(acc.toList)
      case Grammar(p :: ps) => expandOptions(p, acc); expandOptions(Grammar(ps), acc)
    }
  }

  def expandOptions(p: Prod, acc: ListBuffer[Prod]): Unit = {
    p match {
      case Prod(nont, Options(Nil), false) =>
        ()
      case Prod(nont, Options(Nil), true) =>
        acc += Prod(nont, Options(Nil), false)
      case Prod(nont, Options(o :: os), nullable) =>
        acc += Prod(nont, Options(List(o)), false)
        expandOptions(Prod(nont, Options(os), nullable), acc)
    }
  }

  def parse(file: File): Grammar = {
    Utils.withReader(file) { reader =>
      val lexer = new SGLexer(reader, file.getPath, fact)
      val parser = new SGInputParser(lexer, fact)
      parser.parse().value.asInstanceOf[Grammar]
    }
  }

}

