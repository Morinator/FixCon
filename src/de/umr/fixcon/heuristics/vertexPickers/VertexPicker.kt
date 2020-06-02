package de.umr.fixcon.heuristics.vertexPickers

interface VertexPicker {

    fun startVertex(): Int

    fun extensionVertex(subgraph : Set<Int>, extension: Set<Int>) : Int
}