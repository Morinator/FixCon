package de.umr.fixcon.itarators

import de.umr.core.dataStructures.SegmentedList
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.openNB
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution
import java.util.*

class SubIterator<V>(problem: CFCO_Problem<V>,
                     startVertex: V,
                     currBestSolution: Solution<V> = Solution())

    : Iterator<V>(problem, startVertex, currBestSolution) {

    override val subgraph = VertexOrderedGraph.fromVertices(startVertex)
    private var extension = SegmentedList(problem.originalGraph.openNB(startVertex))
    private val pointers = LinkedList(listOf(0))

    init {
        require(problem.targetSize > 1)
        mutate()
    }

    fun mutate() {
        do {
            if (isValid || pointers[0] >= extension.size || additionBoundApplicable) {
                if (!isValid) extension.removeLastSegment()

                subgraph.removeLastVertex()
                pointers.pop()
            } else {
                if (numVerticesMissing > 1) extension.addAll(discoveredNB(extension[pointers[0]]))

                addVertexWithEdges(extension[pointers[0]])
                pointers[0]++
                pointers.push(pointers.peek())
            }
        } while (!isValid && pointers.isNotEmpty())
        updateSolution()
    }

    private fun discoveredNB(vertex: V): Set<V> = problem.originalGraph.openNB(vertex)
            .filter { it !in extension && it != startVertex }.toSet()

    private val additionBoundApplicable: Boolean
        get() = currentFunctionalValue() + problem.function.completeAdditionBound(subgraph, problem.targetSize) <= currBestSolution.value
}