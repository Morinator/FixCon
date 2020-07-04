package de.umr.fixcon.itarators

import de.umr.core.dataStructures.SegmentedList
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.dataStructures.expandSubgraph
import de.umr.core.dataStructures.openNB
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import kotlin.collections.HashSet
import java.util.ArrayDeque

class SubIterator<V>(p: Problem<V>, start: V, sol: Solution<V> = Solution(), private val useBound: Boolean = true) : Iterator<V>(p, start, sol) {

    override val subgraph = VertexOrderedGraph.fromVertices(start)
    private var extension = SegmentedList(p.g.openNB(start))
    private val pointers = ArrayDeque<Int>().apply { add(0) }

    init {
        require(p.function.k > 1)
        mutate()
    }

    fun mutate() {
        do {
            if (pointers.peek() >= extension.size || isValid || (useBound && problem.cantBeatOther(subgraph, sol))) {
                if (numVerticesMissing != 0) extension.removeLastSegment()
                subgraph.removeLastVertex()
                pointers.pop()
            } else {
                if (numVerticesMissing > 1) extension.addAll(exclusiveDiscoveries(extension[pointers.peek()]))
                subgraph.expandSubgraph(problem.g, extension[pointers.peek()])
                pointers.push(pointers.pop() + 1)
                pointers.push(pointers.peek())
            }
        } while (!isValid && pointers.isNotEmpty())

        if (isValid) sol.updateIfBetter(subgraph, problem.eval(subgraph))
    }

    private fun exclusiveDiscoveries(vertex: V): Collection<V> = problem.g.openNB(vertex)
            .filterTo(HashSet()) { it !in extension && it != startVertex }
            .sortedBy { problem.g.degreeOf(it) }
}