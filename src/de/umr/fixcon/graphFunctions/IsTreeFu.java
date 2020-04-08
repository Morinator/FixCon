package de.umr.fixcon.graphFunctions;

import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;

public class IsTreeFu extends AbstractIndicatorFu implements GraphFu {

    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(Graph<Integer> g, int... parameters) {
        if (g.nodes().isEmpty()) throw new IllegalArgumentException("This graph has 0 vertices");
        boolean isConnected = Graphs.reachableNodes(g, g.nodes().iterator().next()).equals(g.nodes());
        boolean containsCycle = Graphs.hasCycle(g);
        return (!containsCycle && isConnected) ? 1.0 : 0.0;
    }
}
