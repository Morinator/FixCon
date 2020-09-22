package de.umr.fixcon

import de.umr.core.dataStructures.copy
import de.umr.core.dataStructures.vertexCount
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph

class Solution<V>(var subgraph: Graph<V, DefaultEdge> = SimpleWeightedGraph(DefaultEdge::class.java),
                  var value: Int = Int.MIN_VALUE) {

    override fun toString() = "Solution: size=${subgraph.vertexCount},  value=$value,  subgraph=$subgraph"

    private fun copyFromOther(other: Graph<V, DefaultEdge>, value: Int) {
        subgraph = other.copy()
        this.value = value
    }

    fun updateIfBetter(g: Graph<V, DefaultEdge>, value: Int) = (value > this.value).also { if (it) copyFromOther(g, value) }

    fun updateIfBetter(sol: Solution<V>) = updateIfBetter(sol.subgraph, sol.value)
}

data class Problem<V>(val g: Graph<V, DefaultEdge>, val f: AbstractGraphFunction) {

    fun eval(graph: Graph<V, DefaultEdge>): Int = f.eval(graph)

    fun cantBeatOther(curr: Graph<V, DefaultEdge>, other: Solution<V>) =
            useAdditionBound && eval(curr) + f.completeBound(curr) <= other.value
}