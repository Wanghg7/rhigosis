package wanghg.rhigosis

/**
  * Created by wanghg on 1/4/2017.
  */
object ScalaParsersMain {
  def main(args: Array[String]): Unit = {
    val parsers = new ScalaParsers
    var rv = parsers.parseAll(parsers.idrest, "abc_123_@#!")
    println(rv.successful)
  }
}
