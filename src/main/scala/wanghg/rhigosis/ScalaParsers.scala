package wanghg.rhigosis

import scala.util.parsing.combinator.RegexParsers

/**
  * Created by wanghg on 1/4/2017.
  */
class ScalaParsers extends RegexParsers {

  /**
    * op       ::=  opchar {opchar}
    *
    * {{{
    * 21  ! 23  # 25  % 26  & 2a  * 2b  +
    * 2d  - 2f  / 3a  : 3c  < 3d  = 3e  >
    * 3f  ? 40  @ 5c  \ 5e  ^ 7c  | 7e  ~
    * }}}
    *
    * TODO: mathematical symbols (Sm)
    * TODO: other symbols (So).
    */
  private[this] val OP =
    """[\x21\x23\x25\x26\x2a\x2b\x2d\x2f\x3a\x3c\x3d\x3e\x3f\x40\x5c\x5e\x7c\x7e]+"""

  def op: Parser[String] = OP.r

  /**
    * idrest   ::=  {letter | digit} [‘_’ op]
    *
    */
  private[this] val IDREST =
    """[\p{Alnum}$_]*(_""" + OP +""")?"""
  //= """[\p{Alnum}$_]*(_[~!@#%^&*-=+\|:<>/?]+)?"""

  def idrest: Parser[String] = {
    println()
    """[\p{Alnum}$_]*(_[~!@#%^&*-=+\|:<>/?]+)?""".r
  }

  /**
    * varid    ::=  lower idrest
    *
    */
  private[this] val VARID =
    """\p{Lower}""" + IDREST

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
    """\p{Upper}""" + IDREST + "|" + VARID + "|" + OP

  def plainid: Parser[String] = PLAINID.r

  /**
    * id       ::=  plainid |  ‘`’ { charNoBackQuoteOrNewline | UnicodeEscape | charEscapeSeq } ‘`’
    *
    */
  private[this] val ID = PLAINID + "|" + "`" + """([\f\n\r\b\t\"\'\\]|\\u+[0-9a-fA-F]{4}|[^\n`])+""" + "`"

  def id: Parser[String] = ID.r

}

