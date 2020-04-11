package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.AbstractIndicatorFunction;
import de.umr.fixcon.graphFunctions.GraphFunction;

import static com.google.common.base.Preconditions.checkArgument;

public class IsDegreeConstrainedFunction extends AbstractIndicatorFunction implements GraphFunction {
    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(final Graph<Integer> g, final int... paras) {
        checkArgument(paras[0] <= paras[1], "Lower and upper bounds are in wrong order");
        return degreeStream(g).allMatch(x -> paras[0] <= x && x <= paras[1]) ? 1 : 0;
    }
}
