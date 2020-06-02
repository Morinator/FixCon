package de.umr.fixcon.heuristics.vertexPickers

import de.umr.fixcon.heuristics.RandomCollection
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge


class DensePicker(val graph: Graph<Int, DefaultEdge>) : VertexPicker {

    private val verticesByDensity = RandomCollection(graph.vertexSet().associateWith { graph.degreeOf(it).toDouble() })

    /**
     * Picks a start-vertex with a probability proportional to its degree in the graph.
     */
    override fun startVertex() = verticesByDensity.pickRandom()

    /**
     * Picks a vertex adjacent to the subgraph with a probability proportional to the number of edges it has to
     * the subgraph.
     */
    override fun extensionVertex(subgraph: Set<Int>, extension: Set<Int>) =
            RandomCollection(
                    extension.associateWith { (neighborSetOf(graph, it) intersect subgraph).size.toDouble() }
            ).pickRandom()
}