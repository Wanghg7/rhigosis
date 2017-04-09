package wanghg.rhigosis

import java.io._

/**
  * Created by wanghg on 9/4/2017.
  */
object Main {

  val ROOT = new File("/Users/wanghg/temp/scala-2.12.x/src/library")

  def main(args: Array[String]): Unit = {
    require(ROOT.exists())
    val parsers = new ScalaParsers
    val it = files(List(Iterator(ROOT)))
    for (file <- it) {
      println(file)
      withReader(file) { reader =>
        parsers.parseAll(parsers.CompilationUnit, reader)
      }
    }
  }

  def withReader(file: File)(op: Reader => Unit): Unit = {
    val in = new FileInputStream(file)
    try {
      val reader = new InputStreamReader(in)
      try {
        op(reader)
      } finally {
        reader.close()
      }
    } finally {
      in.close()
    }
  }

  def files(itList: List[Iterator[File]]): Stream[File] = itList match {
    case Nil => Stream.empty[File]
    case (it :: rest) if !it.hasNext => files(rest)
    case (it :: _) =>
      val file = it.next()
      if (file.isDirectory) {
        files(file.listFiles().toIterator :: itList)
      } else if (!file.getName.endsWith(".scala")) {
        files(itList)
      } else {
        file #:: files(itList)
      }
  }

}
