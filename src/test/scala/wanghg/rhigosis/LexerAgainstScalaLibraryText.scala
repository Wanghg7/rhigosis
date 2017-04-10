package wanghg.rhigosis

import java.io.File

import org.junit.Test
import org.junit.Assert._
import wanghg.Utils

/**
  * Created by wanghg on 9/4/2017.
  */
class LexerAgainstScalaLibraryText {

  val ROOT = new File("/Users/wanghg/temp/scala-2.12.x/src/library")

  require(ROOT.exists())

  def foreach(lexer: Lexer): Unit =
    if (lexer.next_token().sym == Sym.EOF) ()
    else foreach(lexer)

  @Test
  def doTest(): Unit = {
    val it = Utils.files(List(Iterator(ROOT)), "scala")
    for (file <- it) {
      Utils.withReader(file) { reader =>
        val lexer = new Lexer(reader, file.getAbsolutePath)
        foreach(lexer)
      }
    }
  }

}

