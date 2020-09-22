package de.umr

import de.umr.core.extensions.edgeCount
import de.umr.core.extensions.vertexCount
import de.umr.core.io.graphFromFile
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import de.umr.fixcon.graphFunctions.graphFunctionByID
import de.umr.fixcon.solve
import java.io.File
import java.lang.System.currentTimeMillis
import java.nio.file.Files.createDirectories
import java.nio.file.Paths
import java.util.concurrent.*
import java.util.concurrent.Executors.newSingleThreadExecutor
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.system.exitProcess

/**args[0] == File-Path for the graph.
 * args[1] == k
 * args[2] == functionID, param1, param2, ..., paramN-1, paramN
 * args[3] == time limit in seconds
 */

var searchTreeNodes : Long = 0

fun main(args: Array<String>) {
    val graph = graphFromFile(args[0])
    val graphName = File(args[0]).name
    val vertexCount = graph.vertexCount
    val edgeCount = graph.edgeCount

    val k = args[1].toInt()
    val funcID = args[2].split(",").first().toInt()
    val funcParams = args[2].split(",").drop(1).map { it.toInt() }
    val timeLimit = args[3].toLong()
    val fu = graphFunctionByID(funcID, k, funcParams)

    createDirectories(Paths.get("results"))

    try {
        val timeBefore = currentTimeMillis()
        val result = newSingleThreadExecutor().submit<Solution<Int>> { solve(Problem(graph, fu)) }.get(timeLimit, SECONDS)
        val secondsElapsed = (currentTimeMillis() - timeBefore) / 1000.0
        File("results/$graphName.$k.$funcID.fixcon").writeText("${funcID};${funcParams.joinToString(",")}".padStart(15) + graphName.padStart(40) + vertexCount.toString().padStart(7) + edgeCount.toString().padStart(9) + k.toString().padStart(4) + secondsElapsed.toString().padStart(9) + result.value.toString().padStart(6) + ("Nodes: $searchTreeNodes").padStart(20) + "     " + result.subgraph.vertexSet().toString() + "\n")
    } catch (e: TimeoutException) {
        File("results/$graphName.$k.$funcID.fixcon").writeText("${funcID};${funcParams.joinToString(",")}".padStart(15) + graphName.padStart(40) + vertexCount.toString().padStart(7) + edgeCount.toString().padStart(9) + k.toString().padStart(4) + "     timeout\n")
    }

    newSingleThreadExecutor().shutdown()
    exitProcess(0)
}