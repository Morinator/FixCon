package de.umr.fixcon.cliqueJoinRule

import de.umr.core.dataStructures.unorderedPairs
import de.umr.core.extensions.addEdgeWithVertices
import de.umr.core.extensions.vertexCount
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun cliqueJoinRule(sub: Graph<Int, DefaultEdge>, p: Problem<Int>, bestVal : Int) =
        cliqueJoinValue(sub, p) <= bestVal

fun cliqueJoinValue(sub: Graph<Int, DefaultEdge>, p: Problem<Int>): Int {
    val newIDs = getNewVertexIDs(sub, p.f.k - sub.vertexCount)
    val extendableVertices = extendableVertices(sub, p.g)

    addAsClique(sub, newIDs)
    connectVertexSets(sub, extendableVertices, newIDs)

    val result = p.eval(sub)
    sub.removeAllVertices(newIDs)
    return result
}

/**The vertices and edges from [subgraph] need to be subsets from [mainGraph].
 *
 * @return The [Set] of all vertices whose degree in [subgraph] is smaller than in [mainGraph].
 */
fun extendableVertices(subgraph: Graph<Int, DefaultEdge>, mainGraph: Graph<Int, DefaultEdge>): Set<Int> =
        subgraph.vertexSet().filterTo(HashSet(), { subgraph.degreeOf(it) < mainGraph.degreeOf(it) })

/***/
fun <V> connectVertexSets(g: Graph<V, DefaultEdge>, vCol1: Collection<V>, vCol2: Collection<V>) {
    for (v1 in vCol1)
        for (v2 in vCol2)
            g.addEdge(v1, v2)
}

fun <V> addAsClique(g: Graph<V, DefaultEdge>, newCliqueVertices: Set<V>) {
    g.addVertex(newCliqueVertices.first())  //in case it has only one element no pairs for edges can be found
    unorderedPairs(newCliqueVertices).forEach { g.addEdgeWithVertices(it.first, it.second) }
}

/**@return A [Set] of [num] vertex-IDs that are NOT already used in [g].*/
fun getNewVertexIDs(g: Graph<Int, DefaultEdge>, num: Int): Set<Int> {
    val firstNewID = g.vertexSet().max()!! + 1
    return (firstNewID until firstNewID + num).toSet()
}