package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class IsNRegularFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>): Double {
        require(args[0] >= 0)
        return if (g.vertexSet().map { x -> g.degreeOf(x) }.all { x -> g.degreeOf(x) == args[0] }) 1.0 else 0.0
    }
}