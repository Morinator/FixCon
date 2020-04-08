package de.umr.fixcon.graphFunctions;

import com.google.common.graph.Graph;

public class EdgeCountFu extends AbstractGraphFu implements GraphFu {

    @Override
    public boolean isEdgeMonotone() {
        return true;
    }

    @Override
    public double apply(Graph<Integer> g, int... parameters) {
        return degreeStream(g).sum() / 2.0;
    }

    @Override
    public double optimum(Graph<Integer> g, int... parameters) {
        int graphSize = g.nodes().size();
        return graphSize * (graphSize - 1) / 2.0;
    }
}
