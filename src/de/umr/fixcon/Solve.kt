package de.umr.fixcon

import de.umr.core.*
import de.umr.core.dataStructures.*
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import de.umr.searchTreeNodes
import de.umr.useHeuristic
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.graph.DefaultEdge
import java.lang.System.currentTimeMillis

fun solve(g: Graph<Int, DefaultEdge>, f: AbstractGraphFunction, timeLimit: Int = Int.MAX_VALUE): Pair<Solution<Int>, Double> {

    //##### Time tracking
    val startTime = currentTimeMillis()
    fun secondsElapsed(): Double = (((currentTimeMillis() - startTime) / 1000.0))

    //##### Analysis
    printFullAnalysis(g)

    //##### Preparation & Heuristic
    removeComponentsSmallerThreshold(g, f.k)
    val sol = if (useHeuristic) getHeuristic(g, f).also { println("Heuristic: $it") } else Solution()
    println("Heuristic finished after ${secondsElapsed()} seconds.")
    if (sol.value == f.globalOptimum()) return (sol to secondsElapsed()).also { println("Heuristic was optimal") }

    //##### Partitioning of the vertices into critical cliques and critical independent sets.
    val critPartition = getCriticalPartitioning(g)

    //##### main loop
    while (sol.value < f.globalOptimum() && g.vertexCount >= f.k) {

        val startVertex = critPartition.subsets.maxByOrNull { it.size }!!.first()
        val subgraph = OrderedGraph<Int>().apply { addVertex(startVertex) }
        fun numVerticesMissing() = f.k - subgraph.vertexCount

        val extension = SegmentedList<Int>().apply { this += neighborListOf(g, startVertex) }
        val pointers = mutableListOf(0)

        val visitedTwins = SetStack<Int>()

        fun cliqueList() = (-1 downTo -numVerticesMissing()).toList()
        val cliqueCompanion = fromVertices(startVertex).apply { addAsClique(this, cliqueList()) }

        fun extendable() = HashSet<Int>().apply {
            for (i in pointers.indices.reversed())
                if (extension.segments[i] > pointers.last()) add(subgraph.orderedVertices[i])
                else break
        }

        fun cliqueJoinRule(): Boolean {
            connectVertexSets(cliqueCompanion, extendable(), cliqueList())
            val isApplicable = f.eval(cliqueCompanion) <= sol.value
            disconnectVertexSets(cliqueCompanion, extendable(), cliqueList())
            return isApplicable
        }

        while (pointers.isNotEmpty()) {  //##### loops through search-trees
            if (pointers.last() >= extension.size || numVerticesMissing() == 0 || (vertexAdditionRule(subgraph, sol, f)) || f.edgeMonotone && cliqueJoinRule()) { //##### backtracking
                if (numVerticesMissing() != 0) extension.removeLastSegment()
                val poppedVertex = subgraph.removeLastVertex()

                visitedTwins.removeLast()
                if (visitedTwins.size > 0) visitedTwins.addToLast(critPartition[poppedVertex])

                cliqueCompanion.removeVertex(poppedVertex)
                cliqueCompanion.addVertex(-numVerticesMissing())
                connectVertexSets(cliqueCompanion, listOf(-numVerticesMissing()), (-1 downTo (-numVerticesMissing() + 1)).toList())

                pointers.removeAt(pointers.size - 1)

            } else if (extension[pointers.last()] in visitedTwins) {    //##### horizontal skip because of critical twin
                pointers[pointers.size - 1]++
                println("critical twin skipped in search-tree")

            } else {    //##### branch into new search-tree node
                if (secondsElapsed() >= timeLimit) return Pair(sol, secondsElapsed())
                if (++searchTreeNodes % 1_000_000 == 0L) println("SearchTree-nodes in million: ${searchTreeNodes / 1_000_000}")

                val nextVertex = extension[pointers.last()]
                if (numVerticesMissing() > 1) extension += neighborListOf(g, nextVertex).filter { it !in extension && it != startVertex }

                visitedTwins.push(emptyList())

                subgraph.expandSubgraph(g, nextVertex)

                cliqueCompanion.expandSubgraph(g, nextVertex)
                cliqueCompanion.removeVertex(cliqueCompanion.vertexSet().minOrNull())

                pointers[pointers.size - 1]++
                pointers.add(pointers.last())
            }
            if (numVerticesMissing() == 0) sol.updateIfBetter(subgraph, f.eval(subgraph))
        }

        deleteUpdateCritSet(g, critPartition, startVertex)
    }
    return Pair(sol, secondsElapsed())
}

fun <V> vertexAdditionRule(curr: Graph<V, DefaultEdge>, currentBestSol: Solution<V>, f: AbstractGraphFunction) =
        f.eval(curr) + f.completeBound(curr) <= currentBestSol.value