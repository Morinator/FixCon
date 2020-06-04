package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**
 * Returns the maximum degree of all vertices in this graph.
 */
object MaxDegreeFunction :  GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun completeAdditionBound(subgraph: VertexOrderedGraph<Int>, targetSize: Int, args: List<Int>) =
            targetSize- subgraph.vertexCount

    override fun eval(g: Graph<Int, DefaultEdge>, args: List<Int>) = g.vertexSet().map { g.degreeOf(it) }.max()!!

    override fun globalUpperBound(vararg size: Int) = (size[0] - 1)
}