package de.umr.fixcon

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.extensions.copy
import de.umr.core.extensions.vertexCount
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph

class Solution<V>(var subgraph: Graph<V, DefaultEdge> = SimpleWeightedGraph(DefaultEdge::class.java), var value: Int = Int.MIN_VALUE) {

    override fun toString() = "Solution: size=${subgraph.vertexCount},  value=$value,  subgraph=$subgraph"

    private fun copyFromOther(other: Graph<V, DefaultEdge>, value: Int) {
        subgraph = other.copy()
        this.value = value
    }

    fun updateIfBetter(g: Graph<V, DefaultEdge>, value: Int) = (value > this.value).also { if (it) copyFromOther(g, value) }

    fun updateIfBetter(sol: Solution<V>) = updateIfBetter(sol.subgraph, sol.value)
}

data class Problem<V>(val g: VertexOrderedGraph<V>, val function: AbstractGraphFunction) {

    fun eval(graph: Graph<V, DefaultEdge>): Int = function.eval(graph)

    fun verticesByDegree() = g.vertexSet().associateWith { g.degreeOf(it) }

    fun cantBeatOther(curr: Graph<V, DefaultEdge>, other: Solution<V>) = eval(curr) + function.completeBound(curr) <= other.value
}