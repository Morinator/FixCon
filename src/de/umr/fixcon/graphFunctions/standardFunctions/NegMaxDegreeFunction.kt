package de.umr.fixcon.graphFunctions.standardFunctions

import com.google.common.graph.Graph
import de.umr.fixcon.graphFunctions.GraphFunction

class NegMaxDegreeFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun apply(g: Graph<Int>, args: List<Int>) = -1 * MaxDegreeFunction().apply(g, args)

    override fun optimum(size: Int) = if (size > 2) -2.0 else -1.0 //assumes if graph is connected
}