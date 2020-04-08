package de.umr.fixcon.graphFunctions;

import com.google.common.graph.Graph;

import java.util.OptionalInt;

public class MinDegreeFu extends AbstractGraphFu implements GraphFu {

    @Override
    public boolean isEdgeMonotone() {
        return true;
    }

    @Override
    public double apply(Graph<Integer> g, int... parameters) {
        OptionalInt result = degreeStream(g).min();
        if (result.isPresent()) return result.getAsInt() * 1d;
        throw new IllegalArgumentException("Graph does not contain any edges");
    }

    @Override
    public double optimum(Graph<Integer> g, int... parameters) {
        if (g.nodes().isEmpty()) throw new IllegalArgumentException("Graph cannot be empty");
        return g.nodes().size() - 1;
    }
}
