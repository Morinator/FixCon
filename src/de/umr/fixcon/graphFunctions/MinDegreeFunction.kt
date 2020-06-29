package de.umr.fixcon.graphFunctions

import de.umr.core.dataStructures.degreeSequence
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Returns the minimum degree of all vertices in this graph.*/
class MinDegreeFunction(k: Int = dummyK) : AbstractGraphFunction(k = k) {

    override val vertexAdditionBound: Int get() = 1

    override fun <V> eval(g: Graph<V, DefaultEdge>) = g.degreeSequence.min()!!

    override fun globalOptimum() = k - 1
}