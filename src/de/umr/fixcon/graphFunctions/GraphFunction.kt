package de.umr.fixcon.graphFunctions

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge


interface GraphFunction {
    val isEdgeMonotone: Boolean
    fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>): Double
    fun optimum(vararg size: Int): Double = 1.0
}