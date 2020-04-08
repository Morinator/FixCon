package de.umr.fixcon.graphFunctions;

import com.google.common.graph.Graph;
import de.umr.core.GraphStatics;

public class HasNoTrianglesFu extends AbstractIndicatorFu implements GraphFu {
    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(Graph<Integer> g, int... parameters) {
        return (!GraphStatics.hasTriangle(g) || g.nodes().size() < 3) ? 1 : 0;
    }
}
