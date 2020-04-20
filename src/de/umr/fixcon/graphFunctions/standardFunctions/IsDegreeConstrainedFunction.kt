package de.umr.fixcon.graphFunctions.standardFunctions

import com.google.common.graph.Graph
import de.umr.fixcon.graphFunctions.GraphFunction

class IsDegreeConstrainedFunction :  GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun apply(g: Graph<Int>, vararg args: Int): Double {
        require(args[0] <= args[1])
        return if (g.nodes().map { x -> g.degree(x) }.all { x: Int -> args[0] <= x && x <= args[1] }) 1.0 else 0.0
    }
}