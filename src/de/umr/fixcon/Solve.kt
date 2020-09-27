package de.umr.fixcon

import de.umr.core.*
import de.umr.core.dataStructures.SegmentedList
import de.umr.core.dataStructures.Solution
import de.umr.core.dataStructures.expandSubgraph
import de.umr.core.dataStructures.vertexCount
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import de.umr.searchTreeNodes
import org.jgrapht.Graph
import org.jgrapht.Graphs
import org.jgrapht.graph.DefaultEdge
import java.lang.System.currentTimeMillis

fun solve(g: Graph<Int, DefaultEdge>, f: AbstractGraphFunction, timeLimit: Int = Int.MAX_VALUE): Pair<Solution<Int>, Double> {

    /**Time tracking*/
    val startTime = currentTimeMillis()
    fun secondsElapsed(): Double = (((currentTimeMillis() - startTime) / 1000.0))

    /**Analysis*/
    printFullAnalysis(g)

    /**Preparation*/
    removeComponentsSmallerThreshold(g, f.k)
    val sol = getHeuristic(g, f).also { println("Heuristic: $it") }
    if (sol.value == f.globalOptimum()) return (sol to secondsElapsed()).also { println("Heuristic was optimal") }

    /**Partitioning of the vertices into critical cliques and critical independent sets.*/
    val criticalPartition = getCriticalPartitioning(g)

    while (sol.value < f.globalOptimum() && g.vertexCount >= f.k) {

        val startVertex = criticalPartition.subsets.maxByOrNull { it.size }!!.first()
        val subgraph = fromVertices(startVertex)
        val vertexStack = mutableListOf(startVertex)
        val extension = SegmentedList<Int>().apply { this += Graphs.neighborListOf(g, startVertex) }
        val pointers = mutableListOf(0)

        fun numVerticesMissing() = f.k - subgraph.vertexCount

        fun extendableVertices() = HashSet<Int>().apply {
            for (i in vertexStack.indices.reversed())
                if (extension.segments[i] > pointers.last())
                    add(vertexStack[i])
                else break
        }

        fun cliqueJoinRule(): Int {
            val newIDs = (-1 downTo -numVerticesMissing()).toSet()
            addAsClique(subgraph, newIDs)
            connectVertexSets(subgraph, extendableVertices(), newIDs)
            return f.eval(subgraph).also { subgraph.removeAllVertices(newIDs) }
        }

        while (pointers.isNotEmpty()) {
            if (pointers.last() >= extension.size || numVerticesMissing() == 0 || (vertexAdditionRule(subgraph, sol, f)) || (f.edgeMonotone && cliqueJoinRule() <= sol.value)) {
                if (numVerticesMissing() != 0) extension.removeLastSegment()
                val v = vertexStack.removeAt(vertexStack.size - 1)
                subgraph.removeVertex(v)
                pointers.removeAt(pointers.size - 1)
            } else {
                if (secondsElapsed() >= timeLimit) return Pair(sol, secondsElapsed())
                if (++searchTreeNodes % 1_000_000 == 0L) println("SearchTree-nodes in million: ${searchTreeNodes / 1_000_000}")

                val nextVertex = extension[pointers.last()]
                if (numVerticesMissing() > 1) extension += Graphs.neighborListOf(g, nextVertex).filter { it !in extension && it != startVertex }
                subgraph.expandSubgraph(g, nextVertex)
                vertexStack.add(nextVertex)
                pointers[pointers.size - 1] += 1
                pointers.add(pointers.last())
            }
            if (numVerticesMissing() == 0) sol.updateIfBetter(subgraph, f.eval(subgraph))
        }

        deleteUpdateCritSet(g, criticalPartition, startVertex)
    }
    return Pair(sol, secondsElapsed())
}

fun <V> vertexAdditionRule(curr: Graph<V, DefaultEdge>, currentBestSol: Solution<V>, f: AbstractGraphFunction) =
        f.eval(curr) + f.completeBound(curr) <= currentBestSol.value