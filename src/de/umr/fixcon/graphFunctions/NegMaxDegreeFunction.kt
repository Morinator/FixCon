package de.umr.fixcon.graphFunctions

import de.umr.core.dataStructures.degreeSequence
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Returns the maximum degree of all vertices in this graph.*/
class NegMaxDegreeFunction(k: Int) : AbstractGraphFunction(k = k) {

    override fun <V> completeAdditionBound(subgraph: Graph<V, DefaultEdge>) = 0

    override fun <V> eval(g: Graph<V, DefaultEdge>) = -g.degreeSequence.max()!!

    override fun globalOptimum() = -2
}