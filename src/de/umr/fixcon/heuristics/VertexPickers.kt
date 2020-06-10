package de.umr.fixcon.heuristics

import de.umr.core.openNB
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import de.umr.fixcon.heuristics.WeightedRandomSet as WRS

sealed class VertexPickers<V>(open val graph: Graph<V, DefaultEdge>) {

    //###  public methods  ###
    open fun startVertex(): V = graph.vertexSet().random()  //default is a Laplace-random vertex
    abstract fun extensionVertex(subgraph: Set<V>, extension: Set<V>): V


    //###  methods used by inheriting classes  ####
    /**@return A [WRS], in which all vertices of [graph] are weighted according to the output of [weight]*/
    protected fun startByDegreeWeighing(weight: (Double) -> Double): V = WRS(graph.vertexSet().associateWith { weight(graph.degreeOf(it).toDouble()) }).random

    /**@return A [WRS], in which all vertices of [graph] are weighted according to the output of [weight]*/
    protected fun extensionVertexWeighting(weight: (Int) -> Double, subgraph: Set<V>, extension: Set<V>) =
            WRS(extension.associateWith { weight(subgraphNB(subgraph, it)) }).random

    /**@return The number of neighbours [v] has in [subgraph]*/
    protected fun subgraphNB(subgraph: Set<V>, v: V): Int = (graph.openNB(v) intersect subgraph).size


    //############################################################################################################


    class RandomSparse<T>(override val graph: Graph<T, DefaultEdge>) : VertexPickers<T>(graph) {
        override fun startVertex(): T = startByDegreeWeighing { 1 / it }

        override fun extensionVertex(subgraph: Set<T>, extension: Set<T>) =
                extensionVertexWeighting({ 1.0 / it }, subgraph, extension)
    }


    class RandomDense<T>(override val graph: Graph<T, DefaultEdge>) : VertexPickers<T>(graph) {
        /**Picks a start-vertex with a probability proportional to its degree in the graph.*/
        override fun startVertex(): T = startByDegreeWeighing { it }

        /**Picks a vertex adjacent to the subgraph with a probability proportional to the number of edges it has to
         * the subgraph.*/
        override fun extensionVertex(subgraph: Set<T>, extension: Set<T>): T =
                extensionVertexWeighting({ it.toDouble() }, subgraph, extension)
    }


    class Laplace<T>(override val graph: Graph<T, DefaultEdge>) : VertexPickers<T>(graph) {
        override fun extensionVertex(subgraph: Set<T>, extension: Set<T>): T = extension.random()
    }


    class GreedySparse<T>(override val graph: Graph<T, DefaultEdge>) : VertexPickers<T>(graph) {
        override fun startVertex(): T = startByDegreeWeighing { 1 / it }

        override fun extensionVertex(subgraph: Set<T>, extension: Set<T>): T =
                extension.minBy { subgraphNB(subgraph, it) }!!
    }


    class GreedyDense<T>(override val graph: Graph<T, DefaultEdge>) : VertexPickers<T>(graph) {
        override fun startVertex(): T = startByDegreeWeighing { it }

        override fun extensionVertex(subgraph: Set<T>, extension: Set<T>): T =
                extension.maxBy { subgraphNB(subgraph, it) }!!
    }
}