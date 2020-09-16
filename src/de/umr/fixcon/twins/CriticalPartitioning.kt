package de.umr.fixcon.twins

import de.umr.core.dataStructures.SetPartitioning
import de.umr.core.extensions.closedNB
import de.umr.core.extensions.closedNBEqualsFast
import de.umr.core.extensions.openNBEqualsFast
import de.umr.core.pad
import de.umr.fixcon.Problem
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge

fun <V> getCriticalPartitioning(problem: Problem<V>): SetPartitioning<V> {

    /**Partitions the [vertices] of the *graph* in [problem] into a [SetPartitioning].
     * The vertices in each subset of the [SetPartitioning] have equal neighbours,
     * either closed or open, respective what is provided as [nbSelector].*/
    fun partitionWithHash(vertices: Collection<V>, hashFu: (V) -> List<Int>, nbSelector: (V) -> Collection<V>): SetPartitioning<V> =
            SetPartitioning<V>().apply {
                for (verticesByHash in vertices.groupBy { hashFu(it) }.values)
                    addByEQPredicate(verticesByHash) { x, y -> nbSelector(x) == nbSelector(y) }
            }

    val partitioning = partitionWithHash(problem.g.vertexSet(), { vHashClosed(problem.g, it) }, { problem.g.closedNB(it) })

    val verticesLeft = partitioning.elements.filter { partitioning[it].size == 1 }
//    println("Vertices with size 1 critical clique: ${verticesLeft.size}")

    partitioning -= verticesLeft
    partitioning.disjointUnion(partitionWithHash(verticesLeft, { vHashOpen(problem.g, it) }, { neighborSetOf(problem.g, it) }))
    return partitioning
}

fun <V> critCliqueMerge(g: Graph<V, DefaultEdge>, partitioning: SetPartitioning<V>, vertices: Collection<V>) {
    for (v1 in vertices)
        for (v2 in neighborListOf(g, v1))
            if (partitioning[v1] !== partitioning[v2] && g.closedNBEqualsFast(v1, v2)) {
                partitioning.merge(v1, v2)
//                println("Crit. CLIQUES merged:".padEnd(pad) + "size " + partitioning[v1].size)
            }
}

fun <V> critISMerge(g: Graph<V, DefaultEdge>, partitioning: SetPartitioning<V>, vertices: Collection<V>) {
    for (v1 in vertices) {
        val middleVertex: V? = neighborListOf(g, v1).minBy { g.degreeOf(it) }
        if (middleVertex != null)
            for (v2 in neighborListOf(g, middleVertex))
                if (partitioning[v1] !== partitioning[v2] && g.openNBEqualsFast(v1, v2)) {
                    partitioning.merge(v1, v2)
//                    println("Crit. IS merged:".padEnd(pad) + "size " + partitioning[v1].size)
                }
    }
}