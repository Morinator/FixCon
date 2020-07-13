package de.umr.fixcon.itarators

import de.umr.core.dataStructures.SegmentedList
import de.umr.core.extensions.duplicateHead
import de.umr.core.extensions.expandSubgraph
import de.umr.core.extensions.incrementHead
import de.umr.core.extensions.openNB
import de.umr.core.fromVertices
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import java.util.*
import kotlin.collections.HashSet

class SimpleIter<V>(p: Problem<V>, start: V, sol: Solution<V> = Solution()) : Iterator<V>(p, start, sol) {

    override val subgraph = fromVertices(this.start).also { vertexStack.push(start) }
    private var extension = SegmentedList<V>().apply { this += p.g.openNB(start) }
    private val pointers = ArrayDeque<Int>(listOf(0))

    init {
        require(p.f.k > 1)
        mutate()
    }

    fun mutate() {
        do {
            if (pointers.peek() >= extension.size || isValid || (p.cantBeatOther(subgraph, sol))) {
                if (!isValid) extension.removeLastSegment()
                subgraph.removeVertex(vertexStack.pop())
                pointers.pop()
            } else {
                if (numVerticesMissing > 1) extension += exclusiveDiscoveries(extension[pointers.peek()])
                subgraph.expandSubgraph(p.g, extension[pointers.peek()])
                vertexStack.push(extension[pointers.peek()])
                incrementHead(pointers)
                duplicateHead(pointers)
            }
        } while (!isValid && pointers.isNotEmpty())

        if (isValid) sol.updateIfBetter(subgraph, p.eval(subgraph))
    }

    private fun exclusiveDiscoveries(vertex: V): Collection<V> = p.g.openNB(vertex)
            .filterTo(HashSet()) { it !in extension && it != start }
}