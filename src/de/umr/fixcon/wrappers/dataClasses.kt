package de.umr.fixcon.wrappers

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.getCopy
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph

/**A Solution for a CFCO (Connected-Fixed-Cardinality-Optimization) Problem consists of the [subgraph] for which the
 * so far best [value] was returned and the [value] itself.*/
data class Solution<V>(var subgraph: Graph<V, DefaultEdge> = SimpleWeightedGraph(DefaultEdge::class.java), var value: Int = Int.MIN_VALUE) {

    private fun replaceWithOther(other: Graph<V, DefaultEdge>, value: Int) {
        subgraph = other.getCopy()
        this.value = value
    }

    /**@return True iff the other solution is better and therefore the current one is updated.
     */
    fun updateIfBetter(subgraph: Graph<V, DefaultEdge>, value: Int) = (value > this.value).also { if (it) replaceWithOther(subgraph, value) }
}

data class Problem<V>(val g: VertexOrderedGraph<V>, val function: GraphFunction) {
    val globalOptimum: Int get() = function.globalOptimum()

    fun eval(graph: Graph<V, DefaultEdge>): Int = function.eval(graph)

    fun completeAdditionBound(graph: Graph<V, DefaultEdge>): Int = function.completeAdditionBound(graph)
}