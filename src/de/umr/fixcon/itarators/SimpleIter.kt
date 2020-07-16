package de.umr.fixcon.itarators

import de.umr.core.dataStructures.SegmentedList
import de.umr.core.dataStructures.duplicateHead
import de.umr.core.extensions.expandSubgraph
import de.umr.core.dataStructures.incrementHead
import de.umr.core.extensions.openNB
import de.umr.core.fromVertices
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import java.util.*
import kotlin.collections.HashSet

class SimpleIter(p: Problem<Int>, start: Int, sol: Solution<Int> = Solution()) : Iterator<Int>(p, start, sol) {

    override val subgraph = fromVertices(this.start)
    private val vertexStack = ArrayDeque<Int>().apply { push(start) }

    private var extension = SegmentedList<Int>().apply { this += p.g.openNB(start) }
    private val pointers = ArrayDeque<Int>(listOf(0))

    init {
        require(p.f.k > 1)
        mutate()
    }

    fun mutate() {
        do {
            if (pointerHeadOutOfRange() || isValid || (p.cantBeatOther(subgraph, sol))) {
                if (!isValid) extension.removeLastSegment()
                subgraph.removeVertex(vertexStack.pop())
                pointers.pop()
            } else {
                if (numVerticesMissing > 1) extension += exclusiveDiscoveries(nextVertex())
                subgraph.expandSubgraph(p.g, nextVertex())
                vertexStack.push(nextVertex())
                incrementHead(pointers)
                duplicateHead(pointers)
            }
        } while (!isValid && pointers.isNotEmpty())

        if (isValid) sol.updateIfBetter(subgraph, p.eval(subgraph))
    }

    private fun pointerHeadOutOfRange() = pointers.peek() >= extension.size

    private fun nextVertex() = extension[pointers.peek()]

    private fun exclusiveDiscoveries(vertex: Int): Collection<Int> = p.g.openNB(vertex)
            .filterTo(HashSet()) { it !in extension && it != start }
}