package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**
 * This function counts the number of edges in a given graph.
 */
object EdgeCountFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = true

    /**corresponds to the arithmetic series: subgraph.size + subgraph.size+1 + ... + targetSize-1*/
    override fun additionBound(subgraph: VertexOrderedGraph<Int>, targetSize: Int, args: List<Int>): Int =
            (targetSize - subgraph.size) * (subgraph.size + (targetSize - subgraph.size-1)/2)

    override fun eval(g: Graph<Int, DefaultEdge>, args: List<Int>): Int = g.vertexSet().map { g.degreeOf(it) }.sum() / 2

    override fun globalUpperBound(vararg size: Int): Int {
        require(size[0] >= 0)
        return size[0] * (size[0] - 1) / 2
    }
}