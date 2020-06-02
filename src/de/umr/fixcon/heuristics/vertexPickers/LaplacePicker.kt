package de.umr.fixcon.heuristics.vertexPickers

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class LaplacePicker(val graph: Graph<Int, DefaultEdge>) : VertexPicker {
    override fun startVertex() : Int = graph.vertexSet().random()

    override fun extensionVertex(subgraph: Set<Int>, extension: Set<Int>) =
            extension.random()
}