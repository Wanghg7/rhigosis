package wanghg.rhigosis

import org.junit.Test
import org.junit.Assert._

/**
  * Created by wanghg on 2/4/2017.
  */
class UnicodeOperatorsTest {

  /**
    * \u21D2 ‘⇒’
    * \u2190 ‘←’
    */
  @Test
  def test: Unit = {
    val fn = (x: Int) => x * x
    assertEquals(49, fn(7))
    assertEquals(List(1, 4, 9, 16), for (x <- 1 until 5) yield fn(x))
    //
    val fn2 = (x: Int) ⇒ x * x
    assertEquals(49, fn2(7))
    assertEquals(List(1, 4, 9, 16), for (x ← 1 until 5) yield fn(x))
  }

}
