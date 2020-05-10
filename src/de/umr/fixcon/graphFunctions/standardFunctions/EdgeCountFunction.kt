package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**
 * This function counts the number of edges in a given graph.
 */
object EdgeCountFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>) = g.vertexSet().map { g.degreeOf(it) }.sum() / 2.0

    override fun optimum(vararg size: Int): Double {
        require(size[0] >= 0)
        return size[0] * (size[0] - 1) / 2.0
    }
}