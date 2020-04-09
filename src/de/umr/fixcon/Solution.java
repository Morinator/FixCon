package de.umr.fixcon;

import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;

import java.util.Set;

public class Solution {

    private Graph<Integer> originalGraph;
    private Set<Integer> subgraphSet = null;
    private double value = Integer.MIN_VALUE;

    public Solution(Graph<Integer> originalGraph) {
        this.originalGraph = originalGraph;
    }


    public void update(Set<Integer> subgraphSet, double value) {
        this.subgraphSet = subgraphSet;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Graph<Integer> getSolution() {
        return Graphs.inducedSubgraph(originalGraph, subgraphSet);
    }

}
