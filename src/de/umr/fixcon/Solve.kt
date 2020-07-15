package de.umr.fixcon

import de.umr.core.extensions.openNB
import de.umr.core.pad
import de.umr.core.prunePartsGreaterK
import de.umr.core.removeSmallComponents
import de.umr.fixcon.heuristic.getHeuristic
import de.umr.fixcon.itarators.SimpleIter
import de.umr.fixcon.twins.critCliqueMerge
import de.umr.fixcon.twins.getCriticalPartitioning

fun <V> solve(p: Problem<V>): Solution<V> {
    removeSmallComponents(p.g, p.f.k)

    val sol = getHeuristic(p)
    if (sol.value == p.f.globalOptimum()) return sol

    val critPartition = getCriticalPartitioning(p)
    prunePartsGreaterK(p.g, p.f.k, critPartition)

    var iteratorsUsed = 0

    while (sol.value < p.f.globalOptimum() && p.graphBigEnough) {
        val startVertex = critPartition.subsets.maxBy { it.size }!!.random()
        val iterator = SimpleIter(p, startVertex, sol)

        while (iterator.isValid) iterator.mutate()

        val nbVertices = p.g.openNB(critPartition[startVertex])
        p.g.removeAllVertices(critPartition[startVertex].also { println("Partition vertices deleted:".padEnd(pad) + it.size) })
        critPartition.removeSubset(startVertex)
        critCliqueMerge(p.g, critPartition, nbVertices)

        iteratorsUsed++
    }
    return sol.also { println("Iterators used:".padEnd(pad) + iteratorsUsed) }
}