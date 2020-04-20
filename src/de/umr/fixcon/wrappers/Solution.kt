package de.umr.fixcon.wrappers;

import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class is a wrapper for a solution. It contains:
 * 1. The graph in which is searched for a subgraph that maximizes the target function
 * 2. The subgraph, identified by its set of vertices, that is optimal.
 * 3. The value of the target function for this specific subgraph.
 */
public class Solution {

    private final Graph<Integer> graph;
    private Set<Integer> subgraphSet;
    private double value = Integer.MIN_VALUE;

    public Solution(final Graph<Integer> graph) {
        this.graph = checkNotNull(graph);
    }

    public void update(final Set<Integer> subgraphSet, final double value) {
        this.subgraphSet = checkNotNull(subgraphSet);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Graph<Integer> getSolution() {
        return Graphs.inducedSubgraph(graph, subgraphSet);
    }

}
