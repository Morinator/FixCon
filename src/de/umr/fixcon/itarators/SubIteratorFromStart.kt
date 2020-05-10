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
class SubIteratorFromStart(private val problem: CFCO_Problem, val startVertex: Int) : GraphIterator<Graph<Int, DefaultEdge>> {
    private val subgraph = VertexOrderedGraph(startVertex)
    private var extension: MultiStack<Int> = MultiStack(neighborSetOf(problem.originalGraph, startVertex))
    private val pointerStack: LinkedList<Int> = LinkedList(listOf(0))
    private var currentBestValue = Int.MIN_VALUE

    init {
        require(problem.subgraphSize > 1)
        mutate()
    }

    /*** @return *True* if the currently selected subgraph has *targetSize* and therefore is valid.*/
    override fun isValid() = subgraph.size == problem.subgraphSize

    /**@return The currently selected subgraph. It may return wrong results if [isValid] is false*/
    override fun current() = subgraph

    /**generates the next subgraph which can then be retrieved with [current]. It may also return wrong graphs once
     * the iterator is exhausted, in which case [isValid] turns false*/
    override fun mutate() {
        do {
            if (isValid()) {
                deleteSubsetHead()
            } else {
                if (pointerHeadIsOutOfRange()) { //size of subset is < k for following code:
                    if (!pointerHeadIsUnused())
                        deleteSubsetHead()
                    deleteSubsetHead()
                    pointerStack.pop()
                } else { //pivot is not out of range
                    if (pruneWithVertexAdditionBound()) continue
                    if (pointerHeadIsUnused()) addPivot() else pointerStack.duplicateHead()
                }
            }
        } while (!isValid() && pointerStack.size > 0)
        currentBestValue = max(currentBestValue, 1) //TODO needs to be updated
    }

    private fun pruneWithVertexAdditionBound(): Boolean {
        return false
    }

    private fun addPivot() {
        expandExtension()
        addVertexWithEdges(extension[pointerStack.first])
        pointerStack.incrementHead()
    }

    private fun addVertexWithEdges(vertex: Int) =
            neighborSetOf(problem.originalGraph, vertex).filter { subgraph.containsVertex(it) }
                    .forEach { addEdgeWithVertices(subgraph, extension[pointerStack.first], it) }

    private fun deleteSubsetHead() {
        shrinkExtension()
        subgraph.removeLastVertex()
    }

    /**for the last element in the subset it is not necessary to adjust the extension-list, because the subset
    wont be extended further. Therefore in this case the adjustment of the extension-list is omitted.*/
    private fun expandExtension() {
        if (subgraph.size != problem.subgraphSize - 1) extension.addAll(getNewExtension(extension[pointerStack.first]))
    }

    /**Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
    to be delete here in this case. This is the case if the size of the subset is targetSize*/
    private fun shrinkExtension() {
        if (!isValid()) extension.removeLastSegment()
    }

    private fun getNewExtension(pivot_vertex: Int): List<Int> =
            neighborSetOf(problem.originalGraph, pivot_vertex).filter { !extension.contains(it) && startVertex != it }

    private fun pointerHeadIsOutOfRange() = pointerStack.first == extension.size

    private fun pointerHeadIsUnused() = subgraph.size == pointerStack.size
}