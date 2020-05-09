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
    private val subgraph = VertexOrderedGraph(startVertex)
    private val pointerStack: LinkedList<Int> = LinkedList(listOf(0))
    private var extension: MultiStack<Int> = MultiStack(neighborSetOf(originalGraph, startVertex))

    init {
        require(targetSize > 1)
        mutate()
    }

    override fun isValid() = subgraph.size == targetSize

    override fun current() = subgraph

    override fun mutate() {
        do {
            if (isValid()) {
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
        expandExtension()
        addVertexWithEdges(extension[pointerStack.first])
        pointerStack.incrementHead()
    }

    private fun addVertexWithEdges(vertex: Int) =
            neighborSetOf(originalGraph, vertex).filter { subgraph.containsVertex(it) }
                    .forEach { addEdgeWithVertices(subgraph, extension[pointerStack.first], it) }

    /*Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
    to be delete here in this case. This is the case if the size of the subset is targetSize*/
    private fun deleteSubsetHead() {
        shrinkExtension()
        subgraph.removeLastVertex()
    }

    private fun expandExtension() {
        if (subgraph.size != targetSize - 1)
            extension.addAll(getNewExtension(extension[pointerStack.first]))
    }

    private fun shrinkExtension() {
        if (!isValid()) extension.removeLastSegment()
    }

    private fun getNewExtension(pivot_vertex: Int): List<Int> =
            neighborSetOf(originalGraph, pivot_vertex).filter { !extension.contains(it) && startVertex != it }

    private fun pointerHeadIsOutOfRange() = pointerStack.first == extension.size

    private fun pointerHeadIsPending() = subgraph.size == pointerStack.size
}