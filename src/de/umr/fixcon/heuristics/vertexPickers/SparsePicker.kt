package de.umr.fixcon.heuristics.vertexPickers

import de.umr.fixcon.heuristics.RandomCollection
import org.jgrapht.Graph
import org.jgrapht.Graphs
import org.jgrapht.graph.DefaultEdge

class SparsePicker(val graph: Graph<Int, DefaultEdge>) : VertexPicker {

    private val verticesBySparsity = RandomCollection(graph.vertexSet().associateWith { 1.0 / graph.degreeOf(it) })

    override fun startVertex() = verticesBySparsity.pickRandom()

    override fun extensionVertex(subgraph: Set<Int>, extension: Set<Int>): Int {
        return RandomCollection(
                extension.associateWith { 1.0 / (Graphs.neighborSetOf(graph, it) intersect subgraph).size }
        ).pickRandom()
    }
}