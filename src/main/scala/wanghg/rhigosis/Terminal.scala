package wanghg.rhigosis

/**
  * Created by wanghg on 16/4/2017.
  */
case class Grammar(prods: List[Prod])

case class Prod(nont: Nonterminal, options: Options, nullable: Boolean)

case class Options(opts: List[Term]) extends Term

case class Sequence(terms: List[Term]) extends Term

sealed abstract class Term

case class Terminal(sym: Symbol) extends Term

case class Nonterminal(sym: Symbol) extends Term

case class Group(term: Options) extends Term

case class ZeroOrOne(term: Options) extends Term

case class ZeroOrMore(term: Options) extends Term

