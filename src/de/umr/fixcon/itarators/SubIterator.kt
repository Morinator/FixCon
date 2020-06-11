package de.umr.fixcon.itarators

import de.umr.core.addEdgeWithVertices
import de.umr.core.dataStructures.SegmentedList
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.openNB
import de.umr.core.vertexCount
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution
import java.util.*

class SubIterator<V>(private val problem: CFCO_Problem<V>, val startVertex: V, private val currBestSolution: Solution<V> = Solution()) {
    val subgraph: VertexOrderedGraph<V> = VertexOrderedGraph.fromVertices(startVertex)
    private var extension = SegmentedList(problem.originalGraph.openNB(startVertex))
    private val pointerStack = LinkedList(listOf(0))

    init {
        require(problem.targetSize > 1)
        mutate()
    }

    val isValid get() = numVerticesMissing() == 0

    fun mutate() {
        do {
            if (isValid) {
                popLastVertexWithExtension()
            } else {
                /**size of subgraph is smaller than targetSize for following code: */
                if (pointerHeadIsOutOfRange()) {
                    if (!pointerHeadIsUnused())
                        popLastVertexWithExtension()
                    popLastVertexWithExtension()
                    pointerStack.pop()
                } else {
                    /**pivot is not out of range*/
                    if (pointerHeadIsUnused()) {
                        addPointerHeadWithExtension()
                    } else {
                        if (pruneWithVertexAdditionBound()) continue
                        pointerStack.push(pointerStack.peek())  //duplicates head element
                    }
                }
            }
        } while (!isValid && pointerStack.isNotEmpty())
        updateSolution()
    }

    private fun updateSolution() {
        if (isValid) currBestSolution.updateIfBetter(subgraph, currentFunctionalValue())
    }

    private fun pruneWithVertexAdditionBound() =
            (currentFunctionalValue() + problem.function.completeAdditionBound(subgraph, problem.targetSize, problem.parameters) <= currBestSolution.value)
                    .also { if (it) popLastVertexWithExtension() }

    private fun currentFunctionalValue() = problem.function.eval(subgraph, problem.parameters)

    private fun numVerticesMissing() = problem.targetSize - subgraph.vertexCount

    private fun addPointerHeadWithExtension() { //TODO warum nicht vertauschbar
        expandExtension()
        addVertexWithEdges(extension[pointerStack.first])
        pointerStack[0]++
    }

    private fun addVertexWithEdges(vertex: V) =
            (problem.originalGraph.openNB(vertex) intersect subgraph.vertexSet())
                    .forEach { subgraph.addEdgeWithVertices(it, vertex) }

    private fun popLastVertexWithExtension() {
        shrinkExtension(); subgraph.removeLastVertex()
    }

    /**for the last element in the subset it is not necessary to adjust the extension-list, because the subset
     * wont be extended further. Therefore in this case the adjustment of the extension-list is omitted.
     *
     * @return True iff extension was altered <=> [subgraph] had more than 1 vertex missing*/
    private fun expandExtension() = (numVerticesMissing() > 1).also {
        if (it) extension.addAll(problem.originalGraph.openNB(extension[pointerStack.first])
                .filter { vertex -> vertex !in extension && vertex != startVertex })
    }

    /**Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
     * to be delete here in this case. This is the case if the size of the subset is targetSize
     * @return True iff extension was altered <=> [subgraph] has more than 1 vertex missing*/
    private fun shrinkExtension() = (!isValid).also { if (it) extension.removeLastSegment() }

    private fun pointerHeadIsOutOfRange() = pointerStack.first == extension.size

    private fun pointerHeadIsUnused() = subgraph.vertexCount == pointerStack.size
}