package wanghg.rhigosis

import org.junit.Test
import org.junit.Assert._
import java.util.regex.Pattern

/**
  * Created by wanghg on 1/4/2017.
  */
class PatternTest {

  @Test
  def testPattern: Unit = {
    val regex = """[\p{Alnum}$_]*(_[~!@#%^&*-=+\|:<>/?]+)?"""
//    var regex = """^[\p{Alnum}$_]*(_[\x21\x23\x25\x26\x2a\x2b\x2d\x2f\x3a\x3c\x3d\x3e\x3f\x40\x5c\x5e\x7c\x7e]+)?$"""
    println(regex)
    //
    assertEquals(true, "abc".matches(regex))
    assertEquals(true, "abc_".matches(regex))
    assertEquals(true, "_abc".matches(regex))
    assertEquals(true, "_abc_".matches(regex))
    assertEquals(true, "abc_def".matches(regex))
    assertEquals(true, "_abc_def".matches(regex))
    assertEquals(true, "abc_def_".matches(regex))
    assertEquals(true, "abc_def_".matches(regex))
    //
    assertEquals(false, "!@#".matches(regex))
    assertEquals(false, "abc!@#".matches(regex))
    assertEquals(true, "_!@#".matches(regex))
    assertEquals(true, "abc_!@#".matches(regex))
    assertEquals(true, "_xx_abc_!@#".matches(regex))
    assertEquals(false, "_xx_abc_!@#_".matches(regex))
    //
    val ptn = Pattern.compile(regex)
    val mtr = ptn.matcher("_xx_abc_!@#")
    val mtr2 = ptn.matcher("_xx_abc_!@#")
    val mtr3 = ptn.matcher("_xx_abc_!@#")
    println(mtr.matches())
    println(mtr.end())
    println(mtr2.lookingAt())
    println(mtr2.end())
    println(mtr3.lookingAt())
    println(mtr3.end())
  }

}

