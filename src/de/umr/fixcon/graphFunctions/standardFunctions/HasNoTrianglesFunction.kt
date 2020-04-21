package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.hasTriangle
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class HasNoTrianglesFunction :  GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>): Double = if (!hasTriangle(g)) 1.0 else 0.0
}