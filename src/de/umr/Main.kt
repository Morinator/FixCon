package de.umr

import de.umr.core.io.graphFromFile
import de.umr.fixcon.Problem
import de.umr.fixcon.graphFunctions.graphFunctionByID
import de.umr.fixcon.solve

fun main(args: Array<String>) {
    val fu = graphFunctionByID(args[1].toInt(), args[2].split(",").map { it.toInt() }.toList())
    println(solve(Problem(graphFromFile(args[3]), fu)))
}