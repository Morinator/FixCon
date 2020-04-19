package de.umr.core

import com.google.common.graph.Graph

fun hasTriangle(g: Graph<Int>) = g.nodes().any { x ->
    g.adjacentNodes(x).any { y -> g.adjacentNodes(x).stream().anyMatch { z -> g.hasEdgeConnecting(y, z) } }
}
