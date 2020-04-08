package de.umr.fixcon.graphFunctions;

import com.google.common.graph.Graph;

public class IsNRegularFu extends AbstractIndicatorFu implements GraphFu {
    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(Graph<Integer> g, int... parameters) {
        if (parameters[0] < 0) throw new IllegalArgumentException("A vertex cannot have a degree lower than 0");
        return degreeStream(g).allMatch(x -> g.degree(x) == parameters[0]) ? 1 : 0;
    }
}
