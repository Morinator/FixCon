package de.umr.fixcon.heuristics.vertexPickers

import de.umr.fixcon.heuristics.RandomCollection
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge

class GreedySparsePicker(val graph: Graph<Int, DefaultEdge>) : VertexPicker {

    private val verticesBySparsity = RandomCollection(graph.vertexSet().associateWith { 1.0 / graph.degreeOf(it) })

    override fun startVertex() = verticesBySparsity.pickRandom()

    override fun extensionVertex(subgraph: Set<Int>, extension: Set<Int>) =
            extension.minBy { (neighborSetOf(graph, it) intersect subgraph).size }!!
}