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

  def expand(g: Grammar): Grammar = Grammar(expand(g.productions, Nil).reverse)

  def expand(g: List[Production], acc: List[Production]): List[Production] = {
    g match {
      case Nil => acc
      case p :: ps =>
        val (g2, acc2) = expand(p, (ps, acc))
        expand(g2, acc2)
    }
  }

  def expand(p: Production, gacc: (List[Production], List[Production])): (List[Production], List[Production]) = {
    (p, gacc) match {
      case (Production(nont, Rhs(Alternation(Nil))), (g, acc)) =>
        (g, acc)
      case (Production(nont, Rhs(Alternation(c :: cs))), (g, acc)) =>
        expand(Production(nont, Rhs(Alternation(cs))), expand(nont, c.terms, Nil, (g, acc)))
    }
  }

  def expand(nont: Nonterminal, cin: List[Term], cout: List[Term], gacc: (List[Production], List[Production])): (List[Production], List[Production]) = {
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
        printf("%s = %s\n", tag, rhs)
        val prod = Production(lhs, Rhs(rhs))
        var newGacc = (prod :: g, acc)
        newGacc = expand(nont, rest, cout, newGacc)
        newGacc = expand(nont, rest, lhs :: cout, newGacc)
        newGacc
      case (sym :: rest, (g, acc)) =>
        expand(nont, rest, sym :: cout, (g, acc))
    }
  }

}

