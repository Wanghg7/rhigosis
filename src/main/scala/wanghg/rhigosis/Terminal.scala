package wanghg.rhigosis

/**
  * Created by wanghg on 16/4/2017.
  */
case class Grammar(prods: List[Prod]) {

  override def toString: String = {
    val sb = new StringBuilder
    prods.foreach(p => {
      sb.append(String.format("%24s ::= ", p.nont.sym.name))
      sb.append(p.options.opts.mkString(String.format("\n%28s ", "|")))
      sb.append(";\n\n")
    })
    sb.toString
  }
}

case class Prod(nont: Nonterminal, options: Options, nullable: Boolean)

case class Options(opts: List[Term]) extends Term {

  override def toString: String = opts.mkString(" | ")
}

case class Sequence(terms: List[Term]) extends Term {

  override def toString: String = {
    terms.mkString(" ")
  }
}

sealed abstract class Term

case class Terminal(sym: Symbol) extends Term {

  override def toString: String = sym.name
}

case class Nonterminal(sym: Symbol) extends Term {

  override def toString: String = sym.name
}

case class Group(term: Options) extends Term {

  override def toString: String = String.format("(%s)", term)
}

case class ZeroOrOne(term: Options) extends Term {

  override def toString: String = String.format("[%s]", term)
}

case class ZeroOrMore(term: Options) extends Term {

  override def toString: String = String.format("{%s}", term)
}

