package de.umr.fixcon.graphFunctions

import de.umr.core.extensions.edgeCount
import de.umr.core.extensions.vertexCount
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**This function counts the number of edges in a given graph.*/
class EdgeCountFunction(k: Int = dummyK) : AbstractGraphFunction(k = k) {

    override val edgeMonotone = true

    /**corresponds to the arithmetic series: subgraph.size + subgraph.size+1 + ... + targetSize-1*/
    override fun <V> completeBound(subgraph: Graph<V, DefaultEdge>) = (subgraph.vertexCount until k).sum()

    /**The number of edges in [g].*/
    override fun <V> eval(g: Graph<V, DefaultEdge>): Int = g.edgeCount

    /**The optimum would be a clique of size [k].
     * There every one of the [k] vertices has a degree of [k-1]. By the handshaking lemma,
     * the number of edges is half the sum of all degrees.*/
    override fun globalOptimum() = (k * (k - 1) / 2)
}