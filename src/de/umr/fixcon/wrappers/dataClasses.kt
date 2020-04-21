package de.umr.fixcon.wrappers

import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder
import com.google.common.graph.Graphs
import com.google.common.graph.MutableGraph
import de.umr.fixcon.graphFunctions.GraphFunction

data class Solution(var subgraph: Graph<Int> = GraphBuilder.undirected().build<Int>(), var value: Double = Double.NEGATIVE_INFINITY) {
    fun update(subgraph: Graph<Int>, value: Double) {
        this.subgraph = Graphs.copyOf(subgraph)
        this.value = value
    }
}


data class CFCO_Problem(val graph: MutableGraph<Int>, val subgraphSize: Int, val function: GraphFunction, val parameters: List<Int>)