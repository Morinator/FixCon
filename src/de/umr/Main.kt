package de.umr

import de.umr.fixcon.Problem
import de.umr.fixcon.graphFunctions.graphFunctionByID

fun main(args: Array<String>) {

    val fu = graphFunctionByID(args[1].toInt(), args[2].split(",").map { it.toInt() }.toList())

}