package wanghg.rhigosis

import java.io.File
import java.util.regex.Pattern

import wanghg.Utils

import scala.collection.mutable

/**
  * Created by wanghg on 2/5/2017.
  */
object ScalaParser {

  def main(args: Array[String]): Unit = {
    val src = new File(args(0))
    require(src.isDirectory)
    val files = Utils.files(List(Iterator(src)), "scala")
    parse(files, 1)
  }

  def parse(files: Stream[File], idx: Int): Unit = {
    files match {
      case Stream.Empty => ()
      case file #:: rest =>
        println("================================================================================")
        printf("%3d: %s\n", idx, file)
        println("--------------------------------------------------------------------------------")
        val source = Utils.readFile(file)
        println(source)
        println("--------------------------------------------------------------------------------")
        parseCompilationUnit(source, 0)
        parse(rest, idx + 1)
    }
  }

  def parseCompilationUnit(source: String, offset: Int): Unit = ???

}

