package de.umr.fixcon

import de.umr.core.*
import de.umr.core.dataStructures.*
import de.umr.fixcon.heuristic.getHeuristic
import de.umr.searchTreeNodes
import org.jgrapht.Graphs

fun runSearchTree(instance: Instance<Int>,  start: Int,  sol: Solution<Int> = Solution()) {

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

    do {
        if (pointers.last() >= extension.size || isValid() || (instance.vertexAdditionRule(subgraph, sol)) || (instance.f.edgeMonotone && cliqueJoinRule() <= sol.value)) {
            if (!isValid()) extension.removeLastSegment()
            val v = vertexStack.removeAt(vertexStack.size - 1)
            subgraph.removeVertex(v)
            pointers.removeAt(pointers.size - 1)
        } else {
            searchTreeNodes++
            if (searchTreeNodes % 1_000_000 == 0L) println("SearchTree-nodes in million: ${searchTreeNodes / 1_000_000}")
            val nextVertex = extension[pointers.last()]
            if (numVerticesMissing() > 1) extension += Graphs.neighborListOf(instance.g, nextVertex).filter { it !in extension && it != start }
            subgraph.expandSubgraph(instance.g, nextVertex)
            vertexStack.add(nextVertex)
            pointers[pointers.size - 1] += 1
            pointers.add(pointers.last())
        }
        if (isValid()) sol.updateIfBetter(subgraph, instance.eval(subgraph))
    } while (pointers.isNotEmpty())
}

fun solve(p: Instance<Int>): Solution<Int> {
    removeSmallComponents(p.g, p.f.k)

    val sol = getHeuristic(p)
    println("Heuristic: $sol")
    if (sol.value == p.f.globalOptimum()) return sol

    val critPartition = getCriticalPartitioning(p)
    prunePartsGreaterK(p.g, p.f.k, critPartition)

    while (sol.value < p.f.globalOptimum() && p.g.vertexCount >= p.f.k) {
        val startVertex = critPartition.subsets.maxByOrNull { it.size }!!.first()
        runSearchTree(p, startVertex, sol)

        val nbVertices = p.g.neighbours(critPartition[startVertex])
        p.g.removeAllVertices(critPartition[startVertex])
        critPartition.removeSubset(startVertex)
        mergeCriticalSets(p, critPartition, nbVertices)
    }
    return sol
}

private fun <V> mergeCriticalSets(p: Instance<V>, cPart: Partitioning<V>, nbVertices: Set<V>) {
    critCliqueMerge(p.g, cPart, nbVertices)
    critISMerge(p.g, cPart, nbVertices)
}

