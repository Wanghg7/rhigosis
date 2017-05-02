package wanghg.examples.jgrapht

import org.jgrapht.Graph
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.{DefaultDirectedGraph, DefaultEdge, ListenableDirectedGraph}
import org.junit.Test
import org.junit.Assert._

/**
  * Created by wanghg on 1/5/2017.
  */
class ShortestPathTest {

  @Test
  def testGraph(): Unit = {
    val g: Graph[String, DefaultEdge] = new DefaultDirectedGraph[String, DefaultEdge](classOf[DefaultEdge])
    g.addVertex("a")
    g.addVertex("b")
    g.addEdge("a", "b")
    g.addEdge("b", "a")
    val alg = new DijkstraShortestPath[String, DefaultEdge](g)
    val path = alg.getPath("b", "a")
    println(path)
  }

}
