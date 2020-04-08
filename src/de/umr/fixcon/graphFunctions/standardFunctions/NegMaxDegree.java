package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.AbstractGraphFunction;
import de.umr.fixcon.graphFunctions.GraphFunction;

public class NegMaxDegree extends AbstractGraphFunction implements GraphFunction {
    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(Graph<Integer> g, int... parameters) {
        return -1 * new MaxDegree().apply(g, parameters);
    }

    @Override
    public double optimum(Graph<Integer> g, int... parameters) {
        return -1;  //does not support graphs with isolated vertices, therefore it is -1 and not 0
    }
}
