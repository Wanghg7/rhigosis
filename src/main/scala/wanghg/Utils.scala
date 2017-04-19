package wanghg

import java.io._

/**
  * Created by wanghg on 9/4/2017.
  */
object Utils {

  def files(itList: List[Iterator[File]], ext: String): Stream[File] = itList match {
    case Nil => Stream.empty[File]
    case (it :: rest) if !it.hasNext => files(rest, ext)
    case (it :: _) =>
      val file = it.next()
      if (file.isDirectory) {
        files(file.listFiles().toIterator :: itList, ext)
      } else if (!file.getName.endsWith("." + ext)) {
        files(itList, ext)
      } else {
        file #:: files(itList, ext)
      }
  }

  def withReader[T](file: File)(op: Reader => T): T = {
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

  def withWriter[T](file: File)(op: Writer => T): T = {
    val in = new FileOutputStream(file)
    try {
      val writer = new OutputStreamWriter(in)
      try {
        op(writer)
      } finally {
        writer.close()
      }
    } finally {
      in.close()
    }
  }

}
