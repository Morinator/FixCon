package de.umr.fixcon.itarators

import de.umr.core.dataStructures.SegmentedList
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.vertexCount
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge
import java.util.*

class SubIteratorFromStart(private val problem: CFCO_Problem, val startVertex: Int, private val currBestSolution: Solution = Solution()) : GraphIterator<Graph<Int, DefaultEdge>> {
    private val subgraph: VertexOrderedGraph<Int> = VertexOrderedGraph(startVertex)
    private var extension = SegmentedList(neighborSetOf(problem.originalGraph, startVertex))
    private val pointerStack = LinkedList(listOf(0))

    init {
        require(problem.targetSize > 1)
        mutate()
    }

    override val isValid get() = numVerticesMissing() == 0

    override val current get() = subgraph

    override fun mutate() {
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
        if (isValid) currBestSolution.updateIfBetter(current, currentFunctionalValue())
    }

    private fun pruneWithVertexAdditionBound() =
            (currentFunctionalValue() + problem.function.completeAdditionBound(subgraph, problem.targetSize, problem.parameters) <= currBestSolution.value)
                    .also { if (it) popLastVertexWithExtension() }

    private fun currentFunctionalValue() = problem.function.eval(subgraph, problem.parameters)

    private fun numVerticesMissing() = problem.targetSize - subgraph.vertexCount

    private fun addPointerHeadWithExtension() {
        expandExtension()
        addVertexWithEdges(extension[pointerStack.first])
        pointerStack[0]++
    }

    private fun addVertexWithEdges(vertex: Int) =
            neighborSetOf(problem.originalGraph, vertex).filter { subgraph.containsVertex(it) }
                    .forEach { addEdgeWithVertices(subgraph, extension[pointerStack.first], it) }

    private fun popLastVertexWithExtension() = shrinkExtension().also { subgraph.removeLastVertex() }

    /**for the last element in the subset it is not necessary to adjust the extension-list, because the subset
    wont be extended further. Therefore in this case the adjustment of the extension-list is omitted.*/
    private fun expandExtension() {
        if (numVerticesMissing() != 1)
            extension.addAll(neighborSetOf(problem.originalGraph, extension[pointerStack.first])
                    .filter { it !in extension && startVertex != it })
    }

    /**Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
    to be delete here in this case. This is the case if the size of the subset is targetSize*/
    private fun shrinkExtension() {
        if (numVerticesMissing() > 0) extension.removeLastSegment()
    }

    private fun pointerHeadIsOutOfRange() = pointerStack.first == extension.size

    private fun pointerHeadIsUnused() = subgraph.vertexCount == pointerStack.size
}