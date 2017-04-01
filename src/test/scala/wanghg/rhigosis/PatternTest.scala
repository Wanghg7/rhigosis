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
    //    val regex = """([\p{Alnum}$_]*_[~!@#%^&*-=+\|:<>/?]+)|([\p{Alnum}$_]*)"""
    //    val regex = """([\p{Alnum}$_]*)"""
    //    val regex = """([\p{Alnum}$_]*)_([~!@#%^&*-=+\|:<>/?]+)"""
    val regex =
    """[~!@#%^&*=+\|:<>/?-]+"""
    println(regex)
    //
    val ptn = Pattern.compile(regex)
    val mtr = ptn.matcher("_xx_abc_!@#")
    val mtr2 = ptn.matcher("_xx_abc_!@#")
    val mtr3 = ptn.matcher("abc_123#@!&^%")
    val mtr4 = ptn.matcher("123#@!&^%")
    println(mtr.matches())
    //    println(mtr.end())
    //
    println(mtr2.lookingAt())
    //    println(mtr2.end())
    //
    println(mtr3.lookingAt())
    //    println(mtr3.end())
    //    println(mtr3.group(1))
    //    println(mtr3.group(2))
    //
    println(mtr4.lookingAt())
//    println(mtr4.end())
//    println(mtr4.group(0))
  }

}

