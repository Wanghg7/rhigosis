package wanghg.rhigosis

import java.io.File

/**
  * Created by wanghg on 9/4/2017.
  */
object Main {

  val ROOT = new File("/Users/wanghg/temp/scala-2.12.x/src/library")

  def main(args: Array[String]): Unit = {
    require(ROOT.exists())
    val it = files(List(Iterator(ROOT)))
    var count = 0
    for (file <- it) {
      println(file)
      count += 1
    }
    println(count)
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
