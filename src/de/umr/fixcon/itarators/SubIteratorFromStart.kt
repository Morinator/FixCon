package de.umr.fixcon.itarators

import de.umr.core.dataStructures.ListUtils.duplicateHead
import de.umr.core.dataStructures.ListUtils.incrementHead
import de.umr.core.dataStructures.MultiStack
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.wrappers.CFCO_Problem
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge
import java.util.*
import kotlin.math.max

/**
 * Iterates through all *connected* subgraphs of *originalGraph* with *targetSize* vertices and which contains [startVertex].
 * The current subgraph can be retrieved with [current], the next subgraph is generated with [mutate].
 * [isValid] returns *true* iff [current] contains a yet unseen subgraph of *targetSize*
 * and is false once this iterator is exhausted.
 *
 * @param startVertex the vertex from which all connected subgraphs are generated. Therefore, every subgraph returned
 * by [current] also contains [startVertex]
 */
class SubIteratorFromStart(val problem: CFCO_Problem, val startVertex: Int) : GraphIterator<Graph<Int, DefaultEdge>> {
    private val subgraph = VertexOrderedGraph(startVertex)
    private var extension: MultiStack<Int> = MultiStack(neighborSetOf(problem.originalGraph, startVertex))
    private val pointerStack: LinkedList<Int> = LinkedList(listOf(0))
    private var currBestValue = Int.MIN_VALUE

    init {
        require(problem.targetSize > 1)
        mutate()
    }

    override fun isValid() = numberVerticesMissing() == 0

    override fun current() = subgraph

    /**generates the next subgraph which can then be retrieved with [current]. It may also return wrong graphs once
     * the iterator is exhausted, in which case [isValid] turns false*/
    override fun mutate() {
        do {
            if (isValid()) {
                deleteLastVertex()
            } else {
                /**size of subgraph is smaller than targetSize for following code: */
                if (pointerHeadIsOutOfRange()) {
                    if (!pointerHeadIsUnused())
                        deleteLastVertex()
                    deleteLastVertex()
                    pointerStack.pop()
                } else {
                    /**pivot is not out of range*/
                    if (pointerHeadIsUnused()) {
                        addPointerHead()
                    } else {
                        if (pruneWithVertexAdditionBound()) continue
                        pointerStack.duplicateHead()
                    }
                }
            }
        } while (!isValid() && pointerStack.isNotEmpty())
        updateCurrentBestValue()
    }

    private fun updateCurrentBestValue() {
        if (pointerStack.isNotEmpty()) {
            currBestValue = max(currBestValue, currentFunctionValue())
        }
    }

    private fun pruneWithVertexAdditionBound(): Boolean {
        val isApplicable = currentFunctionValue() + numberVerticesMissing() * problem.function.additionBound(problem.targetSize) <= currBestValue
        if (isApplicable) {
            deleteLastVertex()
        }
        return isApplicable
    }

    private fun currentFunctionValue() = problem.function.apply(subgraph, problem.parameters)

    private fun numberVerticesMissing() = problem.targetSize - subgraph.size

    private fun addPointerHead() {
        expandExtension()
        addVertexWithEdges(extension[pointerStack.first])
        pointerStack.incrementHead()
    }

    private fun addVertexWithEdges(vertex: Int) =
            neighborSetOf(problem.originalGraph, vertex).filter { subgraph.containsVertex(it) }
                    .forEach { addEdgeWithVertices(subgraph, extension[pointerStack.first], it) }

    private fun deleteLastVertex() {
        shrinkExtension()
        subgraph.removeLastVertex()
    }

    /**for the last element in the subset it is not necessary to adjust the extension-list, because the subset
    wont be extended further. Therefore in this case the adjustment of the extension-list is omitted.*/
    private fun expandExtension() {
        if (numberVerticesMissing() != 1) extension.addAll(getNewExtension(extension[pointerStack.first]))
    }

    /**Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
    to be delete here in this case. This is the case if the size of the subset is targetSize*/
    private fun shrinkExtension() {
        if (numberVerticesMissing() != 0) extension.removeLastSegment()
    }

    private fun getNewExtension(pointerHead: Int): List<Int> =
            neighborSetOf(problem.originalGraph, pointerHead).filter { !extension.contains(it) && startVertex != it }

    private fun pointerHeadIsOutOfRange() = pointerStack.first == extension.size

    private fun pointerHeadIsUnused() = subgraph.size == pointerStack.size
}