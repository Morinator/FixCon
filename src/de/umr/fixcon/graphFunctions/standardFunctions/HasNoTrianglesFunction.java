package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.core.GraphAlgorithms;
import de.umr.fixcon.graphFunctions.DecisionProblem;
import de.umr.fixcon.graphFunctions.GraphFunction;

public class HasNoTrianglesFunction extends DecisionProblem implements GraphFunction {
    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(final Graph<Integer> g, final int... parameters) {
        return (!GraphAlgorithms.hasTriangle(g)) ? 1 : 0;
    }
}
