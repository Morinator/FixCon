package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.AbstractGraphFunction;
import de.umr.fixcon.graphFunctions.GraphFunction;

import java.util.OptionalInt;

public class MaxDegree extends AbstractGraphFunction implements GraphFunction {

    @Override
    public boolean isEdgeMonotone() {
        return true;
    }

    @Override
    public double apply(Graph<Integer> g, int... parameters) {
        OptionalInt result = degreeStream(g).max();
        if (result.isPresent()) return result.getAsInt();
        throw new IllegalArgumentException("Graph does not contain any edges");
    }

    @Override
    public double optimum(int size, int... parameters) {
        return size - 1;
    }
}
