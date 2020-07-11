package de.umr.fixcon.graphFunctions

import org.jgrapht.Graph
import org.jgrapht.GraphMetrics.getDiameter
import org.jgrapht.graph.DefaultEdge

class DiameterFunction(k: Int= dummyK) : AbstractGraphFunction(k = k) {

    /**Adds the vertex to one of the ends of the path that results in the diameter.*/
    override val vertexAdditionBound: Int get() = 1

    /**The diameter of [g], which is the longest shortest path in [g], or equivalently the
     * maximum of all the distances between any two vertices in [g].*/
    override fun <V> eval(g: Graph<V, DefaultEdge>) = getDiameter(g).toInt()

    /**Path with [k] vertices has [k]-1 edges that form a path.*/
    override fun globalOptimum() = k - 1
}