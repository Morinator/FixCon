package de.umr.core

import de.umr.core.dataStructures.VertexOrderedGraph
import org.jgrapht.Graphs.addEdgeWithVertices
import java.lang.Integer.parseInt
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

object GraphFileReader {

    private fun edgesFromNetworkRepo(filePath: String, weighted: Boolean = false): List<Triple<Int, Int, Double>> {
        return Files.lines(Paths.get(filePath)).toList()
                .filter { it.matches(if (weighted) Regex("""\d+\s+\d+\s+\d+""") else Regex("""\d+\s+\d+""")) }
                .map { it.split(Regex("""\s+""")) }
                .map { Triple(parseInt(it[0]), parseInt(it[1]), if (weighted) it[2].toDouble() else 1.0) }
    }

    fun graphFromNetworkRepo(filePath: String, weighted: Boolean = false): VertexOrderedGraph<Int> =
            VertexOrderedGraph(edgesFromNetworkRepo(filePath, weighted))
}