package de.umr.fixcon.graphFunctions.standardFunctions

import com.google.common.graph.Graph
import de.umr.fixcon.graphFunctions.GraphFunction

class MinDegreeFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun apply(g: Graph<Int>, args: List<Int>) = g.nodes().map { x -> g.degree(x) }.min()!!.toDouble()

    override fun optimum(size: Int): Double = (size - 1).toDouble()
}