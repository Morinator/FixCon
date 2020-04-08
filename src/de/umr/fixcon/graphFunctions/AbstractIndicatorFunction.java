package de.umr.fixcon.graphFunctions;

import com.google.common.graph.Graph;

public abstract class AbstractIndicatorFunction extends AbstractGraphFunction {

    @Override
    public double optimum(Graph<Integer> g, int... parameters) {
        return 1;
    }
}
