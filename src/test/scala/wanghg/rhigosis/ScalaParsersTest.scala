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
    assertEquals(true, parsers.parseAll(parsers.idrest, "").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "abc").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "_abc").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "_abc__").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "abc_123").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "__abc_123").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "abc_123__").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "_abc_123__").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "_#@!&^%").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "abc_123_@").successful)
    assertEquals(true, parsers.parseAll(parsers.idrest, "abc_123_#@!&^%").successful)
    //
    assertEquals(false, parsers.parseAll(parsers.idrest, "#@!&^%").successful)
    assertEquals(false, parsers.parseAll(parsers.idrest, "abc#@!&^%").successful)
    assertEquals(false, parsers.parseAll(parsers.idrest, "abc_123#@!&^%").successful)
  }

  @Test
  def test_varid(): Unit = {
    val parsers = new ScalaParsers
    assertEquals(true, parsers.parseAll(parsers.varid, "abc").successful)
    assertEquals(true, parsers.parseAll(parsers.varid, "abc_123").successful)
    assertEquals(true, parsers.parseAll(parsers.varid, "abc_123__").successful)
    assertEquals(true, parsers.parseAll(parsers.varid, "abc_123_@").successful)
    assertEquals(true, parsers.parseAll(parsers.varid, "abc_123_#@!&^%").successful)
    //
    assertEquals(false, parsers.parseAll(parsers.varid, "").successful)
    assertEquals(false, parsers.parseAll(parsers.varid, "$abc").successful)
    assertEquals(false, parsers.parseAll(parsers.varid, "_abc").successful)
    assertEquals(false, parsers.parseAll(parsers.varid, "_abc__").successful)
    assertEquals(false, parsers.parseAll(parsers.varid, "__abc_123").successful)
    assertEquals(false, parsers.parseAll(parsers.varid, "_abc_123__").successful)
    assertEquals(false, parsers.parseAll(parsers.varid, "_#@!&^%").successful)
    assertEquals(false, parsers.parseAll(parsers.varid, "#@!&^%").successful)
    assertEquals(false, parsers.parseAll(parsers.varid, "abc#@!&^%").successful)
    assertEquals(false, parsers.parseAll(parsers.varid, "abc_123#@!&^%").successful)
  }

  @Test
  def test_boundvarid: Unit = {
    val parsers = new ScalaParsers
    assertEquals(true, parsers.parseAll(parsers.boundvarid, "abc").successful)
    assertEquals(true, parsers.parseAll(parsers.boundvarid, "abc_123").successful)
    assertEquals(true, parsers.parseAll(parsers.boundvarid, "abc_123__").successful)
    assertEquals(true, parsers.parseAll(parsers.boundvarid, "abc_123_@").successful)
    assertEquals(true, parsers.parseAll(parsers.boundvarid, "abc_123_#@!&^%").successful)
    //
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "$abc").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "_abc").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "_abc__").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "__abc_123").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "_abc_123__").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "_#@!&^%").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "#@!&^%").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "abc#@!&^%").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "abc_123#@!&^%").successful)
    // --
    assertEquals(true, parsers.parseAll(parsers.boundvarid, "`abc`").successful)
    assertEquals(true, parsers.parseAll(parsers.boundvarid, "`abc_123`").successful)
    assertEquals(true, parsers.parseAll(parsers.boundvarid, "`abc_123__`").successful)
    assertEquals(true, parsers.parseAll(parsers.boundvarid, "`abc_123_@`").successful)
    assertEquals(true, parsers.parseAll(parsers.boundvarid, "`abc_123_#@!&^%`").successful)
    //
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "``").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "`$abc`").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "`_abc`").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "`_abc__`").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "`__abc_123`").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "`_abc_123__`").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "`_#@!&^%`").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "`#@!&^%`").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "`abc#@!&^%`").successful)
    assertEquals(false, parsers.parseAll(parsers.boundvarid, "`abc_123#@!&^%`").successful)
  }

  @Test
  def test_plainid: Unit = {
    val parsers = new ScalaParsers
    assertEquals(true, parsers.parseAll(parsers.plainid, "abc").successful)
    assertEquals(true, parsers.parseAll(parsers.plainid, "abc_123").successful)
    assertEquals(true, parsers.parseAll(parsers.plainid, "abc_123_#@!").successful)
    assertEquals(true, parsers.parseAll(parsers.plainid, "Abc").successful)
    assertEquals(true, parsers.parseAll(parsers.plainid, "Abc_123").successful)
    assertEquals(true, parsers.parseAll(parsers.plainid, "Abc_123_#@!").successful)
    assertEquals(true, parsers.parseAll(parsers.plainid, "_abc").successful)
    assertEquals(true, parsers.parseAll(parsers.plainid, "$abc_123").successful)
    assertEquals(true, parsers.parseAll(parsers.plainid, "$abc_123_#@!").successful)
    assertEquals(true, parsers.parseAll(parsers.plainid, "@").successful)
    assertEquals(true, parsers.parseAll(parsers.plainid, "#@!").successful)
    //
    assertEquals(false, parsers.parseAll(parsers.plainid, "abc#@!").successful)
    assertEquals(false, parsers.parseAll(parsers.plainid, "_#@!").successful)
    assertEquals(false, parsers.parseAll(parsers.plainid, "$#@!").successful)
    assertEquals(false, parsers.parseAll(parsers.plainid, "#@!a").successful)
    assertEquals(false, parsers.parseAll(parsers.plainid, "#@!_").successful)
    assertEquals(false, parsers.parseAll(parsers.plainid, "#@!$").successful)
  }

  @Test
  def test_id: Unit = {
    val parsers = new ScalaParsers
    assertEquals(true, parsers.parseAll(parsers.id, "abc").successful)
    assertEquals(true, parsers.parseAll(parsers.id, "abc_123").successful)
    assertEquals(true, parsers.parseAll(parsers.id, "abc_123_#@!").successful)
    assertEquals(true, parsers.parseAll(parsers.id, "Abc").successful)
    assertEquals(true, parsers.parseAll(parsers.id, "Abc_123").successful)
    assertEquals(true, parsers.parseAll(parsers.id, "Abc_123_#@!").successful)
    assertEquals(true, parsers.parseAll(parsers.id, "_abc").successful)
    assertEquals(true, parsers.parseAll(parsers.id, "$abc_123").successful)
    assertEquals(true, parsers.parseAll(parsers.id, "$abc_123_#@!").successful)
    assertEquals(true, parsers.parseAll(parsers.id, "@").successful)
    assertEquals(true, parsers.parseAll(parsers.id, "#@!").successful)
    //
    assertEquals(false, parsers.parseAll(parsers.id, "abc#@!").successful)
    assertEquals(false, parsers.parseAll(parsers.id, "_#@!").successful)
    assertEquals(false, parsers.parseAll(parsers.id, "$#@!").successful)
    assertEquals(false, parsers.parseAll(parsers.id, "#@!a").successful)
    assertEquals(false, parsers.parseAll(parsers.id, "#@!_").successful)
    assertEquals(false, parsers.parseAll(parsers.id, "#@!$").successful)
    // --
    assertEquals(true, parsers.parseAll(parsers.id, """`\\f\\n\\r\\b\\t`""").successful)
    assertEquals(true, parsers.parseAll(parsers.id, """`\\uu0020`""").successful)
    assertEquals(true, parsers.parseAll(parsers.id, """`ghost🌞love🍎`""").successful)
    //
    assertEquals(false, parsers.parseAll(parsers.id, "`this\nthat`").successful)
    assertEquals(false, parsers.parseAll(parsers.id, "`this`that`").successful)
  }

  @Test
  def test_reserved: Unit = {
    val parsers = new ScalaParsers
    assertEquals(true, parsers.parseAll(parsers.reserved, "abstract").successful)
    assertEquals(true, parsers.parseAll(parsers.reserved, "do").successful)
    assertEquals(true, parsers.parseAll(parsers.reserved, "_").successful)
    assertEquals(true, parsers.parseAll(parsers.reserved, ">:").successful)
    assertEquals(true, parsers.parseAll(parsers.reserved, "<:").successful)
    assertEquals(true, parsers.parseAll(parsers.reserved, "<-").successful)
    assertEquals(true, parsers.parseAll(parsers.reserved, "=>").successful)
    assertEquals(true, parsers.parseAll(parsers.reserved, "⇒").successful)
    assertEquals(true, parsers.parseAll(parsers.reserved, "←").successful)
    //
    assertEquals(false, parsers.parseAll(parsers.reserved, "fork").successful)
  }

}

