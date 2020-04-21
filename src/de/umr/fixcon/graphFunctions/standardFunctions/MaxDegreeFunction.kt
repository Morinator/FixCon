package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class MaxDegreeFunction :  GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>) = g.vertexSet().map { x -> g.degreeOf(x) }.max()!!.toDouble()

    override fun optimum(size: Int) = (size - 1).toDouble()
}