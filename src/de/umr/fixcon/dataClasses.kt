package de.umr.fixcon

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.dataStructures.copy
import de.umr.core.dataStructures.vertexCount
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph

/**A Solution for a CFCO (Connected-Fixed-Cardinality-Optimization) Problem consists of the [subgraph] for which the
 * so far best [value] was returned and the [value] itself.*/
class Solution<V>(var subgraph: Graph<V, DefaultEdge> = SimpleWeightedGraph(DefaultEdge::class.java), var value: Int = Int.MIN_VALUE) {

    override fun toString() = "Solution: size=${subgraph.vertexCount},  value=$value,  subgraph=$subgraph"

    fun copy() = Solution(subgraph.copy(), value)

    private fun copyFromOther(other: Graph<V, DefaultEdge>, value: Int) {
        subgraph = other.copy()
        this.value = value
    }

    /**@return True iff the other solution is better and therefore the current one is updated.
     */
    fun updateIfBetter(g: Graph<V, DefaultEdge>, value: Int) = (value > this.value).also { if (it) copyFromOther(g, value) }

    fun updateIfBetter(sol: Solution<V>) = updateIfBetter(sol.subgraph, sol.value)
}

data class Problem<V>(val g: VertexOrderedGraph<V>, val function: AbstractGraphFunction) {
    fun eval(graph: Graph<V, DefaultEdge>): Int = function.eval(graph)

    fun completeAdditionBound(graph: Graph<V, DefaultEdge>): Int = function.completeAdditionBound(graph)

    fun verticesByDegree() = g.vertexSet().associateWith { g.degreeOf(it) }
}