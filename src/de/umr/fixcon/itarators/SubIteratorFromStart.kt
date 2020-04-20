package de.umr.fixcon.itarators

import com.google.common.collect.Iterables
import com.google.common.graph.ElementOrder
import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder
import com.google.common.graph.MutableGraph
import de.umr.core.utils.FastList
import java.util.*
import java.util.stream.Collectors

class SubIteratorFromStart(private val originalGraph: MutableGraph<Int>, val startVertex: Int, private val targetSize: Int) : GraphIterator<Graph<Int>> {
    var searchTreeCounter = 0
    private val subgraph = GraphBuilder.undirected().nodeOrder(ElementOrder.insertion<Any>()).build<Int>()
    private var extensionList: MutableList<Int> = FastList()
    private val pointerStack: Deque<Int> = LinkedList(listOf(0))
    private val privateStack: Deque<Int> = LinkedList()

    override fun isValid(): Boolean = subgraph.nodes().size == targetSize

    override fun current(): Graph<Int> = subgraph

    override fun mutate() {
        do {
            if (subgraph.nodes().size == targetSize) {
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
        if (subgraph.nodes().size != targetSize - 1) {
            val newExtension = getNewExtension(pivotVertex())
            extensionList.addAll(newExtension)
            privateStack.push(newExtension.size)
        }
        originalGraph.adjacentNodes(pivotVertex()).stream().filter { x: Int -> subgraph.nodes().contains(x) }
                .forEach { x-> subgraph.putEdge(pivotVertex(), x) }
        pointerStack.push(pointerStack.pop() + 1)
        searchTreeCounter++
    }

    /*Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
    to be delete here in this case. This is the case if the size of the subset is targetSize*/
    private fun deleteSubsetHead() {
        if (subgraph.nodes().size != targetSize) extensionList = extensionList.subList(0, extensionList.size - privateStack.pop())
        subgraph.removeNode(Iterables.getLast(subgraph.nodes()))
    }

    private fun getNewExtension(pivot_vertex: Int) =
            originalGraph.adjacentNodes(pivot_vertex).filter { x -> !extensionList.contains(x) && x != startVertex }

    private fun pointerHeadIsOutOfRange(): Boolean = pointerStack.first == extensionList.size

    private fun pointerHeadIsPending(): Boolean = subgraph.nodes().size == pointerStack.size

    private fun pivotVertex(): Int = extensionList[pointerStack.first]

    init {
        require(targetSize > 1)
        subgraph.addNode(startVertex)
        extensionList.addAll(originalGraph.adjacentNodes(startVertex))
        privateStack.push(extensionList.size)
        mutate()
    }
}