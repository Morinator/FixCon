package de.umr.fixcon

import de.umr.core.dataStructures.Partitioning
import de.umr.core.dataStructures.neighbours
import de.umr.core.dataStructures.vertexCount
import de.umr.core.prunePartsGreaterK
import de.umr.core.removeSmallComponents
import de.umr.fixcon.heuristic.getHeuristic
import de.umr.fixcon.itarators.IteratorSimple

fun solve(p: Instance<Int>): Solution<Int> {
    removeSmallComponents(p.g, p.f.k)

    val sol = getHeuristic(p)
    println("Heuristic: $sol")
    if (sol.value == p.f.globalOptimum()) return sol

    val critPartition = getCriticalPartitioning(p)
    prunePartsGreaterK(p.g, p.f.k, critPartition)

    while (sol.value < p.f.globalOptimum() && p.g.vertexCount >= p.f.k) {
        val startVertex = critPartition.subsets.maxByOrNull { it.size }!!.first()
        val iterator = IteratorSimple(p, startVertex, sol)

        while (iterator.isValid) iterator.mutate()      //TODO in den iterator rein

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

