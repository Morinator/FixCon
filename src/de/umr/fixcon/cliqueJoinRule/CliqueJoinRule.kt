package de.umr.fixcon.cliqueJoinRule

import de.umr.core.*
import de.umr.core.extensions.vertexCount
import de.umr.fixcon.Problem
import de.umr.fixcon.cliqueJoinChance
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import kotlin.random.Random

fun cliqueJoinRule(sub: Graph<Int, DefaultEdge>, p: Problem<Int>, bestVal: Int) = Random.nextDouble() < cliqueJoinChance && cliqueJoinValue(sub, p) <= bestVal

fun cliqueJoinValue(sub: Graph<Int, DefaultEdge>, p: Problem<Int>): Int {
    val newIDs = getNewVertexIDs(sub, p.f.k - sub.vertexCount)
    val extendable = extendableVertices(sub, p.g)
    addAsClique(sub, newIDs)
    connectVertexSets(sub, extendable, newIDs)

    val result = p.eval(sub)
    sub.removeAllVertices(newIDs)
    return result
}