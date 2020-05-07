package de.umr.fixcon.wrappers


import de.umr.core.GraphAlgorithms.copyIntGraph
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

data class Solution(var subgraph: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java), var value: Double = Double.NEGATIVE_INFINITY) {
    fun update(subgraph: Graph<Int, DefaultEdge>, value: Double) {
        this.subgraph = copyIntGraph(subgraph)
        this.value = value
    }
}

data class CFCO_Problem(val graph: Graph<Int, DefaultEdge>, val subgraphSize: Int, val function: GraphFunction, val parameters: List<Int>)