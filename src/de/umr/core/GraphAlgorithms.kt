package de.umr.core


import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

fun hasTriangle(g: Graph<Int, DefaultEdge>) = g.vertexSet().any { x ->
    neighborSetOf(g, x).any { y -> neighborSetOf(g, x).any { z -> g.containsEdge(y, z) } }
}

fun copyIntGraph(g: Graph<Int, DefaultEdge>): Graph<Int, DefaultEdge> {
    val resultGraph = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)
    g.vertexSet().forEach { x -> neighborSetOf(g, x).forEach { y -> addEdgeWithVertices(resultGraph, x, y) } }
    return resultGraph
}
