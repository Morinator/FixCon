package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.AbstractGraphFunction;
import de.umr.fixcon.graphFunctions.GraphFunction;

public class NegMaxDegreeFunction extends AbstractGraphFunction implements GraphFunction {
    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(Graph<Integer> g, final int... parameters) {
        return -1 * new MaxDegreeFunction().apply(g, parameters);
    }

    @Override
    public double optimum(int size) {
        return size > 2 ? -2 : -1;  //works if graph is connected
    }
}
