package de.umr.fixcon.itarators

import de.umr.core.dataStructures.ListUtils.duplicateHead
import de.umr.core.dataStructures.ListUtils.incrementHead
import de.umr.core.dataStructures.MultiStack
import de.umr.core.dataStructures.VertexOrderedGraph
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge
import java.util.*

class SubIteratorFromStart(private val originalGraph: Graph<Int, DefaultEdge>, val startVertex: Int, private val targetSize: Int) : GraphIterator<Graph<Int, DefaultEdge>> {
    private val subgraph = VertexOrderedGraph<Int>()
    private val pointerStack: LinkedList<Int> = LinkedList(listOf(0))

    private var extension: MultiStack<Int> = MultiStack(neighborSetOf(originalGraph, startVertex))

    init {
        require(targetSize > 1)
        subgraph.addVertex(startVertex)
        mutate()
    }

    override fun isValid(): Boolean = subgraph.size == targetSize

    override fun current(): Graph<Int, DefaultEdge> = subgraph

    override fun mutate() {
        do {
            if (subgraph.size == targetSize) {
                deleteSubsetHead()
            } else {
                if (pointerHeadIsOutOfRange()) { //size of subset is < k for following code:
                    if (!pointerHeadIsPending())
                        deleteSubsetHead()
                    deleteSubsetHead()
                    pointerStack.pop()
                } else { //pivot is not out of range
                    if (pointerHeadIsPending()) addPivot() else pointerStack.duplicateHead()
                }
            }
        } while (!isValid() && pointerStack.size > 0)
    }

    /*for the last element in the subset it is not necessary to adjust the extension-list, because the subset
    wont be extended further. Therefore in this case the adjustment of the extension-list is omitted.*/
    private fun addPivot() {
        if (!subgraphOneTooSmall())
            extension.addAll(getNewExtension(pivotVertex()))
        addVertexWithEdges(pivotVertex())
        pointerStack.incrementHead()
    }

    private fun addVertexWithEdges(vertex: Int) =
            neighborSetOf(originalGraph, vertex).filter { subgraph.containsVertex(it) }
                    .forEach { addEdgeWithVertices(subgraph, pivotVertex(), it) }

    private fun subgraphOneTooSmall() = subgraph.size == targetSize - 1

    /*Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
    to be delete here in this case. This is the case if the size of the subset is targetSize*/
    private fun deleteSubsetHead() {
        if (!subgraphIsFull())
            extension.removeTailSegment()
        subgraph.removeLastVertex()
    }

    private fun subgraphIsFull() = subgraph.size == targetSize

    private fun getNewExtension(pivot_vertex: Int): List<Int> =
            neighborSetOf(originalGraph, pivot_vertex).filter { !extension.contains(it) && it != startVertex }

    private fun pointerHeadIsOutOfRange(): Boolean = pointerStack.first == extension.size

    private fun pointerHeadIsPending(): Boolean = subgraph.size == pointerStack.size

    private fun pivotVertex(): Int = extension[pointerStack.first]
}