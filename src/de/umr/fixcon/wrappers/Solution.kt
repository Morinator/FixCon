package de.umr.fixcon.wrappers

import com.google.common.base.Preconditions
import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder
import com.google.common.graph.Graphs

/**
 * This class is a wrapper for a solution. It contains:
 * 1. The graph in which is searched for a subgraph that maximizes the target function
 * 2. The subgraph, identified by its set of vertices, that is optimal.
 * 3. The value of the target function for this specific subgraph.
 */
data class Solution(var subgraph: Graph<Int> = GraphBuilder.undirected().build<Int>(), var value: Double = Double.NEGATIVE_INFINITY) {

    fun update(subgraph: Graph<Int>, value: Double) {
        this.subgraph = Graphs.copyOf(subgraph)
        this.value = value
    }
}