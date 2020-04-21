package de.umr.fixcon.graphFunctions.standardFunctions

import com.google.common.graph.Graph
import de.umr.core.hasTriangle
import de.umr.fixcon.graphFunctions.GraphFunction

class HasNoTrianglesFunction :  GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun apply(g: Graph<Int>, args: List<Int>): Double = if (!hasTriangle(g)) 1.0 else 0.0
}