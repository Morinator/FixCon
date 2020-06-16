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


    private fun replaceWithOther(subgraph: Graph<V, DefaultEdge>, value: Int) {
        this.subgraph = subgraph.getCopy()
        this.value = value
    }

    /**@return True iff the other solution is better and therefore the current one is updated.
     */
    fun updateIfBetter(subgraph: Graph<V, DefaultEdge>, value: Int) = (value > this.value).also { if (it) replaceWithOther(subgraph, value) }

    fun updateIfBetter(other: Solution<V>) = updateIfBetter(other.subgraph, other.value)
}

/**A [Problem] consists of the [g] inside of which the optimal connected subgraph of size [k]
 * is searched. These subgraphs are evaluated by a [function], which may use [parameters]*/
data class Problem<V>(val g: VertexOrderedGraph<V>,
                      val k: Int,
                      val function: GraphFunction,
                      val parameters: List<Int> = emptyList()) {

    val globalOptimum: Int get() = function.globalOptimum(k)

    fun eval(graph: Graph<V, DefaultEdge>): Int = function.eval(graph, parameters)

    fun completeAdditionBound(graph: Graph<V, DefaultEdge>): Int = function.completeAdditionBound(graph, k, parameters)
}