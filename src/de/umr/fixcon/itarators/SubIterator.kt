package de.umr.fixcon.itarators

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge


class SubIterator(private val originalGraph: Graph<Int, DefaultEdge>, private val targetSize: Int) : GraphIterator<Graph<Int, DefaultEdge>> {
    private var subgraphMutator = SubIteratorFromStart(originalGraph, anyVertex(), targetSize)
    var searchTreeCounter = 0
    var sizeKSubgraphCount = 0

    override fun isValid(): Boolean = subgraphMutator.isValid()

    override fun current(): Graph<Int, DefaultEdge> = subgraphMutator.current()

    override fun mutate() { //fixed_subgraphIterator throws exception if it doesn't have next element
        subgraphMutator.mutate()
        sizeKSubgraphCount++
        while (!subgraphMutator.isValid() && originalGraph.vertexSet().size > targetSize) {
            searchTreeCounter += subgraphMutator.searchTreeCounter
            originalGraph.removeVertex(subgraphMutator.startVertex)
            subgraphMutator = SubIteratorFromStart(originalGraph, anyVertex(), targetSize)
        }
    }

    private fun anyVertex(): Int = originalGraph.vertexSet().first()
}