package de.umr.fixcon.itarators

import de.umr.core.utils.FastList
import org.jgrapht.Graphs
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.util.*


class SubIteratorFromStart(private val originalGraph: org.jgrapht.Graph<Int, DefaultEdge>, val startVertex: Int, private val targetSize: Int) : GraphIterator<org.jgrapht.Graph<Int, DefaultEdge>> {
    var searchTreeCounter = 0
    private val subgraph = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)
    private val pointerStack: Deque<Int> = LinkedList(listOf(0))
    private val subgraphStack: Deque<Int> = LinkedList(listOf(startVertex))
    private var extensionList: MutableList<Int> = FastList(Graphs.neighborSetOf(originalGraph, startVertex))
    private val privateStack: Deque<Int> = LinkedList(listOf(Graphs.neighborSetOf(originalGraph, startVertex).size))

    override fun isValid(): Boolean = subgraphStack.size == targetSize

    override fun current(): org.jgrapht.Graph<Int, DefaultEdge> = subgraph

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
                    if (pointerHeadIsPending()) addPivot() else pointerStack.push(pointerStack.peek())
                }
            }
        } while (!isValid() && pointerStack.size > 0)
    }

    /*for the last element in the subset it is not necessary to generate the extension-list, because the subset
    wont be extended further. Therefore in this case the adjustment of the extension-list is omitted.*/
    private fun addPivot() {
        if (subgraph.vertexSet().size != targetSize - 1) {
            val newExtension = getNewExtension(pivotVertex())
            extensionList.addAll(newExtension)
            privateStack.push(newExtension.size)
        }
        subgraphStack.push(pivotVertex())
        Graphs.neighborSetOf(originalGraph, pivotVertex()).filter { x -> subgraph.vertexSet().contains(x) }
                .forEach { x-> Graphs.addEdgeWithVertices(subgraph, pivotVertex(), x) }
        pointerStack.push(pointerStack.pop() + 1)
        searchTreeCounter++
    }

    /*Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
    to be delete here in this case. This is the case if the size of the subset is targetSize*/
    private fun deleteSubsetHead() {
        if (subgraphStack.size != targetSize) extensionList.removeLast(privateStack.pop())
        subgraph.removeVertex(subgraphStack.pop())
    }

    private fun getNewExtension(pivot_vertex: Int) =
            Graphs.neighborSetOf(originalGraph,pivot_vertex).filter { x -> !extensionList.contains(x) && x != startVertex }

    private fun pointerHeadIsOutOfRange(): Boolean = pointerStack.first == extensionList.size

    private fun pointerHeadIsPending(): Boolean = subgraphStack.size == pointerStack.size

    private fun pivotVertex(): Int = extensionList[pointerStack.first]

    init {
        require(targetSize > 1)
        subgraph.addVertex(startVertex)
        mutate()
    }
}

private fun <E> MutableList<E>.removeLast(n : Int) {
    repeat(n) { this.removeAt(this.size-1)}
}