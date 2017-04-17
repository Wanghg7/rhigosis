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
    println(expand(g))
  }

  def parse(file: File): Grammar = {
    Utils.withReader(file) { reader =>
      val lexer = new SGLexer(reader, file.getPath, fact)
      val parser = new SGInputParser(lexer, fact)
      parser.parse().value.asInstanceOf[Grammar]
    }
  }

  def expand(g: Grammar): Grammar = Grammar(expand(g, Nil).reverse)

  def expand(g: Grammar, acc: List[Production]): List[Production] = {
    g match {
      case Grammar(Nil) => acc
      case Grammar(p :: ps) => expand(Grammar(ps), expand(p, acc))
    }
  }

  def expand(p: Production, acc: List[Production]): List[Production] = {
    p match {
      case Production(nont, Rhs(Alternation(Nil))) =>
        acc
      case Production(nont, Rhs(Alternation(c :: cs))) =>
        expand(Production(nont, Rhs(Alternation(cs))), expand(nont, c.terms, Nil, acc))
    }
  }

  def expand(nont: Nonterminal, cin: List[Term], cout: List[Term], acc: List[Production]): List[Production] = {
    cin match {
      case Nil =>
        Production(nont, Rhs(Alternation(List(Concatenation(cout.reverse))))) :: acc
      case Concatenation(terms) :: rest =>
        expand(nont, terms ++ rest, cout, acc)
      case Grouping(alt) :: rest =>
        expand(nont, alt :: rest, cout, acc)
      case Optional(alt) :: rest =>
        var newAcc = acc
        newAcc = expand(nont, rest, cout, newAcc)
        newAcc = expand(nont, alt :: rest, cout, newAcc)
        newAcc
      case Alternation(cats) :: rest =>
        var newAcc = acc
        for (cat <- cats) {
          newAcc = expand(nont, cat :: rest, cout, newAcc)
        }
        newAcc
      case Repetition(alt) :: rest =>
        expand(nont, rest, Repetition(alt) :: cout, acc)
      case sym :: rest =>
        expand(nont, rest, sym :: cout, acc)
    }
  }

}

