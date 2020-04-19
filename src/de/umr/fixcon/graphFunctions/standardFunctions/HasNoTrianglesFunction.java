package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.DecisionProblem;
import de.umr.fixcon.graphFunctions.GraphFunction;

import static de.umr.core.GraphAlgorithmsKt.hasTriangle;

public class HasNoTrianglesFunction extends DecisionProblem implements GraphFunction {
    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(final Graph<Integer> g, final int... parameters) {
        return (!hasTriangle(g)) ? 1 : 0;
    }
}
