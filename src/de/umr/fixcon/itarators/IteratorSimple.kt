package de.umr.fixcon.itarators

import de.umr.core.dataStructures.SegmentedList
import de.umr.core.extensions.expandSubgraph
import de.umr.core.fromVertices
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import de.umr.fixcon.rules.cliqueJoinRule
import de.umr.searchTreeNodes
import org.jgrapht.Graphs.neighborListOf
import java.util.*
import kotlin.collections.HashSet

class IteratorSimple(p: Problem<Int>, start: Int, sol: Solution<Int> = Solution()) : Iterator<Int>(p, start, sol) {

    override val subgraph = fromVertices(start)
    private val vertexStack = ArrayDeque<Int>().apply { push(start) }

    private var extension = SegmentedList<Int>().apply { this += neighborListOf(p.g, start) }
    private val pointers = ArrayDeque<Int>(listOf(0))

    val extendableVertices = HashSet<Int>()

    init {
        require(p.f.k > 1)
        mutate()
    }

    fun mutate() {
        do {
            if (pointerHeadOutOfRange() || isValid || backTrackingAllowed()) {
                if (!isValid) extension.removeLastSegment()
                val v = vertexStack.pop()
                subgraph.removeVertex(v)
                pointers.pop()
                extendableVertices.remove(v)
        } else {
                searchTreeNodes++
                if (numVerticesMissing > 1) extension += exclusiveDiscoveries(nextVertex())
                subgraph.expandSubgraph(p.g, nextVertex())
                vertexStack.push(nextVertex())
                pointers.push(pointers.pop() + 1)   //TODO hässlich
                pointers.push(pointers.first())
                if (pointers.peek()!! >= extension.segmentList.last()) extendableVertices.add(vertexStack.peek()!!)
            }
        } while (!isValid && pointers.isNotEmpty())

        if (isValid) sol.updateIfBetter(subgraph, p.eval(subgraph))
    }

    private fun pointerHeadOutOfRange() = pointers.peek() >= extension.size

    private fun nextVertex() = extension[pointers.peek()]

    private fun exclusiveDiscoveries(vertex: Int): Collection<Int> = neighborListOf(p.g, vertex)
            .filter { it !in extension && it != start }

    /**@return *True* if one of the backtracking-rules is applicable.*/
    private fun backTrackingAllowed() = (p.cantBeatOther(subgraph, sol)) || (p.f.edgeMonotone && cliqueJoinRule(subgraph, p, sol.value))
}