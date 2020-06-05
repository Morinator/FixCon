package de.umr.fixcon.wrappers


import de.umr.core.GraphAlgorithms.copyIntGraph
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

/**A Solution for a CFCO (Connected-Fixed-Cardinality-Optimization) Problem consists of the [subgraph] for which the
 * so far best [value] was returned and the [value] itself.*/
data class Solution(var subgraph: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java), var value: Int = Int.MIN_VALUE) {

    private fun update(subgraph: Graph<Int, DefaultEdge>, value: Int) {
        this.subgraph = copyIntGraph(subgraph)
        this.value = value
    }

    fun updateIfBetter(subgraph: Graph<Int, DefaultEdge>, value: Int) {
        if (value > this.value) update(subgraph, value)
    }

}

/**A [CFCO_Problem] consists of the [originalGraph] inside of which the optimal connected subgraph of size [targetSize]
 * is searched. These subgraphs are evaluated by a [function], which may use [parameters]*/
data class CFCO_Problem(val originalGraph: VertexOrderedGraph<Int>,
                        val targetSize: Int,
                        val function: GraphFunction,
                        val parameters: List<Int>)