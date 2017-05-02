package wanghg.rhigosis

import java.io.File
import java.util.regex.Pattern

import org.jgrapht.{DirectedGraph, Graph}
import org.jgrapht.alg.{NaiveLcaFinder, TarjanLowestCommonAncestor}
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.{DefaultDirectedGraph, DefaultEdge}
import wanghg.Utils

import scala.collection.mutable

/**
  * Created by wanghg on 19/4/2017.
  */
object SGAnalyzer {

  val pLhs = Pattern.compile("^[a-zA-Z0-9_]+")

  def main(args: Array[String]): Unit = {
    val file = new File(args(0))
    require(file.exists())
    val rules = rulesFromGrammar(readFile(file)).map(r => {
      val mtr = pLhs.matcher(r)
      require(mtr.find())
      mtr.group() -> r
    }).toMap
    //
    val nont1 = "StableId"
    val nont2 = "SelfType"
    //
    val refs = findRefs(rules, Set(nont1, nont2), Set.empty[String], Set.empty[(String, String)])
    //
    val g: DirectedGraph[String, DefaultEdge] = new DefaultDirectedGraph[String, DefaultEdge](classOf[DefaultEdge])
    (refs.map(r => r._1) ++ refs.map(r => r._2)).foreach(v => {
      g.addVertex(v)
    })
    refs.foreach(ref => {
      g.addEdge(ref._2, ref._1)
    })
    val lcaFinder = new NaiveLcaFinder[String, DefaultEdge](g)
    val lca = lcaFinder.findLca(nont1, nont2)
    println(lca)
    val sp = new DijkstraShortestPath[String, DefaultEdge](g)
    println(sp.getPath(lca, nont1))
    println(sp.getPath(lca, nont2))
    //
  }

  def printRefs(refs: Set[(String, String)], nonts: Set[String]): Unit = {
    Utils.withWriter(new File("Def.temp/refs.gv")) { writer =>
      writer.write("digraph {\n")
      nonts.foreach(nont => {
        writer.write(String.format("\t%s [style=filled;fillcolor=red]\n", nont))
      })
      refs.foreach(r => {
        val (occur, nont) = r
        writer.write(String.format("\t%s -> %s\n", occur, nont))
      })
      writer.write("}\n")
    }
  }

  def findRefs(rules: Map[String, String], nonts: Set[String], done: Set[String], refs: Set[(String, String)]): Set[(String, String)] = {
    //    nonts.foreach(println)
    if (nonts.isEmpty)
      refs
    else {
      val refs2 = refs ++ findRefs(rules, nonts).filter(p => p._1 != p._2)
      //      refs2.foreach(println)
      val done2 = done ++ nonts
      val nonts2 = refs2.map(r => r._2) -- done2
      findRefs(rules, nonts2, done2, refs2)
    }
  }

  def findRefs(rules: Map[String, String], nonts: Set[String]): Set[(String, String)] = {
    val refs = mutable.Set.empty[(String, String)]
    rules.foreach(rule => {
      val (lhs, rhs) = rule
      val mOccur = Pattern.compile(nonts.mkString("\\W(", "|", ")\\W")).matcher(rhs)
      if (mOccur.find) {
        val occur = mOccur.group(1)
        refs += occur -> lhs
      }
    })
    refs.toSet
  }

  def rulesFromGrammar(grammar: String): List[String] = {
    val lbuf = mutable.ListBuffer.empty[String]
    val mtr = Pattern.compile("[a-zA-Z0-9_]+ *::=[^;]+;").matcher(grammar)
    while (mtr.find()) {
      lbuf += mtr.group(0)
    }
    lbuf.toList
  }

  def readFile(file: File): String = {
    val sb = new java.lang.StringBuilder
    val buf = new Array[Char](1024)
    Utils.withReader(file) { reader =>
      var n = 0
      while (n != -1) {
        sb.append(buf, 0, n)
        n = reader.read(buf)
      }
    }
    sb.toString
  }

}

