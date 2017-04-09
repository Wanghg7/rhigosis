package wanghg.examples.jflex

import java.io.Reader

import scala.collection.mutable.ListBuffer

/**
  * Created by wanghg on 9/4/2017.
  */
object SuppFinder {

  def find(reader: Reader): Iterator[String] = {

    val lexerWrapper = new Traversable[String] {

      override def foreach[U](f: (String) => U): Unit = foreach(new SuppLexer(reader), f)

      def foreach[U](lexer: SuppLexer, f: (String) => U): Unit = {
        lexer.next() match {
          case null => ()
          case next => f(next); foreach(lexer, f)
        }
      }
    }

    lexerWrapper.filter(_.length > 1).toIterator
  }

}

