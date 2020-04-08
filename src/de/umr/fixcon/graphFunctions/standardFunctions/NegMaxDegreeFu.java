package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.AbstractGraphFu;
import de.umr.fixcon.graphFunctions.GraphFu;

public class NegMaxDegreeFu extends AbstractGraphFu implements GraphFu {
    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(Graph<Integer> g, int... parameters) {
        return -1 * new MaxDegreeFu().apply(g, parameters);
    }

    @Override
    public double optimum(Graph<Integer> g, int... parameters) {
        return -1;  //does not support graphs with isolated vertices, therefore it is -1 and not 0
    }
}
