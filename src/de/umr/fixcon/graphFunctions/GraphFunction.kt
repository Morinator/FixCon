package de.umr.fixcon.graphFunctions

import com.google.common.graph.Graph

interface GraphFunction {
    val isEdgeMonotone: Boolean
    fun apply(g: Graph<Int>, args: List<Int>): Double
    fun optimum(size: Int): Double = 1.0
}