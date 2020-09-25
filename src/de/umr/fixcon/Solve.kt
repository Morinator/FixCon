package de.umr.fixcon

import de.umr.core.*
import de.umr.core.dataStructures.*
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import de.umr.searchTreeNodes
import org.jgrapht.Graph
import org.jgrapht.Graphs
import org.jgrapht.graph.DefaultEdge
import java.lang.System.currentTimeMillis

fun runSearchTree(g: Graph<Int, DefaultEdge>, f: AbstractGraphFunction, start: Int, sol: Solution<Int> = Solution()) {

    val subgraph = fromVertices(start)
    val vertexStack = mutableListOf(start)

    val extension = SegmentedList<Int>().apply { this += Graphs.neighborListOf(g, start) }
    val pointers = mutableListOf(0)

    fun numVerticesMissing() = f.k - subgraph.vertexCount
    fun isValid() = numVerticesMissing() == 0

    fun extendableVertices() = HashSet<Int>().apply {
        for (i in vertexStack.indices.reversed())
            if (extension.segments[i] > pointers.last())
                add(vertexStack[i])
            else break
    }

    fun cliqueJoinRule(): Int {
        val newIDs = (-1 downTo -(f.k - subgraph.vertexCount)).toSet()
        addAsClique(subgraph, newIDs)
        connectVertexSets(subgraph, extendableVertices(), newIDs)
        return f.eval(subgraph).also { subgraph.removeAllVertices(newIDs) }
    }

    while (pointers.isNotEmpty()) {
        if (pointers.last() >= extension.size || isValid() || (vertexAdditionRule(subgraph, sol, f)) || (f.edgeMonotone && cliqueJoinRule() <= sol.value)) {
            if (!isValid()) extension.removeLastSegment()
            val v = vertexStack.removeAt(vertexStack.size - 1)
            subgraph.removeVertex(v)
            pointers.removeAt(pointers.size - 1)
        } else {
            if (++searchTreeNodes % 1_000_000 == 0L) println("SearchTree-nodes in million: ${searchTreeNodes / 1_000_000}")
            val nextVertex = extension[pointers.last()]
            if (numVerticesMissing() > 1) extension += Graphs.neighborListOf(g, nextVertex).filter { it !in extension && it != start }
            subgraph.expandSubgraph(g, nextVertex)
            vertexStack.add(nextVertex)
            pointers[pointers.size - 1] += 1
            pointers.add(pointers.last())
        }
        if (isValid()) sol.updateIfBetter(subgraph, f.eval(subgraph))
    }
}

fun solve(g: Graph<Int, DefaultEdge>, f: AbstractGraphFunction, timeLimit: Int = Int.MAX_VALUE): Pair<Solution<Int>, Double> {

    val startTime = currentTimeMillis()
    fun secondsElapsed(): Double = (((currentTimeMillis() - startTime) / 1000.0))

    printFullAnalysis(g)

    removeComponentsSmallerThreshold(g, f.k)

    val sol = getHeuristic(g, f).also { println("Heuristic: $it") }
    if (sol.value == f.globalOptimum()) return (sol to secondsElapsed()).also { println("Heuristic was optimal") }

    val criticalPartition = getCriticalPartitioning(g)

    while (sol.value < f.globalOptimum() && g.vertexCount >= f.k && secondsElapsed() < timeLimit) {
        val startVertex = criticalPartition.subsets.maxByOrNull { it.size }!!.first()

        runSearchTree(g, f, startVertex, sol)

        deleteUpdateCritSet(g, criticalPartition, startVertex)
    }
    return sol to secondsElapsed()
}

fun <V> vertexAdditionRule(curr: Graph<V, DefaultEdge>, currentBestSol: Solution<V>, f: AbstractGraphFunction) =
        f.eval(curr) + f.completeBound(curr) <= currentBestSol.value