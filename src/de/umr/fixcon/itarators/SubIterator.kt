package de.umr.fixcon.itarators

import de.umr.core.dataStructures.SegmentedList
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.openNB
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import java.util.*

class SubIterator<V>(problem: Problem<V>, startVertex: V, currBestSolution: Solution<V> = Solution())
    : Iterator<V>(problem, startVertex, currBestSolution) {

    override val subgraph = VertexOrderedGraph.fromVertices(startVertex)
    private var extension = SegmentedList(problem.g.openNB(startVertex))
    private val pointers = LinkedList(listOf(0))

    init {
        require(problem.function.k > 1)
        mutate()
    }

    fun mutate() {
        do {
            if (isValid || pointers[0] >= extension.size || additionBoundApplicable) {
                if (!isValid) extension.removeLastSegment()
                subgraph.removeLastVertex()
                pointers.pop()
            } else {
                if (numVerticesMissing > 1) extension.addAll(firstDiscoveredFromVertex(extension[pointers[0]]))
                addToSubgraph(extension[pointers[0]])
                pointers[0]++
                pointers.push(pointers[0])
            }
        } while (!isValid && pointers.isNotEmpty())

        if (isValid) currBestSolution.updateIfBetter(subgraph, problem.eval(subgraph))
    }

    private fun firstDiscoveredFromVertex(vertex: V): Set<V> = problem.g.openNB(vertex)
            .filter { it !in extension && it != startVertex }.toSet()
}