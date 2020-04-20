package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.AbstractGraphFunction;
import de.umr.fixcon.graphFunctions.GraphFunction;

import java.util.OptionalInt;

import static com.google.common.base.Preconditions.checkArgument;

public class MinDegreeFunction extends AbstractGraphFunction implements GraphFunction {

    @Override
    public boolean isEdgeMonotone() {
        return true;
    }

    @Override
    public double apply(final Graph<Integer> g, final int... parameters) {
        OptionalInt result = degreeStream(g).min();
        checkArgument(result.isPresent(), "Graph does not contain any edges");
        return result.getAsInt();
    }

    @Override
    public double optimum(int size) {
        return size - 1;
    }
}
