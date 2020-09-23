package de.umr.fixcon

import de.umr.core.dataStructures.Partitioning
import de.umr.core.dataStructures.neighbours
import de.umr.core.dataStructures.vertexCount
import de.umr.core.prunePartsGreaterK
import de.umr.core.removeSmallComponents
import de.umr.fixcon.heuristic.getHeuristic
import de.umr.fixcon.itarators.IteratorSimple

fun solve(p: Problem<Int>): Solution<Int> {
    removeSmallComponents(p.g, p.f.k)

    val sol = getHeuristic(p)
    println("Heuristic: $sol")
    if (sol.value == p.f.globalOptimum()) return sol

    val critPartition = getCriticalPartitioning(p)

    while (sol.value < p.f.globalOptimum() && p.g.vertexCount > 0) {

        prunePartsGreaterK(p.g, p.f.k, critPartition)

        val startV = critPartition.subsets.maxBy { it.size }!!.random()
        val iterator = IteratorSimple(p, startV, sol)

        while (iterator.isValid) iterator.mutate()

        val nbVertices = p.g.neighbours(critPartition[startV])
        p.g.removeAllVertices(critPartition[startV])
        critPartition.removeSubset(startV)
        mergeTwinSets(p, critPartition, nbVertices)
    }
    return sol
}

private fun <V> mergeTwinSets(p: Problem<V>, cPart: Partitioning<V>, nbVertices: Set<V>) {
    critCliqueMerge(p.g, cPart, nbVertices)
    critISMerge(p.g, cPart, nbVertices)
}

