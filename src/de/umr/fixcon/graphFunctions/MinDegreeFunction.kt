package de.umr.fixcon.graphFunctions

import de.umr.core.extensions.degreeSequence
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Returns the minimum degree of all vertices in this graph.*/
class MinDegreeFunction(k: Int = dummyK) : AbstractGraphFunction(k = k) {

    override val edgeMonotone = true

    /**Potentially, the new vertex could increase the degree of each vertex
     * with minimal degree by 1.*/
    override val vertexAdditionBound: Int get() = 1

    /**@return The minimum of the degrees of all vertices in [g].*/
    override fun <V> eval(g: Graph<V, DefaultEdge>) = g.degreeSequence.min()!!

    /**The optimal graph is a clique of size [k], in which each vertex has a degree of [k]-1.*/
    override fun globalOptimum() = k - 1
}