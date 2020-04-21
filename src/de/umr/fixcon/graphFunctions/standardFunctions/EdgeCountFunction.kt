package de.umr.fixcon.graphFunctions.standardFunctions

import com.google.common.graph.Graph
import de.umr.fixcon.graphFunctions.GraphFunction

class EdgeCountFunction :  GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun apply(g: Graph<Int>, args: List<Int>): Double = g.nodes().map { x -> g.degree(x) }.sum() / 2.0

    override fun optimum(size: Int): Double {
        require(size >= 0)
        return size * (size - 1) / 2.0
    }
}