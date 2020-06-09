package de.umr.fixcon.heuristics.vertexPickers

import de.umr.fixcon.heuristics.WeightedRandomSet
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class RandomSparsePicker<T>(override val graph: Graph<T, DefaultEdge>) : VertexPicker<T>(graph) {

    override fun startVertex(): T = verticesBySparsity.random

    override fun extensionVertex(subgraph: Set<T>, extension: Set<T>) =
            WeightedRandomSet(extension.associateWith { 1.0 / subgraphNB(subgraph, it) }).random
}