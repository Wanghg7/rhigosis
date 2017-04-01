package wanghg.rhigosis

import org.junit.Test
import org.junit.Assert._

/**
  * Created by wanghg on 1/4/2017.
  */
class ScalaParsersTest {

  @Test
  def test_op(): Unit = {
    val parsers = new ScalaParsers
    assertEquals(false, parsers.parseAll(parsers.op, "$#@!").successful)
    assertEquals(true, parsers.parseAll(parsers.op, "#@!").successful)
    assertEquals(false, parsers.parseAll(parsers.op, "a#@!").successful)
    assertEquals(true, parsers.parseAll(parsers.op, "#@!&^%").successful)
  }

  @Test
  def test_idrest(): Unit = {
    val parsers = new ScalaParsers
    assertEquals(true, parsers.parseAll(parsers.idrest, "abc").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "abc_123").successful)
    assertEquals(false, parsers.parseAll(parsers.idrest, "#@!&^%").successful)
    assertEquals(false, parsers.parseAll(parsers.idrest, "abc_123#@!&^%").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "abc_123_@").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "abc_123_#@!&^%").successful)
  }

}
