package de.umr.fixcon.heuristics.vertexPickers

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class LaplacePicker<T>(val graph: Graph<T, DefaultEdge>) : VertexPicker<T> {
    override fun startVertex(): T = graph.vertexSet().random()

    override fun extensionVertex(subgraph: Set<T>, extension: Set<T>): T =
            extension.random()
}