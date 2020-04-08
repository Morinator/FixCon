package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import de.umr.fixcon.graphFunctions.AbstractGraphFu;
import de.umr.fixcon.graphFunctions.GraphFu;

import java.util.OptionalInt;

public class MaxDegreeFu extends AbstractGraphFu implements GraphFu {

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
    public double optimum(Graph<Integer> g, int... parameters) {
        if (g.nodes().isEmpty()) throw new IllegalArgumentException("Graph cannot be empty");
        return g.nodes().size() - 1;
    }
}
