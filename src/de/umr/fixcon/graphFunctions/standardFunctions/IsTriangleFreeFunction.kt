package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.GraphAlgorithms
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**
 * This function returns 1 (indicator for **True**) iff the graph contains no triangles.
 */
object IsTriangleFreeFunction :  GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>): Double = if (!GraphAlgorithms.hasTriangle(g)) 1.0 else 0.0
}