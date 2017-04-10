package wanghg.rhigosis

import java.io.File

import org.junit.Test
import wanghg.Utils

/**
  * Created by wanghg on 9/4/2017.
  */
class ParserAgainstScalaLibraryTest {

  val ROOT = new File("/Users/wanghg/temp/scala-2.12.x/src/library")

  require(ROOT.exists())

  @Test
  def doTest(): Unit = {
    val it = Utils.files(List(Iterator(ROOT)), "scala")
    for (file <- it) {
      Utils.withReader(file) { reader =>
        val lexer = new Lexer(reader, file.getAbsolutePath)
        val parser = new Parser(lexer)
        parser.debug_parse()
      }
    }
  }

}

