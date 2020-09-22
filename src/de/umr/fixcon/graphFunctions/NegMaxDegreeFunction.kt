package de.umr.fixcon.graphFunctions

import de.umr.core.dataStructures.degreeSequence
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Returns the maximum degree of all vertices in this graph.*/
class NegMaxDegreeFunction(k: Int= dummyK) : AbstractGraphFunction(k = k) {

    /**The maximum degree cannot be reduced by adding vertices. Therefore, the best case
     * is if the maximum degree is not changed at all.*/
    override fun <V> completeBound(subgraph: Graph<V, DefaultEdge>) = 0

    /**@return The negative of the maximum degree of the vertices in [g].*/
    override fun <V> eval(g: Graph<V, DefaultEdge>) = -g.degreeSequence.max()!!

    /**Assuming the graph is connected and has a size of at least 3, there needs to be
     * a vertex with degree of at least 2 to connect the other vertices.*/
    override fun globalOptimum() = if (k <= 2) -1 else -2
}