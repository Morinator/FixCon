package de.umr.core.dataStructures

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph

class Solution<V>(var subgraph: Graph<V, DefaultEdge> = SimpleWeightedGraph(DefaultEdge::class.java), var value: Int = Int.MIN_VALUE) {

    override fun toString() = "Solution: size=${subgraph.vCount},  value=$value,  subgraph=${subgraph.vertexSet()}"

}