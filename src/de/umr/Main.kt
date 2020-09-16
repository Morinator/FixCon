package de.umr

import de.umr.core.extensions.edgeCount
import de.umr.core.extensions.vertexCount
import de.umr.core.io.graphFromFile
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import de.umr.fixcon.graphFunctions.graphFunctionByID
import de.umr.fixcon.solve
import java.io.File
import java.nio.file.Files
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
fun main(args: Array<String>) {

    val k = args[1].toInt()
    val funcID = args[2].split(",").first().toInt()
    val funcParams = args[2].split(",").drop(1).map { it.toInt() }
    val timeLimit = args[3].toLong()
    val graphName = File(args[0]).name
    val fu = graphFunctionByID(funcID, k, funcParams)
    val graph = graphFromFile(args[0])
    val vertexCount = graph.vertexCount
    val edgeCount = graph.edgeCount

    Files.createDirectories(Paths.get("results"))

    try {
        val timeBefore = System.currentTimeMillis()
        val result = newSingleThreadExecutor().submit<Solution<Int>> { solve(Problem(graph, fu)) }.get(timeLimit, SECONDS)
        val secondsElapsed = (System.currentTimeMillis() - timeBefore) / 1000.0
        File("results/$graphName.$k.$funcID.fixcon").writeText(funcID.toString().padStart(5) + graphName.padStart(40) + vertexCount.toString().padStart(7) + edgeCount.toString().padStart(9) + k.toString().padStart(4) + secondsElapsed.toString().padStart(9) + result.value.toString().padStart(6) + "    " + result.subgraph+"\n")
    } catch (e: TimeoutException) {
        File("results/$graphName.$k.$funcID.fixcon").writeText(funcID.toString().padStart(5) + graphName.padStart(40) + vertexCount.toString().padStart(7) + edgeCount.toString().padStart(9) + k.toString().padStart(4) + "     timeout\n")
    }

    newSingleThreadExecutor().shutdown()
    exitProcess(0)

}