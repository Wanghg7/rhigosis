package wanghg.rhigosis

import scala.collection.mutable.ListBuffer


/**
  * Created by wanghg on 16/4/2017.
  */
object SGUtils {

  val ptn = "‘([^’]+)’".r

  def id(s: String): Term = s match {
    case "id" => Terminal('ID)
    case "semi" => Terminal('SEMI)
    case "nl" => Terminal('NL)
    case "integerLiteral" => Terminal('INTEGER)
    case "floatingPointLiteral" => Terminal('FLOAT)
    case "booleanLiteral" => Terminal('BOOLEAN)
    case "characterLiteral" => Terminal('CHARACTER)
    case "stringLiteral" => Terminal('STRING)
    case "symbolLiteral" => Terminal('SYMBOL)
    case _ => Nonterminal(Symbol(s))
  }

  def grammar(s: ListBuffer[Production]) = Grammar(s.toList)

  def production(name: String, rhs: Rhs) = Production(Nonterminal(Symbol(name)), rhs)

  def rhs(options: Alternation) = Rhs(options)

  def listBuffer[T](e: T): ListBuffer[T] = ListBuffer(e)

  def listBuffer[T](es: ListBuffer[T], e: T): ListBuffer[T] = es += e

  def alternation(s: ListBuffer[Concatenation]): Alternation = Alternation(s.toList)

  def concatenation(s: ListBuffer[Term]): Concatenation = Concatenation(s.toList)

  def concatenation(): Concatenation = Concatenation(List.empty[Term])

  def grouping(t: Alternation): Grouping = Grouping(t)

  def optional(t: Alternation): Optional = Optional(t)

  def repetition(t: Alternation): Repetition = Repetition(t)

  def literal(s: String): Terminal = s match {
    case ptn("-") => Terminal('MINUS)
    case ptn("null") => Terminal('NULL)
    case ptn(".") => Terminal('DOT)
    case ptn(",") => Terminal('COMMA)
    case ptn("this") => Terminal('THIS)
    case ptn("super") => Terminal('SUPER)
    case ptn("[") => Terminal('LBRACK)
    case ptn("]") => Terminal('RBRACK)
    case ptn("(") => Terminal('LPAREN)
    case ptn(")") => Terminal('RPAREN)
    case ptn("{") => Terminal('LBRACE)
    case ptn("}") => Terminal('RBRACE)
    case ptn("=>") => Terminal('RDARROW)
    case ptn("forSome") => Terminal('FORSOME)
    case ptn("type") => Terminal('TYPE)
    case ptn("val") => Terminal('VAL)
    case ptn("var") => Terminal('VAR)
    case ptn("with") => Terminal('WITH)
    case ptn("#") => Terminal('HASH)
    case ptn(":") => Terminal('COLON)
    case ptn("_") => Terminal('UNDERSCORE)
    case ptn("*") => Terminal('ASTERISK)
    case ptn("implicit") => Terminal('IMPLICIT)
    case ptn("if") => Terminal('IF)
    case ptn("else") => Terminal('ELSE)
    case ptn("while") => Terminal('WHILE)
    case ptn("try") => Terminal('TRY)
    case ptn("catch") => Terminal('CATCH)
    case ptn("finally") => Terminal('FINALLY)
    case ptn("do") => Terminal('DO)
    case ptn("for") => Terminal('FOR)
    case ptn("yield") => Terminal('YIELD)
    case ptn("throw") => Terminal('THROW)
    case ptn("return") => Terminal('RETURN)
    case ptn("=") => Terminal('EQ)
    case ptn("match") => Terminal('MATCH)
    case ptn("+") => Terminal('PLUS)
    case ptn("~") => Terminal('TILDE)
    case ptn("!") => Terminal('EXCLAM)
    case ptn("new") => Terminal('NEW)
    case ptn("lazy") => Terminal('LAZY)
    case ptn("<-") => Terminal('LARROW)
    case ptn("case") => Terminal('CASE)
    case ptn("|") => Terminal('OR)
    case ptn("@") => Terminal('AT)
    case ptn(">:") => Terminal('LBOUNDS)
    case ptn("<:") => Terminal('UBOUNDS)
    case ptn("<%") => Terminal('VBOUNDS)
    case ptn("override") => Terminal('OVERRIDE)
    case ptn("abstract") => Terminal('ABSTRACT)
    case ptn("final") => Terminal('FINAL)
    case ptn("sealed") => Terminal('SEALED)
    case ptn("private") => Terminal('PRIVATE)
    case ptn("protected") => Terminal('PROTECTED)
    case ptn("import") => Terminal('IMPORT)
    case ptn("def") => Terminal('DEF)
    case ptn("class") => Terminal('CLASS)
    case ptn("object") => Terminal('OBJECT)
    case ptn("trait") => Terminal('TRAIT)
    case ptn("extends") => Terminal('EXTENDS)
    case ptn("package") => Terminal('PACKAGE)
  }


}
