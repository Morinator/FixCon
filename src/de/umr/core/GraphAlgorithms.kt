package de.umr.core;

import com.google.common.graph.Graph;

public class GraphAlgorithms {

    static boolean areOpenTwins(Graph<Integer> g, int x, int y) {
        return true;
    }

    static boolean areClosedTwins(Graph<Integer> g, int x, int y) {
        return true;
    }

    static boolean isSmallerTwin(Graph<Integer> g, int x, int y) {
        return true;
    }

    static int diameter(Graph<Integer> g) {
        return -123;
    }

    public static boolean hasTriangle(Graph<Integer> g) {
        return g.nodes().stream().anyMatch(x -> g.adjacentNodes(x).stream().anyMatch(
                y -> g.adjacentNodes(x).stream().anyMatch(z -> g.hasEdgeConnecting(y, z)))
        );
    }
}
