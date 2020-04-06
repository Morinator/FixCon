package de.umr.fixcon.graphFunctions;

import com.google.common.graph.Graph;

/**
 * The given functional interface Function<T,R> only supports 1 parameter, but multiple were needed.
 */
@FunctionalInterface
public interface GraphFunction {

    double apply(Graph<Integer> g, int... parameters);
}