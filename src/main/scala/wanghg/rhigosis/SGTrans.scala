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
    val file2 = new File(args(1))
    require(file.exists())
    val g: Grammar = parse(file).distinct
    val expanded = expand(g).distinct
    val output = expanded.toString
    println(output)
    Utils.withWriter(file2) { writer =>
      writer.write(output)
    }
  }

  def parse(file: File): Grammar = {
    Utils.withReader(file) { reader =>
      val lexer = new SGLexer(reader, file.getPath, fact)
      val parser = new SGInputParser(lexer, fact)
      parser.parse().value.asInstanceOf[Grammar]
    }
  }

  def expand(g: Grammar): Grammar = {
    val (g2, acc2) = expand(g, Nil)
    g2.copy(productions = acc2.reverse)
  }

  def expand(g: Grammar, acc: List[Production]): (Grammar, List[Production]) = {
    g match {
      case Grammar(_, _, Nil) => (g, acc)
      case Grammar(_, _, p :: ps) =>
        val (g2, acc2) = expand(p, (g.copy(productions = ps), acc))
        expand(g2, acc2)
    }
  }

  def expand(p: Production, gacc: (Grammar, List[Production])): (Grammar, List[Production]) = {
    (p, gacc) match {
      case (Production(nont, Rhs(Alternation(Nil))), (g, acc)) =>
        (g, acc)
      case (Production(nont, Rhs(Alternation(c :: cs))), (g, acc)) =>
        expand(Production(nont, Rhs(Alternation(cs))), expand(nont, c.terms, Nil, (g, acc)))
    }
  }

  def expand(nont: Nonterminal,
             cin: List[Term],
             cout: List[Term],
             gacc: (Grammar, List[Production])): (Grammar, List[Production]) = {
    (cin, gacc) match {
      case (Nil, (g, acc)) =>
        (g, Production(nont, Rhs(Alternation(List(Concatenation(cout.reverse))))) :: acc)
      case (Concatenation(terms) :: rest, (g, acc)) =>
        expand(nont, terms ++ rest, cout, (g, acc))
      case (Grouping(alt) :: rest, (g, acc)) =>
        expand(nont, alt :: rest, cout, (g, acc))
      case (Optional(alt) :: rest, (g, acc)) =>
        var newGacc = (g, acc)
        newGacc = expand(nont, rest, cout, newGacc)
        newGacc = expand(nont, alt :: rest, cout, newGacc)
        newGacc
      case (Alternation(cats) :: rest, (g, acc)) =>
        var newGacc = (g, acc)
        for (cat <- cats) {
          newGacc = expand(nont, cat :: rest, cout, newGacc)
        }
        newGacc
      case (Repetition(alt) :: rest, (g, acc)) =>
        val tag = String.format("_X%08x", Repetition(alt).hashCode.asInstanceOf[Integer])
        val lhs = Nonterminal(Symbol(tag))
        val rhs = Alternation(List(
          Concatenation(List(alt)),
          Concatenation(List(lhs, alt))
        ))
        val prod = Production(lhs, Rhs(rhs))
        var newGacc = (Grammar(g.terminals, lhs :: g.nonterminals, prod :: g.productions), acc)
        newGacc = expand(nont, rest, cout, newGacc)
        newGacc = expand(nont, rest, lhs :: cout, newGacc)
        newGacc
      case (sym :: rest, (g, acc)) =>
        expand(nont, rest, sym :: cout, (g, acc))
    }
  }

}

