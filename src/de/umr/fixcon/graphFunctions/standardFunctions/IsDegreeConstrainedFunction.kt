package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class IsDegreeConstrainedFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>): Double {
        require(args[0] <= args[1])
        return if (g.vertexSet().map { g.degreeOf(it) }.all { it in args[0]..args[1] }) 1.0 else 0.0
    }
}