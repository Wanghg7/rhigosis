package wanghg.examples.jflex

import java.io.StringReader

import org.junit.Assert._
import org.junit.Test

/**
  * Created by wanghg on 9/4/2017.
  */
class SuppFinderTest {

  @Test
  def testSuppFinder(): Unit = {
    val reader = new StringReader(
      """
        |I love 🍎
        |I love 🌞
        |I love 🍷
      """.stripMargin)
    val it = SuppFinder.find(reader)
    assertEquals("🍎", it.next)
    assertEquals("🌞", it.next)
    assertEquals("🍷", it.next)
    assertEquals(false, it.hasNext)
  }

}

