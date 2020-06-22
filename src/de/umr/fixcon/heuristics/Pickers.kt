package de.umr.fixcon.heuristics

import org.jgrapht.Graph

enum class Pickers(val fu: (Graph<Any, Any>) -> Any) {
    LINEAR({ it }),
}