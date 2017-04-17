package wanghg.rhigosis

/**
  * Created by wanghg on 16/4/2017.
  */
case class Grammar(prods: List[Prod]) {

  override def toString: String = prods.mkString("", "\n\n", "\n\n")
}

case class Prod(nont: Nonterminal, rhs: Rhs) {

  override def toString: String = String.format("%24s ::= %s;", nont, rhs)
}

case class Rhs(options: Options) {

  override def toString: String = options.seqs.mkString(String.format("\n%28s ", "|"))
}

case class Options(seqs: List[Sequence]) extends Term {

  override def toString: String = seqs.mkString(" | ")
}

case class Sequence(terms: List[Term]) extends Term {

  override def toString: String = terms.mkString(" ")
}

sealed abstract class Term

case class Terminal(sym: Symbol) extends Term {

  override def toString: String = sym.name
}

case class Nonterminal(sym: Symbol) extends Term {

  override def toString: String = sym.name
}

case class Group(options: Options) extends Term {

  override def toString: String = String.format("(%s)", options)
}

case class ZeroOrOne(options: Options) extends Term {

  override def toString: String = String.format("[%s]", options)
}

case class ZeroOrMore(options: Options) extends Term {

  override def toString: String = String.format("{%s}", options)
}

