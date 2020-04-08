package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.AbstractIndicatorFunction;
import de.umr.fixcon.graphFunctions.GraphFunction;

public class IsDegreeConstrained extends AbstractIndicatorFunction implements GraphFunction {
    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(Graph<Integer> g, int... parameters) {
        if (parameters[0] > parameters[1])
            throw new IllegalArgumentException("Lower and upper bounds are in wrong order");
        return degreeStream(g).allMatch(x -> parameters[0] <= x && x <= parameters[1]) ? 1 : 0;
    }
}
