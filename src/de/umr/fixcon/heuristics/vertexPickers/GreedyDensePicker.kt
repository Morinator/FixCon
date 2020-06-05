package de.umr.fixcon.heuristics.vertexPickers

import de.umr.core.openNB
import de.umr.fixcon.heuristics.RandomSet
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge


class GreedyDensePicker<T>(val graph: Graph<T, DefaultEdge>) : VertexPicker<T> {

    private val verticesByDensity = RandomSet(graph.vertexSet().associateWith { graph.degreeOf(it).toDouble() })

    /*** Picks a start-vertex with a probability proportional to its degree in the graph.*/
    override fun startVertex(): T = verticesByDensity.random

    /*** Picks a vertex adjacent to the subgraph with a probability proportional to the number of edges it has to
     * the subgraph.*/
    override fun extensionVertex(subgraph: Set<T>, extension: Set<T>): T =
            extension.maxBy { (graph.openNB(it) intersect subgraph).size }!!

}