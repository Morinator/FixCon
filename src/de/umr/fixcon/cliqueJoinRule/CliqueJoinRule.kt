package de.umr.fixcon.cliqueJoinRule

import de.umr.core.dataStructures.unorderedPairs
import de.umr.core.extensions.addEdgeWithVertices
import de.umr.core.extensions.vertexCount
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun cliqueJoinRule(p: Problem<Int>, curr: Graph<Int, DefaultEdge>, bestSolution: Solution<Int>): Boolean {
    val cliqueVertices = unusedVertexSet(curr, p.f.k - curr.vertexCount)
    val extendableVertices = extendableVertices(curr, p.g)

    addAsClique(curr, cliqueVertices)
    connectVertexSets(p.g, cliqueVertices, extendableVertices)

    val applicable = p.eval(curr) <= bestSolution.value

    curr.removeAllVertices(cliqueVertices)
    return applicable
}

fun extendableVertices(subgraph: Graph<Int, DefaultEdge>, mainGraph: Graph<Int, DefaultEdge>): Set<Int> =
        subgraph.vertexSet().filterTo(HashSet(), { subgraph.degreeOf(it) != mainGraph.degreeOf(it) })

fun <V> connectVertexSets(g: Graph<V, DefaultEdge>, vCol1: Collection<V>, vCol2: Collection<V>) {
    for (v1 in vCol2) for (v2 in vCol1) g.addEdgeWithVertices(v1, v2)
}

fun <V> addAsClique(g: Graph<V, DefaultEdge>, newCliqueVertices: Set<V>) {
    for (pair in unorderedPairs(newCliqueVertices)) {
        g.addEdgeWithVertices(pair.first, pair.second)
    }
}

/**@return A [Set] of [num] vertex-IDs that are NOT already used in [g].*/
fun unusedVertexSet(g: Graph<Int, DefaultEdge>, num: Int): Set<Int> {
    val firstNewID = g.vertexSet().max()!! + 1
    return (firstNewID until firstNewID + num).toSet()
}