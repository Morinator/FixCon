package de.umr.fixcon.heuristics.vertexPickers

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class GreedyDensePicker<T>(override val graph: Graph<T, DefaultEdge>) : VertexPicker<T>(graph) {

    override fun startVertex(): T = verticesByDensity.random

    override fun extensionVertex(subgraph: Set<T>, extension: Set<T>): T =
            extension.maxBy { subgraphNB(subgraph, it) }!!
}