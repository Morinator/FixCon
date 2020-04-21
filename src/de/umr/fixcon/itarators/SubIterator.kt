package de.umr.fixcon.itarators

import com.google.common.graph.Graph
import com.google.common.graph.Graphs
import com.google.common.graph.MutableGraph

class SubIterator(private val originalGraph: MutableGraph<Int>, private val targetSize: Int) : GraphIterator<Graph<Int>> {
    private var subgraphMutator = SubIteratorFromStart(originalGraph, anyVertex(), targetSize)
    var searchTreeCounter = 0
    var sizeKSubgraphCount = 0

    override fun isValid(): Boolean = subgraphMutator.isValid()

    override fun current(): Graph<Int> = Graphs.copyOf(subgraphMutator.current())

    override fun mutate() { //fixed_subgraphIterator throws exception if it doesn't have next element
        subgraphMutator.mutate()
        sizeKSubgraphCount++
        while (!subgraphMutator.isValid() && originalGraph.nodes().size > targetSize) {
            searchTreeCounter += subgraphMutator.searchTreeCounter
            originalGraph.removeNode(subgraphMutator.startVertex)
            subgraphMutator = SubIteratorFromStart(originalGraph, anyVertex(), targetSize)
        }
    }

    private fun anyVertex(): Int = originalGraph.nodes().first()
}