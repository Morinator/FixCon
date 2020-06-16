package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.degreeSequence
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Returns the maximum degree of all vertices in this graph.*/
object NegMaxDegreeFunction : GraphFunction {
    override fun <V> completeAdditionBound(subgraph: Graph<V, DefaultEdge>, targetSize: Int, args: List<Int>) = 0

    override fun <V> eval(g: Graph<V, DefaultEdge>, args: List<Int>) = -(g.degreeSequence.max()!!)

    override fun globalOptimum(graphSize: Int) = -2
}