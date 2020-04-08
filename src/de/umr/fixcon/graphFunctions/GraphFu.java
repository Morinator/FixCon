package de.umr.fixcon.graphFunctions;

import com.google.common.graph.Graph;

public interface GraphFu {

    boolean isEdgeMonotone();

    double apply(Graph<Integer> g, int... parameters);

    double optimum(Graph<Integer> g, int... parameters);
}
