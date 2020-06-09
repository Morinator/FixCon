package de.umr.fixcon.heuristics.vertexPickers

import de.umr.core.openNB
import de.umr.fixcon.heuristics.WeightedRandomSet
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

abstract class VertexPicker<V>(open val graph: Graph<V, DefaultEdge>) {

    protected val verticesByDensity = WeightedRandomSet(graph.vertexSet().associateWith { graph.degreeOf(it).toDouble() })

    protected val verticesBySparsity = WeightedRandomSet(graph.vertexSet().associateWith { 1.0 / graph.degreeOf(it) })

    /**@return The number of neighbours [v] has in [subgraph]*/
    protected fun subgraphNB(subgraph: Set<V>, v: V): Int = (graph.openNB(v) intersect subgraph).size

    open fun startVertex(): V = graph.vertexSet().random()

    abstract fun extensionVertex(subgraph: Set<V>, extension: Set<V>): V
}