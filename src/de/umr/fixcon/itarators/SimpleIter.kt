package de.umr.fixcon.itarators

import de.umr.core.dataStructures.SegmentedList
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.extensions.duplicateHead
import de.umr.core.extensions.expandSubgraph
import de.umr.core.extensions.incrementHead
import de.umr.core.extensions.openNB
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import java.util.*
import kotlin.collections.HashSet

class SimpleIter<V>(p: Problem<V>, start: V, sol: Solution<V> = Solution(), private val useBound: Boolean = true) : Iterator<V>(p, start, sol) {

    override val subgraph = VertexOrderedGraph.fromVertices(start)
    private var extension = SegmentedList<V>().apply { addAll(p.g.openNB(start)) }
    private val pointers = ArrayDeque<Int>(listOf(0))

    init {
        require(p.f.k > 1)
        mutate()
    }

    fun mutate() {
        do {
            if (pointers.peek() >= extension.size || isValid || (p.cantBeatOther(subgraph, sol) && useBound)) {
                if (!isValid) extension.removeLastSegment()
                subgraph.removeLastVertex()
                pointers.pop()
            } else {
                if (numVerticesMissing > 1) extension.addAll(exclusiveDiscoveries(extension[pointers.peek()]))
                subgraph.expandSubgraph(p.g, extension[pointers.peek()])
                pointers.incrementHead()
                pointers.duplicateHead()
            }
        } while (!isValid && pointers.isNotEmpty())

        if (isValid) sol.updateIfBetter(subgraph, p.eval(subgraph))
    }

    private fun exclusiveDiscoveries(vertex: V): Collection<V> = p.g.openNB(vertex)
            .filterTo(HashSet()) { it !in extension && it != startVertex }
}