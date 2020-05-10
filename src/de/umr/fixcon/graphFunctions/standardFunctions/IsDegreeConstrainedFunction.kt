package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**
 * This function returns 1 (indicator for **True**) iff the degree of every vertex in the graph
 * is inside of the specified range
 */
object IsDegreeConstrainedFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun vertexAdditionBound(): Int {
        TODO("Not yet implemented")
    }

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>) : Int {
        require(args[0] <= args[1])
        return if (g.vertexSet().map { g.degreeOf(it) }.all { it in args[0]..args[1] }) 1 else 0
    }
}