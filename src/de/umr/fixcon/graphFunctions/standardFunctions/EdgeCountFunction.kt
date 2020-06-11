package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.edgeCount
import de.umr.core.vertexCount
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**This function counts the number of edges in a given graph.*/
object EdgeCountFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = true

    /**corresponds to the arithmetic series: subgraph.size + subgraph.size+1 + ... + targetSize-1*/
    override fun completeAdditionBound(subgraph: VertexOrderedGraph<Int>, targetSize: Int, args: List<Int>) =
            (subgraph.vertexCount until targetSize).sum()

    override fun eval(g: Graph<Int, DefaultEdge>, args: List<Int>): Int = g.edgeCount

    override fun globalUpperBound(graphSize: Int) = (graphSize * (graphSize - 1) / 2)
}