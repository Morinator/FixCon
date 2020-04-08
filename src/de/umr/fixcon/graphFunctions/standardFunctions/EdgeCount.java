package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.AbstractGraphFunction;
import de.umr.fixcon.graphFunctions.GraphFunction;

public class EdgeCount extends AbstractGraphFunction implements GraphFunction {

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