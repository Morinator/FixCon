package de.umr.core

import de.umr.core.dataStructures.VertexOrderedGraph
import java.lang.Integer.parseInt
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

object GraphFileReader {

    private val weightedEdgePattern = Regex("""\d+\s+\d+\s+\d+""")
    private val unweightedEdgePattern = Regex("""\d+\s+\d+""")
    private val separator = Regex("""\s+""")

    private fun edgesFromFile(filePath: String, weighted: Boolean = false): List<Triple<Int, Int, Double>> {
        return Files.lines(Paths.get(filePath)).toList()
                .filter { it.matches(if (weighted) weightedEdgePattern else unweightedEdgePattern) }
                .map { it.split(separator) }
                .map { Triple(parseInt(it[0]), parseInt(it[1]), if (weighted) it[2].toDouble() else 1.0) }
    }

    fun graphFromFile(filePath: String, weighted: Boolean = false): VertexOrderedGraph<Int> =
            VertexOrderedGraph(edgesFromFile(filePath, weighted))
}