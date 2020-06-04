package de.umr.fixcon.heuristics.vertexPickers

import de.umr.fixcon.heuristics.RandomCollection
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge

class GreedySparsePicker<T>(val graph: Graph<T, DefaultEdge>) : VertexPicker<T> {

    private val verticesBySparsity = RandomCollection(graph.vertexSet().associateWith { 1.0 / graph.degreeOf(it) })

    override fun startVertex(): T = verticesBySparsity.random

    override fun extensionVertex(subgraph: Set<T>, extension: Set<T>): T =
            extension.minBy { (neighborSetOf(graph, it) intersect subgraph).size }!!
}