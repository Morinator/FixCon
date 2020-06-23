package de.umr.fixcon.graphFunctions

import org.jgrapht.Graph
import org.jgrapht.GraphMetrics.getDiameter
import org.jgrapht.graph.DefaultEdge

class DiameterFunction(k: Int) : AbstractGraphFunction(k = k) {

    override val vertexAdditionBound: Int get() = 1

    override fun <V> eval(g: Graph<V, DefaultEdge>) = getDiameter(g).toInt()

    /**Path with [k] vertices has [k]-1 edges that form a path.*/
    override fun globalOptimum() = k - 1
}