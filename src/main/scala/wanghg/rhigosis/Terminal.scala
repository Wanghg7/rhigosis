package wanghg.rhigosis

/**
  * Created by wanghg on 16/4/2017.
  */
case class Grammar(terminals: List[Terminal], nonterminals: List[Nonterminal], productions: List[Production]) {

  override def toString: String = {
    val sb = new StringBuilder()
    sb ++= terminals.mkString("", "\n", "\n")
    sb ++= nonterminals.mkString("", "\n", "\n")
    productions.mkString("", "\n\n", "\n\n")
  }
}

case class Production(nont: Nonterminal, rhs: Rhs) {

  override def toString: String = String.format("%24s ::= %s;", nont, rhs)
}

case class Rhs(alternation: Alternation) {

  override def toString: String = alternation.concatenations.mkString(String.format("\n%28s ", "|"))
}

sealed abstract class Term

case class Alternation(concatenations: List[Concatenation]) extends Term {

  override def toString: String = concatenations.mkString(" | ")
}

case class Concatenation(terms: List[Term]) extends Term {

  override def toString: String = terms.mkString(" ")
}

case class Grouping(alternation: Alternation) extends Term {

  override def toString: String = String.format("(%s)", alternation)
}

case class Optional(alternation: Alternation) extends Term {

  override def toString: String = String.format("[%s]", alternation)
}

case class Repetition(alternation: Alternation) extends Term {

  override def toString: String = String.format("{%s}", alternation)
}

case class Terminal(symbol: Symbol) extends Term {

  override def toString: String = symbol.name
}

case class Nonterminal(symbol: Symbol) extends Term {

  override def toString: String = symbol.name
}

