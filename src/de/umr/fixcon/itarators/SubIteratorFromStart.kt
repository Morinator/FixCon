package de.umr.fixcon.itarators

import de.umr.core.utils.ExtensionList
import de.umr.core.utils.ListUtils.duplicateHead
import de.umr.core.utils.ListUtils.incrementHead
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.util.*

class SubIteratorFromStart(private val originalGraph: Graph<Int, DefaultEdge>, val startVertex: Int, private val targetSize: Int) : GraphIterator<Graph<Int, DefaultEdge>> {
    var searchTreeCounter = 0
    private val subgraph = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)
    private val pointerStack: LinkedList<Int> = LinkedList(listOf(0))
    private val subgraphStack: LinkedList<Int> = LinkedList(listOf(startVertex))

    private var extensionList: ExtensionList<Int> = ExtensionList(neighborSetOf(originalGraph, startVertex))
    private val privateStack: LinkedList<Int> = LinkedList(listOf(neighborSetOf(originalGraph, startVertex).size))

    init {
        require(targetSize > 1)
        subgraph.addVertex(startVertex)
        mutate()
    }

    override fun isValid(): Boolean = subgraphStack.size == targetSize

    override fun current(): Graph<Int, DefaultEdge> = subgraph

    override fun mutate() {
        do {
            if (subgraphStack.size == targetSize) {
                deleteSubsetHead()
            } else {
                if (pointerHeadIsOutOfRange()) { //size of subset is < k for following code:
                    if (!pointerHeadIsPending()) deleteSubsetHead()
                    deleteSubsetHead()
                    pointerStack.pop()
                } else { //pivot is not out of range
                    if (pointerHeadIsPending()) addPivot() else duplicateHead(pointerStack)
                }
            }
        } while (!isValid() && pointerStack.size > 0)
    }

    /*for the last element in the subset it is not necessary to adjust the extension-list, because the subset
    wont be extended further. Therefore in this case the adjustment of the extension-list is omitted.*/
    private fun addPivot() {
        if (!subgraphOneTooSmall()) {
            val newExtension = getNewExtension(pivotVertex())
            extensionList.addAll(newExtension)
            privateStack.push(newExtension.size)
        }
        subgraphStack.push(pivotVertex())
        neighborSetOf(originalGraph, pivotVertex()).filter { subgraph.vertexSet().contains(it) }
                .forEach { addEdgeWithVertices(subgraph, pivotVertex(), it) }
        incrementHead(pointerStack)
        searchTreeCounter++
    }

    private fun subgraphOneTooSmall() = subgraphStack.size == targetSize - 1

    /*Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
    to be delete here in this case. This is the case if the size of the subset is targetSize*/
    private fun deleteSubsetHead() {
        if (!subgraphHasTargetSize()) {
            extensionList.removeFromEnd(privateStack.pop())
        }
        subgraph.removeVertex(subgraphStack.pop())
    }

    private fun subgraphHasTargetSize() = subgraphStack.size == targetSize

    private fun getNewExtension(pivot_vertex: Int): List<Int> =
            neighborSetOf(originalGraph, pivot_vertex).filter { !extensionList.contains(it) && it != startVertex }

    private fun pointerHeadIsOutOfRange(): Boolean = pointerStack.first == extensionList.size

    private fun pointerHeadIsPending(): Boolean = subgraphStack.size == pointerStack.size

    private fun pivotVertex(): Int = extensionList[pointerStack.first]
}