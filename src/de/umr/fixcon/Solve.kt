package de.umr.fixcon

import de.umr.core.*
import de.umr.core.dataStructures.*
import de.umr.searchTreeNodes
import org.jgrapht.Graphs

fun runSearchTree(instance: Instance<Int>, start: Int, sol: Solution<Int> = Solution()) {

    val subgraph = fromVertices(start)
    val vertexStack = mutableListOf(start)

    val extension = SegmentedList<Int>().apply { this += Graphs.neighborListOf(instance.g, start) }
    val pointers = mutableListOf(0)

    fun numVerticesMissing() = instance.f.k - subgraph.vertexCount
    fun isValid() = numVerticesMissing() == 0

    fun extendableVertices() = HashSet<Int>().apply {
        for (i in vertexStack.indices.reversed())
            if (extension.segments[i] > pointers.last())
                add(vertexStack[i])
            else break
    }

    fun cliqueJoinRule(): Int {
        val newIDs = getNewVertexIDs(subgraph, instance.f.k - subgraph.vertexCount)
        addAsClique(subgraph, newIDs)
        connectVertexSets(subgraph, extendableVertices(), newIDs)
        return instance.eval(subgraph).also { subgraph.removeAllVertices(newIDs) }
    }

    while (pointers.isNotEmpty()) {
        if (pointers.last() >= extension.size || isValid() || (instance.vertexAdditionRule(subgraph, sol)) || (instance.f.edgeMonotone && cliqueJoinRule() <= sol.value)) {
            if (!isValid()) extension.removeLastSegment()
            val v = vertexStack.removeAt(vertexStack.size - 1)
            subgraph.removeVertex(v)
            pointers.removeAt(pointers.size - 1)
        } else {
            if (++searchTreeNodes % 1_000_000 == 0L) println("SearchTree-nodes in million: ${searchTreeNodes / 1_000_000}")
            val nextVertex = extension[pointers.last()]
            if (numVerticesMissing() > 1) extension += Graphs.neighborListOf(instance.g, nextVertex).filter { it !in extension && it != start }
            subgraph.expandSubgraph(instance.g, nextVertex)
            vertexStack.add(nextVertex)
            pointers[pointers.size - 1] += 1
            pointers.add(pointers.last())
        }
        if (isValid()) sol.updateIfBetter(subgraph, instance.eval(subgraph))
    }
}

fun solve(i: Instance<Int>): Solution<Int> {
    removeComponentsSmallerThreshold(i.g, i.f.k)

    val sol = getHeuristic(i)
    println("Heuristic: $sol")

    if (sol.value == i.f.globalOptimum())
        return sol.also { println("Heuristic was optimal") }


    val criticalPartition = getCriticalPartitioning(i)

    while (sol.value < i.f.globalOptimum() && i.g.vertexCount >= i.f.k) {
        val startVertex = criticalPartition.subsets.maxByOrNull { it.size }!!.first()

        runSearchTree(i, startVertex, sol)

        //update critical partition (perform merges if possible)
        val nbVertices = i.g.neighbours(criticalPartition[startVertex])
        i.g.removeAllVertices(criticalPartition[startVertex])
        criticalPartition.removeSubset(startVertex)
        critCliqueMerge(i.g, criticalPartition, nbVertices)
        critISMerge(i.g, criticalPartition, nbVertices)
    }
    return sol
}