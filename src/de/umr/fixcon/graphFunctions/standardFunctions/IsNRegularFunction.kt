package de.umr.fixcon.graphFunctions.standardFunctions

import com.google.common.graph.Graph
import de.umr.fixcon.graphFunctions.GraphFunction

class IsNRegularFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun apply(g: Graph<Int>, args: List<Int>): Double {
        require(args[0] >= 0)
        return if (g.nodes().map { x -> g.degree(x) }.all { x: Int -> g.degree(x) == args[0] }) 1.0 else 0.0
    }
}