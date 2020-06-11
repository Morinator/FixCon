package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.degreeSequence
import de.umr.core.vertexCount
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Returns the minimum degree of all vertices in this graph.*/
object MinDegreeFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun <V> completeAdditionBound(subgraph: VertexOrderedGraph<V>, targetSize: Int, args: List<Int>) = targetSize - subgraph.vertexCount

    override fun <V> eval(g: Graph<V, DefaultEdge>, args: List<Int>) =
            g.degreeSequence.min()!!


    override fun globalUpperBound(graphSize: Int) = MaxDegreeFunction.globalUpperBound(graphSize)   //functions are exactly the same
}