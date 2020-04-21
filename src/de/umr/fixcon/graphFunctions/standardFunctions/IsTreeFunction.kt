package de.umr.fixcon.graphFunctions.standardFunctions

import com.google.common.graph.Graph
import com.google.common.graph.Graphs
import de.umr.fixcon.graphFunctions.GraphFunction

class IsTreeFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun apply(g: Graph<Int>, args: List<Int>): Double {
        require(g.nodes().isNotEmpty())
        val isConnected = Graphs.reachableNodes(g, g.nodes().first()) == g.nodes()
        !Graphs.hasCycle(g)
        return if (!Graphs.hasCycle(g) && isConnected) 1.0 else 0.0
    }
}