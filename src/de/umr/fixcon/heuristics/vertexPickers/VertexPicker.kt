package de.umr.fixcon.heuristics.vertexPickers

interface VertexPicker<T> {

    fun startVertex(): T

    fun extensionVertex(subgraph : Set<T>, extension: Set<T>) : T
}