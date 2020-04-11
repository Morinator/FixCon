package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.AbstractGraphFunction;
import de.umr.fixcon.graphFunctions.GraphFunction;

import static com.google.common.base.Preconditions.checkArgument;

public class EdgeCountFunction extends AbstractGraphFunction implements GraphFunction {

    @Override
    public boolean isEdgeMonotone() {
        return true;
    }

    @Override
    public double apply(final Graph<Integer> g, final int... parameters) {
        return degreeStream(g).sum() / 2.0;
    }

    @Override
    public double optimum(final int size) {
        checkArgument(size >= 0);
        return size * (size - 1) / 2.0;
    }
}