package de.umr.fixcon;

import com.google.common.graph.MutableGraph;
import de.umr.fixcon.graphFunctions.GraphFunction;

/**
 * This class represents a connected fixed cardinality optimization (CFCO) problem.
 * <p>
 * This class is a wrapper for the following objects:
 * 1. The graph for which the subgraph which optimizes the target-function should be found.
 * 2. The target function
 * 3. The size the resulting subgraph must have.
 * 4. Parameters for the target function (for example the range in which all degrees of the subgraph must be).
 */
public class Problem {
    MutableGraph<Integer> graph;
    GraphFunction function;
    int subgraphSize;
    int[] parameters;

    /**
     * This constructor only sets all the 4 objects contained in this class.
     */
    public Problem(MutableGraph<Integer> graph, int subgraphSize, GraphFunction function, int... parameters) {
        this.graph = graph;
        this.function = function;
        this.subgraphSize = subgraphSize;
        this.parameters = parameters;
    }
}
