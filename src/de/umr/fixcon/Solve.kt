package de.umr.fixcon

import de.umr.core.dataStructures.SetPartitioning
import de.umr.core.extensions.*
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
    pruneBigSubsets(critPartition, p)

    var iteratorsUsed = 0

    while (sol.value < p.f.globalOptimum() && p.g.vertexCount >= p.f.k) {
        val startVertex = critPartition.subsets.maxBy { it.size }!!.random()
        val iterator = SimpleIter(p, startVertex, sol)

        while (iterator.isValid) iterator.mutate()

        updateCriticalPartitionAndGraph(p, critPartition, startVertex)

        iteratorsUsed++
    }
    return sol.also { println("Iterators used:".padEnd(pad) + iteratorsUsed) }
}

private fun <V> updateCriticalPartitionAndGraph(p: Problem<V>, critPartition: SetPartitioning<V>, startVertex: V) {
    val nbVertices = p.g.openNB(critPartition[startVertex])

    p.g.removeAllVertices(critPartition[startVertex].also { println("Start vertices deleted: ${it.size}") })
    critPartition.removeSubset(startVertex)

    //merge critical cliques
    for ((v1, v2) in allPairs(nbVertices))
        if (p.g.closedNBEquals(v1, v2))
            critPartition.merge(v1, v2).also { println("Merge critical cliques") }

    //merge critical independent sets
//    for (v1 in nbVertices) {
//        val minNB = p.g.openNB(v1).minBy { p.g.degreeOf(it) }
//        minNB?.let {
//            for (v2 in p.g.openNB(minNB))
//                if (p.g.openNBEquals(v1, v2))
//                    critPartition.merge(v1, minNB).also { println("Merge critical independent set") }
//        }
//    }
}