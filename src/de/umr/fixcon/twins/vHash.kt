package de.umr.fixcon.twins

import de.umr.core.extensions.closedNB
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge

private fun <V> vHashHelper(graph: Graph<V, DefaultEdge>, v: V, nbSelector: (V) -> Set<V>) =
        listOf(graph.degreeOf(v), nbSelector(v).sumBy { graph.degreeOf(it) }, nbSelector(v).sumBy { it.hashCode() })

fun <V> vHashClosed(graph: Graph<V, DefaultEdge>, v: V) = vHashHelper(graph, v, { graph.closedNB(it) })

fun <V> vHashOpen(graph: Graph<V, DefaultEdge>, v: V) = vHashHelper(graph, v, { neighborSetOf(graph, it) })