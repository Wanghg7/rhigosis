package wanghg.rhigosis

import scala.util.parsing.combinator.RegexParsers

/**
  * Created by wanghg on 1/4/2017.
  */
class ScalaParsers extends RegexParsers {

  /**
    * op       ::=  opchar {opchar}
    *
    * TODO: mathematical symbols (Sm)
    * TODO: other symbols (So).
    */
  private[this] val OP =
    """[!#%&*+/:<=>?@\^|~-]+"""

  def op: Parser[String] = OP.r

  /**
    * idrest   ::=  {letter | digit} [‘_’ op]
    *
    */
  private[this] val IDREST =
    """[\p{Alnum}$_]*_""" + OP +"""|[\p{Alnum}$_]*"""

  def idrest: Parser[String] = IDREST.r

  /**
    * varid    ::=  lower idrest
    *
    */
  private[this] val VARID =
    """\p{Lower}""" + "(" + IDREST + ")"

  def varid: Parser[String] = VARID.r

  /**
    * boundvarid ::=  varid | ‘`’ varid ‘`’
    *
    */
  private[this] val BOUNDVARID = VARID + "|" + "`" + VARID + "`"

  def boundvarid: Parser[String] = BOUNDVARID.r

  /**
    * plainid  ::=  upper idrest |  varid |  op
    *
    */
  private[this] val PLAINID =
    """[\p{Upper}$_](""" + IDREST + ")|" + VARID + "|" + OP

  def plainid: Parser[String] = PLAINID.r

  /**
    * id       ::=  plainid |  ‘`’ { charNoBackQuoteOrNewline | UnicodeEscape | charEscapeSeq } ‘`’
    *
    */
  private[this] val ID = "(" + PLAINID + ")|" + "`" + """([\\f\\n\\r\\b\\t\\"\\'\\\\]|\\u+[0-9a-fA-F]{4}|[^\n`])+""" + "`"

  def id: Parser[String] = ID.r

}

