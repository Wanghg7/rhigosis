package wanghg.rhigosis

import java.io.File
import java_cup.runtime.{ComplexSymbolFactory, SymbolFactory}

import wanghg.Utils

/**
  * Created by wanghg on 14/4/2017.
  */
object SGTrans {
  def main(args: Array[String]): Unit = {
    val file = new File(args(0))
    require(file.exists())
    val fact = new ComplexSymbolFactory();
    Utils.withReader(file) { reader =>
      val lexer = new SGLexer(reader, file.getPath, fact)
      val parser = new SGParser(lexer, fact)
      parser.parse()
    }
  }
}
