package de.umr.fixcon.graphFunctions.standardFunctions

import com.google.common.graph.Graph
import de.umr.fixcon.graphFunctions.GraphFunction

class MaxDegreeFunction :  GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun apply(g: Graph<Int>, vararg args: Int): Double = g.nodes().map { x -> g.degree(x) }.max()!!.toDouble()

    override fun optimum(size: Int) = (size - 1).toDouble()
}