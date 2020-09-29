package de.umr.fixcon

import de.umr.core.*
import de.umr.core.dataStructures.SegmentedList
import de.umr.core.dataStructures.Solution
import de.umr.core.dataStructures.expandSubgraph
import de.umr.core.dataStructures.vertexCount
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import de.umr.searchTreeNodes
import de.umr.useHeuristic
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborListOf
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
    val sol = if (useHeuristic) getHeuristic(g, f).also { println("Heuristic: $it") } else Solution()
    if (sol.value == f.globalOptimum()) return (sol to secondsElapsed()).also { println("Heuristic was optimal") }

    /**Partitioning of the vertices into critical cliques and critical independent sets.*/
    val criticalPartition = getCriticalPartitioning(g)

    while (sol.value < f.globalOptimum() && g.vertexCount >= f.k) {

        val startVertex = criticalPartition.subsets.maxByOrNull { it.size }!!.first()
        val subgraph = fromVertices(startVertex)
        val vertexStack = mutableListOf(startVertex)

        val extension = SegmentedList<Int>().apply { this += neighborListOf(g, startVertex) }
        val pointers = mutableListOf(0)

        val twinStack = ArrayDeque<MutableSet<Int>>().apply { add(HashSet()) }
        val twinSet = HashSet<Int>()

        fun numVerticesMissing() = f.k - subgraph.vertexCount

        fun extendableVertices() = HashSet<Int>().apply {
            for (i in pointers.indices.reversed())
                if (extension.segments[i] > pointers.last())
                    add(vertexStack[i])
                else break
        }

        fun cliqueJoinRule(): Boolean {
            val newIDs = (-1 downTo -numVerticesMissing()).toSet()
            addAsClique(subgraph, newIDs)
            connectVertexSets(subgraph, extendableVertices(), newIDs)
            return f.eval(subgraph).also { subgraph.removeAllVertices(newIDs) } <= sol.value
        }

        while (pointers.isNotEmpty()) {

            if (pointers.last() >= extension.size || numVerticesMissing() == 0 || (vertexAdditionRule(subgraph, sol, f)) || (f.edgeMonotone && cliqueJoinRule())) {
                if (numVerticesMissing() != 0) extension.removeLastSegment()
                val v = vertexStack.removeAt(vertexStack.size - 1)

                twinSet.removeAll(twinStack.removeLast())
                if (twinStack.isNotEmpty()) {
                    twinStack.last().addAll(criticalPartition[v])
                    twinSet.addAll(criticalPartition[v])
                }

                subgraph.removeVertex(v)
                pointers.removeAt(pointers.size - 1)

            } else if (extension[pointers.last()] in twinSet) {
                pointers[pointers.size - 1] += 1
                println("bla")

            } else {
                if (secondsElapsed() >= timeLimit) return Pair(sol, secondsElapsed())
                if (++searchTreeNodes % 1_000_000 == 0L) println("SearchTree-nodes in million: ${searchTreeNodes / 1_000_000}")

                val nextVertex = extension[pointers.last()]
                if (numVerticesMissing() > 1) extension += neighborListOf(g, nextVertex).filter { it !in extension && it != startVertex }

                twinStack.addLast(HashSet())

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