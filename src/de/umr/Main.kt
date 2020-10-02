package de.umr

import de.umr.core.*
import de.umr.core.dataStructures.*
import de.umr.fixcon.deleteUpdateCritSet
import de.umr.fixcon.getCriticalPartitioning
import de.umr.fixcon.getHeuristic
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import de.umr.fixcon.graphFunctions.graphFunctionByID
import org.jgrapht.Graph
import org.jgrapht.Graphs
import org.jgrapht.graph.DefaultEdge
import java.io.File
import java.nio.file.Files.createDirectories
import java.nio.file.Paths

/**args[0] == File-Path for the graph.
 * args[1] == k
 * args[2] == functionID, param1, param2, ..., paramN-1, paramN
 * args[3] == time limit in seconds*/

//##### Global Settings
const val paddingRight = 25
const val defaultEdgeWeight = 1.0
const val useHeuristic = false

//##### Globals
var searchTreeNodes: Long = 0
var vertexAdditionRuleSkips: Long = 0
var cliqueJoinRuleSkips: Long = 0
val criticalTwinSkips: Long = 0

fun main(args: Array<String>) {

    //##### Reading of Command-Line arguments
    val graph = graphFromFile(file = args[0]).also { g -> require(g.vertexSet().all { v -> v >= 0 }) }
    val k = args[1].toInt()
    val funcID = args[2].split(",").first().toInt()
    val funcParams = args[2].split(",").drop(1).map { it.toInt() }
    val vertexCount = graph.vertexCount
    val edgeCount = graph.edgeCount

    //##### run algorithm
    val (solution, usedTime) = solve(graph, f = graphFunctionByID(funcID, k, funcParams), timeLimit = args[3].toInt())

    //##### logging results
    createDirectories(Paths.get("results"))
    File("results/${File(args[0]).name}.$k.$funcID.fixcon").writeText("${funcID};${funcParams.joinToString(",")}".padStart(15) + File(args[0]).name.padStart(40) + vertexCount.toString().padStart(7) + edgeCount.toString().padStart(9) + k.toString().padStart(4) + usedTime.toString().padStart(14) + solution.value.toString().padStart(6) + ("Nodes: $searchTreeNodes").padStart(20) + "     " + solution.subgraph.vertexSet().toString() + "\n")
}

fun solve(g: Graph<Int, DefaultEdge>, f: AbstractGraphFunction, timeLimit: Int = Int.MAX_VALUE): Pair<Solution<Int>, Double> {

    //##### Time tracking
    val startTime = System.currentTimeMillis()
    fun secondsElapsed(): Double = (((System.currentTimeMillis() - startTime) / 1000.0))

    //##### Analysis
    printFullAnalysis(g)

    //##### Preparation & Heuristic
    removeComponentsSmallerThreshold(g, f.k)
    val sol = if (useHeuristic) getHeuristic(g, f).also { println("Heuristic: $it") } else Solution()
    println("Heuristic:".padEnd(paddingRight) + "${"%.1f".format(secondsElapsed())} sec.")
    if (sol.value == f.globalOptimum()) return (sol to secondsElapsed()).also { println("Heuristic was optimal") }

    //##### Partitioning of the vertices into critical cliques and critical independent sets.
    val critPartition = getCriticalPartitioning(g)

    //##### main loop
    while (sol.value < f.globalOptimum() && g.vertexCount >= f.k) {

        val startVertex = critPartition.subsets.maxByOrNull { it.size }!!.first()
        val subgraph = OrderedGraph<Int>().apply { addVertex(startVertex) }
        fun numVerticesMissing() = f.k - subgraph.vertexCount

        val extension = SegmentedList<Int>().apply { this += Graphs.neighborListOf(g, startVertex) }
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
            if (!f.edgeMonotone) return false
            connectVertices(cliqueCompanion, extendable(), cliqueList())
            val isApplicable = f.eval(cliqueCompanion) <= sol.value
            disconnectVertices(cliqueCompanion, extendable(), cliqueList())
            return isApplicable.also { if (it) cliqueJoinRuleSkips++ }
        }

        while (pointers.isNotEmpty()) {  //##### loops through search-trees
            if (pointers.last() >= extension.size || numVerticesMissing() == 0 || (vertexAdditionRule(subgraph, sol, f)) || cliqueJoinRule()) {
                if (numVerticesMissing() != 0) extension.removeLastSegment()
                val poppedVertex = subgraph.removeLastVertex()

                visitedTwins.removeLast()
                if (visitedTwins.size > 0) visitedTwins.addToLast(critPartition[poppedVertex])

                cliqueCompanion.removeVertex(poppedVertex)
                cliqueCompanion.addVertex(-numVerticesMissing())
                connectVertices(cliqueCompanion, listOf(-numVerticesMissing()), (-1 downTo (-numVerticesMissing() + 1)).toList())

                pointers.removeAt(pointers.size - 1)

            } else if (extension[pointers.last()] in visitedTwins) {
                pointers[pointers.size - 1]++
                println("critical twin skipped in search-tree")

            } else {
                if (secondsElapsed() >= timeLimit) return Pair(sol, secondsElapsed())
                if (++searchTreeNodes % 1_000_000 == 0L) println("SearchTree-nodes in million: ${searchTreeNodes / 1_000_000}")

                val nextVertex = extension[pointers.last()]
                if (numVerticesMissing() > 1) extension += Graphs.neighborListOf(g, nextVertex).filter { it !in extension && it != startVertex }

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

    println("Vertex addition rule:".padEnd(paddingRight) + vertexAdditionRuleSkips)
    println("Clique join rule:".padEnd(paddingRight) + cliqueJoinRuleSkips)
    println("Critical twins".padEnd(paddingRight) + criticalTwinSkips)

    return Pair(sol, secondsElapsed())
}

fun <V> vertexAdditionRule(curr: Graph<V, DefaultEdge>, currentBestSol: Solution<V>, f: AbstractGraphFunction): Boolean {
    val result = f.eval(curr) + f.completeBound(curr) <= currentBestSol.value
    if (result) vertexAdditionRuleSkips++
    return result
}