package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.vertexCount
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.GraphMetrics.getDiameter
import org.jgrapht.graph.DefaultEdge

/**
 * The other functions and properties need to be revised.
 */
object DiameterFunction : GraphFunction {

    override val isEdgeMonotone: Boolean get() = true

    override fun <V> eval(g: Graph<V, DefaultEdge>, args: List<Int>) = getDiameter(g).toInt()

    /**If every new vertex extends the currently longest path.*/
    override fun <V> completeAdditionBound(subgraph: VertexOrderedGraph<V>, targetSize: Int, args: List<Int>) =
            targetSize - subgraph.vertexCount

    /**Path with [graphSize] vertices has [graphSize]-1 edges that form a path.*/
    override fun globalOptimum(graphSize: Int) = graphSize-1
}