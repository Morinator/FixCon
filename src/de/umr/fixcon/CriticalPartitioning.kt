package de.umr.fixcon

import de.umr.core.dataStructures.*
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge

fun <V> getCriticalPartitioning(g: Graph<V, DefaultEdge>): Pair<Partitioning<V>, Set<V>> {

    /**Partitions the [vertices] of [g]. The vertices in each subset of the [Partitioning] have
     * equal neighbours, either closed or open, respective to what is provided as [nbSelector].*/
    fun partitionWithHash(vertices: Collection<V>, hashFu: (V) -> List<Int>, nbSelector: (V) -> Collection<V>): Partitioning<V> =
            Partitioning<V>().apply { vertices.groupBy { hashFu(it) }.values.forEach { addByEQPredicate(it) { x, y -> nbSelector(x) == nbSelector(y) } } }

    fun <V> vHashHelper(graph: Graph<V, DefaultEdge>, v: V, nbSelector: (V) -> Set<V>) =
            listOf(graph.degreeOf(v), nbSelector(v).sumBy { graph.degreeOf(it) }, nbSelector(v).sumBy { it.hashCode() })

    val partitioning = partitionWithHash(g.vertexSet(), { vHashHelper(g, it, { v -> g.closedNB(v) }) }, { g.closedNB(it) })

    val remainingVertices = partitioning.elements.filter { partitioning[it].size == 1 }.toSet()

    partitioning -= remainingVertices
    partitioning.disjointUnion(partitionWithHash(remainingVertices, { vHashHelper(g, it, { v -> neighborSetOf(g, v) }) }, { neighborSetOf(g, it) }))
    return partitioning to remainingVertices
}

fun <V> critCliqueMerge(g: Graph<V, DefaultEdge>, partitioning: Partitioning<V>, vertices: Collection<V>) {
    for (v in vertices)
        for (nb in neighborListOf(g, v))
            if (partitioning[v] !== partitioning[nb] && g.closedNBEqualsFast(v, nb))
                partitioning.merge(v, nb)
}

fun <V> critISMerge(g: Graph<V, DefaultEdge>, partitioning: Partitioning<V>, vertices: Collection<V>) {
    for (v1 in vertices) {
        val middleVertex: V? = neighborListOf(g, v1).minByOrNull { g.degreeOf(it) }
        if (middleVertex != null)
            for (v2 in neighborListOf(g, middleVertex))
                if (partitioning[v1] !== partitioning[v2] && g.openNBEqualsFast(v1, v2))
                    partitioning.merge(v1, v2)
    }
}

fun deleteUpdateTwinSet(g: Graph<Int, DefaultEdge>, criticalPartition: Partitioning<Int>, startVertex: Int) {
    val nbVertices = g.neighbours(criticalPartition[startVertex])
    g.removeAllVertices(criticalPartition[startVertex])
    criticalPartition.removeSubset(startVertex)
    critCliqueMerge(g, criticalPartition, nbVertices)
    critISMerge(g, criticalPartition, nbVertices)
}