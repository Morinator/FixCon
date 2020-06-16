package de.umr.core

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun <V> shareOfDegree  (g: Graph<V, DefaultEdge>, d : Int) : Double =
        g.vertexSet().count { g.degreeOf(it) == d }.toDouble() / g.vertexCount