package de.umr.fixcon.graphFunctions;

import com.google.common.graph.Graph;

public interface GraphFunction {

    boolean isEdgeMonotone();

    double apply(Graph<Integer> g, int... parameters);

    double optimum(int size);
}