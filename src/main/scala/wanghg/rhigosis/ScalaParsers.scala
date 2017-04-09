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
  private[this] val ID =
    "(" + PLAINID + ")|" + "`" + """([\\f\\n\\r\\b\\t\\"\\'\\\\]|\\u+[0-9a-fA-F]{4}|[^\n`])+""" + "`"

  def id: Parser[String] = ID.r

  /**
    * abstract    case        catch       class       def
    * do          else        extends     false       final
    * finally     for         forSome     if          implicit
    * import      lazy        macro       match       new
    * null        object      override    package     private
    * protected   return      sealed      super       this
    * throw       trait       try         true        type
    * val         var         while       with        yield
    * _    :    =    =>    <-    <:    <%     >:    #    @
    *
    * The Unicode operators \u21D2 ‘⇒’ and \u2190 ‘←’, which have the ASCII equivalents => and <-, are also reserved.
    *
    */
  private[this] val RESERVED =
    "abstract|case|catch|class|def|" +
      "do|else|extends|false|final|" +
      "finally|for|forSome|if|implicit|" +
      "import|lazy|macro|match|new|" +
      "null|object|override|package|private|" +
      "protected|return|sealed|super|this|" +
      "throw|trait|try|true|type|" +
      "val|var|while|with|yield|" +
      "_|=>|=|<-|<:|<%|>:|#|@|:|" +
      "\u21d2|\u2190"

  def reserved: Parser[String] = RESERVED.r

  def semi: Parser[String] = """;|\n+""".r

  def CompilationUnit: Parser[Unit] =
    opt("package" ~ id ~ rep("." ~ id) ~ semi) ^^ (_ => ())

}

