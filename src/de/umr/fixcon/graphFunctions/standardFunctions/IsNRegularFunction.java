package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.AbstractIndicatorFunction;
import de.umr.fixcon.graphFunctions.GraphFunction;

import static com.google.common.base.Preconditions.checkArgument;

public class IsNRegularFunction extends AbstractIndicatorFunction implements GraphFunction {
    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(final Graph<Integer> g, final int... parameters) {
        checkArgument(parameters[0] >= 0);
        return degreeStream(g).allMatch(x -> g.degree(x) == parameters[0]) ? 1 : 0;
    }
}
