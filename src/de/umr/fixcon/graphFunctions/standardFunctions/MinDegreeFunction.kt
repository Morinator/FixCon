package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**
 * Returns the minimum degree of all vertices in this graph.
 */
object MinDegreeFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun vertexAdditionBound(): Int {
        throw Exception("not implemented yet")
    }

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>) = g.vertexSet().map { g.degreeOf(it) }.min()!!

    override fun globalUpperBound(vararg size: Int): Int = (size[0] - 1)
}