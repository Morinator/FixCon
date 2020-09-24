package de.umr.fixcon.itarators

import de.umr.core.*
import de.umr.core.dataStructures.SegmentedList
import de.umr.core.dataStructures.expandSubgraph
import de.umr.core.dataStructures.vertexCount
import de.umr.fixcon.Instance
import de.umr.fixcon.Solution
import de.umr.searchTreeNodes
import org.jgrapht.Graphs.neighborListOf

class IteratorSimple(p: Instance<Int>, start: Int, sol: Solution<Int> = Solution()) : Iterator<Int>(p, start, sol) {

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
                if (searchTreeNodes % 1_000_000 == 0L) println("SearchTree-nodes in million: ${searchTreeNodes  / 1_000_000}")
                val nextVertex = extension[pointers.last()]
                if (numVerticesMissing > 1) extension += neighborListOf(instance.g, nextVertex).filter { it !in extension && it != start }
                subgraph.expandSubgraph(instance.g, nextVertex)
                vertexStack.add(nextVertex)
                pointers[pointers.size - 1] += 1
                pointers.add(pointers.last())
            }
        } while (!isValid && pointers.isNotEmpty())

        if (isValid) sol.updateIfBetter(subgraph, instance.eval(subgraph))
    }

    private fun backTrackingAllowed() = (instance.vertexAdditionRule(subgraph, sol)) || (instance.f.edgeMonotone && cliqueJoinRule() <= sol.value)

    private fun cliqueJoinRule(): Int {
        val newIDs = getNewVertexIDs(subgraph, instance.f.k - subgraph.vertexCount)
        addAsClique(subgraph, newIDs)
        connectVertexSets(subgraph, extendableVertices(), newIDs)
        return instance.eval(subgraph).also { subgraph.removeAllVertices(newIDs) }
    }

    private fun extendableVertices() = HashSet<Int>().apply {
        for (i in vertexStack.indices.reversed())
            if (extension.segments[i] > pointers.last())
                add(vertexStack[i])
            else break
    }
}