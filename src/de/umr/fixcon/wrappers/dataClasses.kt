package de.umr.fixcon.wrappers

import de.umr.core.getCopy
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph

/**A Solution for a CFCO (Connected-Fixed-Cardinality-Optimization) Problem consists of the [subgraph] for which the
 * so far best [value] was returned and the [value] itself.*/
data class Solution(var subgraph: Graph<Int, DefaultEdge> = SimpleWeightedGraph(DefaultEdge::class.java), var value: Int = Int.MIN_VALUE) {

    private fun update(subgraph: Graph<Int, DefaultEdge>, value: Int) {
        this.subgraph = subgraph.getCopy()
        this.value = value
    }

    /**@return True iff the other solution is better and therefore the current one is updated.
     */
    fun updateIfBetter(subgraph: Graph<Int, DefaultEdge>, value: Int) = (value > this.value).also { if (it) update(subgraph, value) }
}

/**A [CFCO_Problem] consists of the [originalGraph] inside of which the optimal connected subgraph of size [targetSize]
 * is searched. These subgraphs are evaluated by a [function], which may use [parameters]*/
data class CFCO_Problem(val originalGraph: VertexOrderedGraph<Int>,
                        val targetSize: Int,
                        val function: GraphFunction,
                        val parameters: List<Int>)