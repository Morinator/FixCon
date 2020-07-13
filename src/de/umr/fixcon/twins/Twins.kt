package de.umr.fixcon.twins

import de.umr.core.dataStructures.SetPartitioning
import de.umr.core.extensions.closedNB
import de.umr.core.extensions.openNB
import de.umr.fixcon.Problem

fun <V> getCriticalPartitioning(problem: Problem<V>): SetPartitioning<V> {

    /**Partitions the [vertices] of the *graph* in [problem] into a [SetPartitioning].
     * The vertices in each subset of the [SetPartitioning] have equal neighbours,
     * either closed or open, respective what is provided as [nbSelector].*/
    fun fastPartition(vertices: Collection<V> = problem.g.vertexSet(), hashFu: (V) -> List<Int>, nbSelector: (V) -> Set<V>) =
            SetPartitioning<V>().apply {
                for (verticesByHash in vertices.groupBy { hashFu(it) }.values) {
                    addByEQPredicate(verticesByHash) { x, y -> nbSelector(x) == nbSelector(y) }
                }
            }

    val partitioning = fastPartition(problem.g.vertexSet(), { vHashClosed(problem.g, it) }, { problem.g.closedNB(it) })

    val verticesLeft = partitioning.elements.filter { partitioning[it].size == 1 }.toList()
    println("Vertices suitable for partitioning into critical independent sets: ${verticesLeft.size}")

    partitioning -= verticesLeft
    partitioning.disjointUnion(fastPartition(verticesLeft, { vHashOpen(problem.g, it) }, { problem.g.openNB(it) }))
    return partitioning
}
