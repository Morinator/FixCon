package de.umr.fixcon.wrappers;

import com.google.common.graph.MutableGraph;
import de.umr.fixcon.graphFunctions.GraphFunction;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class represents a connected fixed cardinality optimization (CFCO) problem.
 * <p>
 * This class is a wrapper for the following objects:
 * 1. The graph for which the subgraph which optimizes the target-function should be found.
 * 2. The target function
 * 3. The size the resulting subgraph must have.
 * 4. Parameters for the target function (for example the range in which all degrees of the subgraph must be).
 */
public class CFCO_Problem {
    public final MutableGraph<Integer> graph;
    public final GraphFunction function;
    public final int subgraphSize;
    public final int[] parameters;

    /**
     * This constructor only sets all the 4 objects contained in this class.
     */
    public CFCO_Problem(final MutableGraph<Integer> graph, final int subgraphSize, final GraphFunction function, final int... parameters) {
        checkArgument(!graph.nodes().isEmpty(), "Graph cannot be empty");
        this.graph = checkNotNull(graph);
        this.function = checkNotNull(function);
        this.parameters = checkNotNull(parameters);
        this.subgraphSize = subgraphSize;
    }
}
