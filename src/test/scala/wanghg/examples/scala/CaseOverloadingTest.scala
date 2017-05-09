package wanghg.examples.scala

import org.junit.Test
import org.junit.Assert._

/**
  * Created by wanghg on 2/5/2017.
  */
class CaseOverloadingTest {

  type x[a, b] = Tuple2[a, b]

  @Test
  def doTest: Unit = {
    var v: String x Int = "a" -> 1
    println(v)

  }

}

class Ref[T]

abstract class Outer {
  type T
}

abstract class ATest {
  val x: Ref[x_type # T] forSome { type x_type <: Outer with Singleton }
}

case class Path(i: Int, s: String) {
  def this(i: Int) = this(i, null)
}

object Path {
  def apply(i: Int) = new Path(i)

}

