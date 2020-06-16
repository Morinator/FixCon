package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.GraphMetrics.getDiameter
import org.jgrapht.graph.DefaultEdge

object DiameterFunction : GraphFunction() {

    override val vertexAdditionBound: Int get() = 1

    override fun <V> eval(g: Graph<V, DefaultEdge>, args: List<Int>) = getDiameter(g).toInt()

    /**Path with [graphSize] vertices has [graphSize]-1 edges that form a path.*/
    override fun globalOptimum(graphSize: Int?) = graphSize!! - 1
}