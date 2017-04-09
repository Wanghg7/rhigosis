package wanghg

import java.io.{File, FileInputStream, InputStreamReader, Reader}

/**
  * Created by wanghg on 9/4/2017.
  */
object Utils {

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

}
