package de.umr.fixcon

import de.umr.core.dataStructures.SetPartitioning
import de.umr.core.extensions.closedNBEquals
import de.umr.core.extensions.connectedPairs
import de.umr.core.extensions.openNB
import de.umr.core.pad
import de.umr.core.pruneBigSubsets
import de.umr.core.removeSmallComponents
import de.umr.fixcon.heuristic.getHeuristic
import de.umr.fixcon.itarators.SimpleIter
import de.umr.fixcon.twins.getCriticalPartitioning

fun <V> solve(p: Problem<V>): Solution<V> {
    removeSmallComponents(p.g, p.f.k)

    val sol = getHeuristic(p)
    if (sol.value == p.f.globalOptimum()) return sol

    val critPartition = getCriticalPartitioning(p)
    pruneBigSubsets(p, critPartition)

    var iteratorsUsed = 0

    while (sol.value < p.f.globalOptimum() && p.graphBigEnough) {
        val startVertex = critPartition.subsets.maxBy { it.size }!!.random()
        val iterator = SimpleIter(p, startVertex, sol)

        while (iterator.isValid) iterator.mutate()

        updateCriticalPartitionAndGraph(p, critPartition, startVertex)

        iteratorsUsed++
    }
    return sol.also { println("Iterators used:".padEnd(pad) + iteratorsUsed) }
}

private fun <V> updateCriticalPartitionAndGraph(p: Problem<V>, critPartition: SetPartitioning<V>, v: V) {
    val nbVertices = p.g.openNB(critPartition[v])

    p.g.removeAllVertices(critPartition[v].also { println("Partition vertices deleted:".padEnd(pad) + it.size) })
    critPartition.removeSubset(v)

    //merge critical cliques
    for ((v1, v2) in p.g.connectedPairs(nbVertices)) {
        if (p.g.closedNBEquals(v1, v2))
            critPartition.merge(v1, v2).also { println("Merge critical cliques") }
    }
}