package de.umr.fixcon.itarators

import de.umr.core.dataStructures.SegmentedList
import de.umr.core.dataStructures.duplicateHead
import de.umr.core.dataStructures.incrementHead
import de.umr.core.extensions.expandSubgraph
import de.umr.core.extensions.nb
import de.umr.core.fromVertices
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import de.umr.fixcon.cliqueJoinRule.cliqueJoinRule
import org.jgrapht.Graphs
import org.jgrapht.Graphs.neighborListOf
import java.util.*

class SimpleIter(p: Problem<Int>, start: Int, sol: Solution<Int> = Solution()) : Iterator<Int>(p, start, sol) {

    override val sub = fromVertices(start)
    private val vertexStack = ArrayDeque<Int>().apply { push(start) }

    private var extension = SegmentedList<Int>().apply { this += neighborListOf(p.g, start) }
    private val pointers = ArrayDeque<Int>(listOf(0))

    init {
        require(p.f.k > 1)
        mutate()
    }

    fun mutate() {
        do {
            if (pointerHeadOutOfRange() || isValid || backTrackingAllowed()) {
                if (!isValid) extension.removeLastSegment()
                sub.removeVertex(vertexStack.pop())
                pointers.pop()
            } else {
                if (numVerticesMissing > 1) extension += exclusiveDiscoveries(nextVertex())
                sub.expandSubgraph(p.g, nextVertex())
                vertexStack.push(nextVertex())
                incrementHead(pointers)
                duplicateHead(pointers)
            }
        } while (!isValid && pointers.isNotEmpty())

        if (isValid) sol.updateIfBetter(sub, p.eval(sub))
    }

    private fun pointerHeadOutOfRange() = pointers.peek() >= extension.size

    private fun nextVertex() = extension[pointers.peek()]

    private fun exclusiveDiscoveries(vertex: Int): Collection<Int> = neighborListOf(p.g, vertex)
            .filter { it !in extension && it != start }

    /**@return *True* if one of the backtracking-rules is applicable.*/
    private fun backTrackingAllowed() = (p.cantBeatOther(sub, sol)) || (p.f.edgeMonotone && cliqueJoinRule(sub, p, sol.value))
}