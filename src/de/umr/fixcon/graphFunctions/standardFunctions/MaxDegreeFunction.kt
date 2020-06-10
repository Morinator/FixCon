package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.degreeList
import de.umr.core.vertexCount
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Returns the maximum degree of all vertices in this graph.*/
object MaxDegreeFunction :  GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun completeAdditionBound(subgraph: VertexOrderedGraph<Int>, targetSize: Int, args: List<Int>) =
            targetSize- subgraph.vertexCount

    override fun eval(g: Graph<Int, DefaultEdge>, args: List<Int>) = g.degreeList.max()!!

    override fun globalUpperBound(graphSize: Int) = (graphSize - 1).also { require(graphSize >= 0) }
}