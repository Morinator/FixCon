package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**
 * This function returns 1 (indicator for **True**) iff the degree of every vertex in the graph
 * is exactly the specified Integer.
 */
object IsNRegularFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun vertexAdditionBound(): Int {
        throw Exception("not implemented yet")
    }

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>): Int {
        require(args[0] >= 0)
        return if (g.vertexSet().all { g.degreeOf(it) == args[0] }) 1 else 0
    }
}