package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.degreeSequence
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Returns the maximum degree of all vertices in this graph.*/
class NegMaxDegreeFunction(k: Int) : GraphFunction(k = k) {

    override fun <V> completeAdditionBound(subgraph: Graph<V, DefaultEdge>) = 0

    override fun <V> eval(g: Graph<V, DefaultEdge>) = -g.degreeSequence.max()!!

    override fun globalOptimum() = -2
}