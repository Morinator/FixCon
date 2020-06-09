package de.umr.fixcon.heuristics.vertexPickers

import de.umr.core.openNB
import de.umr.fixcon.heuristics.WeightedRandomSet
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge


class RandomDensePicker<T>(override val graph: Graph<T, DefaultEdge>) : VertexPicker<T>(graph) {

    /**Picks a start-vertex with a probability proportional to its degree in the graph.*/
    override fun startVertex(): T = verticesByDensity.random

    /**Picks a vertex adjacent to the subgraph with a probability proportional to the number of edges it has to
     * the subgraph.*/
    override fun extensionVertex(subgraph: Set<T>, extension: Set<T>): T =
            WeightedRandomSet(extension.associateWith { neighboursInSubgraph(subgraph, it).toDouble() }).random
}