package de.umr.fixcon

import de.umr.core.extensions.vertexCount
import de.umr.core.pad
import de.umr.core.removeSmallComponents
import de.umr.fixcon.heuristic.Heuristic
import de.umr.fixcon.itarators.SimpleIter
import de.umr.fixcon.twins.critCliques
import de.umr.fixcon.twins.critIS
import de.umr.fixcon.twins.pruneBigSubsets

fun <V> solve(p: Problem<V>): Solution<V> {
    removeSmallComponents(p.g, p.function.k)

    val sol = Heuristic(p).get()
    if (sol.value == p.function.globalOptimum()) return sol

    val critCliques = critCliques(p)
    critIS(p, critCliques)
    pruneBigSubsets(critCliques, p)

    var iteratorsUsed = 0
    while (sol.value < p.function.globalOptimum() && p.g.vertexCount >= p.function.k) {
        val startVertex = critCliques.subsets().maxBy { it.size }!!.random()
        val iterator = SimpleIter(p, startVertex, sol)

        while (iterator.isValid) iterator.mutate()

        println("BAD VERTICES: ${critCliques[startVertex].size}")
        p.g.removeAllVertices(critCliques[startVertex])
        critCliques.removeSubset(startVertex)
        iteratorsUsed++
    }
    return sol.also { println("Iterators used:".padEnd(pad) + iteratorsUsed) }
}

