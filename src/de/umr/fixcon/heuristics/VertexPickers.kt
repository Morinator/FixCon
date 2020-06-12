package de.umr.fixcon.heuristics

import de.umr.core.openNB
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import de.umr.fixcon.heuristics.WeightedRandomSet as WRS

sealed class VertexPickers<V>(val graph: Graph<V, DefaultEdge>) {

    abstract fun startVertex(): V
    abstract fun extensionVertex(subgraph: Set<V>, extension: Set<V>): V

    //###  methods used by inheriting classes  ####
    /**@return A vertex from [graph], picked with weighted chance. The weight of each vertex is the value
     * of [weightFu] applied to the degree of the vertex.*/
    protected fun startByDegreeWeighing(weightFu: (Double) -> Double): V =
            WRS(graph.vertexSet().associateWith { weightFu(graph.degreeOf(it).toDouble()) }).random

    /**@return A vertex from [extension], picked with weighted chance. The weight of each vertex *v* is the value
     * of [weightFu] applied to the number edges *v* has to [subgraph]*/
    protected fun extensionVertexWeighting(weightFu: (Double) -> Double, subgraph: Set<V>, extension: Set<V>) =
            WRS(extension.associateWith { weightFu(subgraphNB(subgraph, it).toDouble()) }).random

    /**@return The number of neighbours [v] has in [subgraph]*/
    protected fun subgraphNB(subgraph: Set<V>, v: V): Int = (graph.openNB(v) intersect subgraph).size


    class RandomSparse<V>(graph: Graph<V, DefaultEdge>) : VertexPickers<V>(graph) {
        override fun startVertex(): V = startByDegreeWeighing { 1 / it }

        override fun extensionVertex(subgraph: Set<V>, extension: Set<V>) =
                extensionVertexWeighting({ 1 / it }, subgraph, extension)
    }

    class RandomDense<V>(graph: Graph<V, DefaultEdge>) : VertexPickers<V>(graph) {
        /**Picks a start-vertex with a probability proportional to its degree in the graph.*/
        override fun startVertex(): V = startByDegreeWeighing { it }

        /**Picks a vertex adjacent to the subgraph with a probability proportional to the number of edges it has to
         * the subgraph.*/
        override fun extensionVertex(subgraph: Set<V>, extension: Set<V>): V =
                extensionVertexWeighting({ it }, subgraph, extension)
    }

    class Laplace<V>(graph: Graph<V, DefaultEdge>) : VertexPickers<V>(graph) {
        override fun startVertex(): V = graph.vertexSet().random()

        override fun extensionVertex(subgraph: Set<V>, extension: Set<V>): V = extension.random()
    }

    class GreedySparse<V>(graph: Graph<V, DefaultEdge>) : VertexPickers<V>(graph) {
        override fun startVertex(): V = startByDegreeWeighing { 1 / it }

        override fun extensionVertex(subgraph: Set<V>, extension: Set<V>): V =
                extension.minBy { subgraphNB(subgraph, it) }!!
    }

    class GreedyDense<V>(graph: Graph<V, DefaultEdge>) : VertexPickers<V>(graph) {
        override fun startVertex(): V = startByDegreeWeighing { it }

        override fun extensionVertex(subgraph: Set<V>, extension: Set<V>): V =
                extension.maxBy { subgraphNB(subgraph, it) }!!
    }
}

