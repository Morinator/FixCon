package de.umr.fixcon

import de.umr.core.dataStructures.Partitioning
import de.umr.core.dataStructures.closedNB
import de.umr.core.dataStructures.closedNBEqualsFast
import de.umr.core.dataStructures.openNBEqualsFast
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge

fun <V> getCriticalPartitioning(problem: Problem<V>): Partitioning<V> {

    /**Partitions the [vertices] of the *graph* in [problem] into a [Partitioning].
     * The vertices in each subset of the [Partitioning] have equal neighbours,
     * either closed or open, respective what is provided as [nbSelector].*/
    fun partitionWithHash(vertices: Collection<V>, hashFu: (V) -> List<Int>, nbSelector: (V) -> Collection<V>): Partitioning<V> =
            Partitioning<V>().apply {
                for (verticesByHash in vertices.groupBy { hashFu(it) }.values)
                    addByEQPredicate(verticesByHash) { x, y -> nbSelector(x) == nbSelector(y) }
            }

    val partitioning = partitionWithHash(problem.g.vertexSet(), { vHashClosed(problem.g, it) }, { problem.g.closedNB(it) })

    val remainingVertices = partitioning.elements.filter { partitioning[it].size == 1 }

    partitioning -= remainingVertices
    partitioning.disjointUnion(partitionWithHash(remainingVertices, { vHashOpen(problem.g, it) }, { neighborSetOf(problem.g, it) }))
    return partitioning
}

fun <V> critCliqueMerge(g: Graph<V, DefaultEdge>, partitioning: Partitioning<V>, vertices: Collection<V>) {
    for (v in vertices)
        for (nb in neighborListOf(g, v))
            if (partitioning[v] !== partitioning[nb] && g.closedNBEqualsFast(v, nb))
                partitioning.merge(v, nb)
}

fun <V> critISMerge(g: Graph<V, DefaultEdge>, partitioning: Partitioning<V>, vertices: Collection<V>) {
    for (v1 in vertices) {
        val middleVertex: V? = neighborListOf(g, v1).minBy { g.degreeOf(it) }
        if (middleVertex != null)
            for (v2 in neighborListOf(g, middleVertex))
                if (partitioning[v1] !== partitioning[v2] && g.openNBEqualsFast(v1, v2))
                    partitioning.merge(v1, v2)
    }
}

private fun <V> vHashHelper(graph: Graph<V, DefaultEdge>, v: V, nbSelector: (V) -> Set<V>) =
        listOf(graph.degreeOf(v), nbSelector(v).sumBy { graph.degreeOf(it) }, nbSelector(v).sumBy { it.hashCode() })

fun <V> vHashClosed(graph: Graph<V, DefaultEdge>, v: V) = vHashHelper(graph, v, { graph.closedNB(it) })

fun <V> vHashOpen(graph: Graph<V, DefaultEdge>, v: V) = vHashHelper(graph, v, { neighborSetOf(graph, it) })