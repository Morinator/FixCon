package de.umr

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

    val fu = graphFunctionByID(funcID, k, funcParams)

    Files.createDirectories(Paths.get("results"))

    try {
        val timeBefore  = System.currentTimeMillis()
        val result = newSingleThreadExecutor().submit<Solution<Int>> { solve(Problem(graphFromFile(args[0]), fu)) }.get(timeLimit, SECONDS)
        val timeElapsed = (System.currentTimeMillis() - timeBefore) / 1000.0
        File("results/${File(args[0]).name}.$k.$funcID").writeText("${result.value}         ${result.subgraph}     $timeElapsed")
    } catch (e: TimeoutException) {
        File("results/${File(args[0]).name}.$k.$funcID").writeText("timeout")
    }

    newSingleThreadExecutor().shutdown()


}