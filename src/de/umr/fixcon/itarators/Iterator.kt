package de.umr.fixcon.itarators

import de.umr.core.dataStructures.vertexCount
import de.umr.fixcon.Instance
import de.umr.fixcon.Solution
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

abstract class Iterator<V>(val instance: Instance<V>, val start: V, val sol: Solution<V> = Solution()) {

    abstract val subgraph: Graph<V, DefaultEdge>

    protected val numVerticesMissing get() = instance.f.k - subgraph.vertexCount

    val isValid get() = numVerticesMissing == 0

    abstract fun run()
}