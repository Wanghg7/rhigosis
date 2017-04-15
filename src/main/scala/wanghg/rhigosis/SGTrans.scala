package wanghg.rhigosis

import java.io.{File, StringReader}
import java_cup.runtime.{ComplexSymbolFactory, SymbolFactory}

import wanghg.Utils

/**
  * Created by wanghg on 14/4/2017.
  */
object SGTrans {

  val fact = new ComplexSymbolFactory();

  def main(args: Array[String]): Unit = {
    val file = new File(args(0))
    require(file.exists())
    Utils.withReader(file) { reader =>
      val lexer = new SGLexer(reader, file.getPath, fact)
      val parser = new SGInputParser(lexer, fact)
      val transformed = transform(parser.parse().value.asInstanceOf[String])
      val out = output(transformed)
      print(out)
    }
  }

  def transform(in: String): String = {
    in
  }

  def output(in: String): String = {
    val lexer = new SGLexer(new StringReader(in), "", fact)
    val parser = new SGOutputParser(lexer, fact)
    val out = parser.parse().value.asInstanceOf[String]
    val sb = new StringBuilder
    sb.append("package wanghg.rhigosis;\n\n")
    sb.append(out)
    sb.toString
  }

}

