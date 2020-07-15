package de.umr.fixcon.twins

import de.umr.core.dataStructures.SetPartitioning
import de.umr.core.extensions.closedNB
import de.umr.core.extensions.closedNBEquals
import de.umr.core.extensions.openNB
import de.umr.fixcon.Problem
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun <V> getCriticalPartitioning(problem: Problem<V>): SetPartitioning<V> {

    /**Partitions the [vertices] of the *graph* in [problem] into a [SetPartitioning].
     * The vertices in each subset of the [SetPartitioning] have equal neighbours,
     * either closed or open, respective what is provided as [nbSelector].*/
    fun partitionWithHash(vertices: Collection<V>, hashFu: (V) -> List<Int>, nbSelector: (V) -> Set<V>): SetPartitioning<V> =
            SetPartitioning<V>().apply {
                for (verticesByHash in vertices.groupBy { hashFu(it) }.values)
                    addByEQPredicate(verticesByHash) { x, y -> nbSelector(x) == nbSelector(y) }
            }

    val partitioning = partitionWithHash(problem.g.vertexSet(), { vHashClosed(problem.g, it) }, { problem.g.closedNB(it) })

    val verticesLeft = partitioning.elements.filter { partitioning[it].size == 1 }
    println("Vertices with size 1 critical clique: ${verticesLeft.size}")

    partitioning -= verticesLeft
    partitioning.disjointUnion(partitionWithHash(verticesLeft, { vHashOpen(problem.g, it) }, { problem.g.openNB(it) }))
    return partitioning
}

 fun <V> critCliqueMerge(g: Graph<V, DefaultEdge>, partitioning: SetPartitioning<V>, vertices: Collection<V>) {

    for (v1 in vertices)
        for (v2 in g.openNB(v1))
            if (g.closedNBEquals(v1, v2)) {
                if (partitioning[v1] != partitioning[v2]) println("Merged disjoint critical cliques")
                partitioning.merge(v1, v2)
            }
}