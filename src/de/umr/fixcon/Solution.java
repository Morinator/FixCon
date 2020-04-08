package de.umr.fixcon;

import com.google.common.graph.Graph;

public class Solution {
    public Graph<Integer> graph;
    public double value;

    public Solution(Graph<Integer> graph, double value) {
        this.graph = graph;
        this.value = value;
    }
}
