package de.umr.fixcon;

import com.google.common.graph.MutableGraph;
import de.umr.fixcon.graphFunctions.GraphFunction;

public class Problem {
    MutableGraph<Integer> graph;
    GraphFunction function;
    int subgraphSize;
    int[] parameters;

    public Problem(MutableGraph<Integer> graph, GraphFunction function, int subgraphSize, int... parameters) {
        this.graph = graph;
        this.function = function;
        this.subgraphSize = subgraphSize;
        this.parameters = parameters;
    }
}
