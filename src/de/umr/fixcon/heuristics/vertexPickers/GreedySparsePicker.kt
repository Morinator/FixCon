package de.umr.fixcon.heuristics.vertexPickers

import de.umr.core.openNB
import de.umr.fixcon.heuristics.RandomSet
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class GreedySparsePicker<T>(val graph: Graph<T, DefaultEdge>) : VertexPicker<T> {

    private val verticesBySparsity = RandomSet(graph.vertexSet().associateWith { 1.0 / graph.degreeOf(it) })

    override fun startVertex(): T = verticesBySparsity.random

    override fun extensionVertex(subgraph: Set<T>, extension: Set<T>): T =
            extension.minBy { (graph.openNB(it) intersect subgraph).size }!!
}