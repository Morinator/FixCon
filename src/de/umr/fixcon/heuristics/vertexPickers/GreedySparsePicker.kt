package de.umr.fixcon.heuristics.vertexPickers

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class GreedySparsePicker<T>(override val graph: Graph<T, DefaultEdge>) : VertexPicker<T>(graph) {

    override fun startVertex(): T = verticesBySparsity.random

    override fun extensionVertex(subgraph: Set<T>, extension: Set<T>): T =
            extension.minBy { neighboursInSubgraph(subgraph, it) }!!
}