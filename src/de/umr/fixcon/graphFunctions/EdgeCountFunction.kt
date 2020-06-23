package de.umr.fixcon.graphFunctions

import de.umr.core.dataStructures.edgeCount
import de.umr.core.dataStructures.vertexCount
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**This function counts the number of edges in a given graph.*/
class EdgeCountFunction(k: Int) : AbstractGraphFunction(k = k) {

    /**corresponds to the arithmetic series: subgraph.size + subgraph.size+1 + ... + targetSize-1*/
    override fun <V> completeAdditionBound(subgraph: Graph<V, DefaultEdge>) = (subgraph.vertexCount until k).sum()

    override fun <V> eval(g: Graph<V, DefaultEdge>): Int = g.edgeCount

    override fun globalOptimum() = (k * (k - 1) / 2)

    fun <V> localOptimum(g: Graph<V, DefaultEdge>, v: V) = g.degreeOf(v) + EdgeCountFunction(k - 1).globalOptimum()
}