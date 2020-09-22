package de.umr.fixcon.itarators

import de.umr.core.*
import de.umr.core.dataStructures.SegmentedList
import de.umr.core.extensions.expandSubgraph
import de.umr.core.extensions.vertexCount
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import de.umr.searchTreeNodes
import org.jgrapht.Graphs.neighborListOf

class IteratorSimple(p: Problem<Int>, start: Int, sol: Solution<Int> = Solution()) : Iterator<Int>(p, start, sol) {

    override val subgraph = fromVertices(start)
    private val vertexStack = mutableListOf(start)

    private var extension = SegmentedList<Int>().apply { this += neighborListOf(p.g, start) }
    private val pointers = mutableListOf(0)

    init {
        require(p.f.k > 1)
        mutate()
    }

    override fun mutate() {
        do {
            if (pointers.last() >= extension.size || isValid || backTrackingAllowed()) {
                if (!isValid) extension.removeLastSegment()
                val v = vertexStack.removeAt(vertexStack.size - 1)
                subgraph.removeVertex(v)
                pointers.removeAt(pointers.size - 1)
            } else {
                searchTreeNodes++
                val nextVertex = extension[pointers.last()]
                if (numVerticesMissing > 1) extension += neighborListOf(p.g, nextVertex).filter { it !in extension && it != start }
                subgraph.expandSubgraph(p.g, nextVertex)
                vertexStack.add(nextVertex)
                pointers[pointers.size - 1] += 1
                pointers.add(pointers.last())
            }
        } while (!isValid && pointers.isNotEmpty())

        if (isValid) sol.updateIfBetter(subgraph, p.eval(subgraph))
    }

    private fun backTrackingAllowed() = (p.cantBeatOther(subgraph, sol)) || (p.f.edgeMonotone && cliqueJoinValue(p) <= sol.value)

    private fun cliqueJoinValue(p: Problem<Int>): Int {
        val newIDs = getNewVertexIDs(subgraph, p.f.k - subgraph.vertexCount)
        addAsClique(subgraph, newIDs)
        connectVertexSets(subgraph, extendableVertices(), newIDs)
        return p.eval(subgraph).also { subgraph.removeAllVertices(newIDs) }
    }

    private fun extendableVertices() = HashSet<Int>().apply {
        for ((i, v) in vertexStack.withIndex())
            if (extension.segmentList[i] > pointers.last()) add(v)
    }
}