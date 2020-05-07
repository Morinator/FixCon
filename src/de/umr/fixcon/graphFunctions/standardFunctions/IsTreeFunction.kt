package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.GraphTests
import org.jgrapht.GraphTests.isTree
import org.jgrapht.graph.DefaultEdge

class IsTreeFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>) = if (isTree(g)) 1.0 else 0.0
}