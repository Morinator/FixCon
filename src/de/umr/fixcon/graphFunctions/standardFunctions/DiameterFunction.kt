package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.GraphMetrics.getDiameter
import org.jgrapht.graph.DefaultEdge

/**
 * The other functions and properties need to be revised.
 */
object DiameterFunction : GraphFunction {

    override fun <V> eval(g: Graph<V, DefaultEdge>, args: List<Int>) = getDiameter(g).toInt()

}