package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.degreeSequence
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Returns the minimum degree of all vertices in this graph.*/
object MinDegreeFunction : GraphFunction() {

    override val vertexAdditionBound: Int get() = 1

    override fun <V> eval(g: Graph<V, DefaultEdge>, args: List<Int>) =
            g.degreeSequence.min()!!

    override fun globalOptimum(graphSize: Int?) = graphSize!! - 1
}