package de.umr.fixcon;

import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;

import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.IntStream;

public class GraphFunction implements Function<Graph<Integer>, Double> {

    final boolean isEdgeMonotone;
    private final Function<Graph<Integer>, Double> targetFunction;

    GraphFunction(StandardFunction standardFunction) {
        switch (standardFunction) {
            case edgeCount:
                isEdgeMonotone = true;
                targetFunction = g -> degreeStream(g).sum() / 2d;
                break;

            case minDegree:
                isEdgeMonotone = true;
                targetFunction = g -> {
                    OptionalInt result = degreeStream(g).min();
                    if (result.isPresent()) return result.getAsInt() * 1d;
                    throw new IllegalArgumentException("Graph does not contain any edges");
                };
                break;

            case negative_MaxDegree:
                isEdgeMonotone = false;
                targetFunction = g -> {
                    OptionalInt result = degreeStream(g).max();
                    if (result.isPresent()) return result.getAsInt() * -1d;
                    throw new IllegalArgumentException("Graph does not contain any edges");
                };
                break;

            case isTree_Indicator:
                isEdgeMonotone = false;
                targetFunction = g -> {
                    if (g.nodes().isEmpty()) throw new IllegalArgumentException("This graph has 0 vertices");
                    boolean isConnected = Graphs.reachableNodes(g, g.nodes().iterator().next()).equals(g.nodes());
                    boolean containsCycle = Graphs.hasCycle(g);
                    return (!containsCycle && isConnected) ? 1d : 0d;
                };
                break;
            default:
                throw new IllegalArgumentException("This function is not supported yet");
        }
    }

    private IntStream degreeStream(Graph<Integer> g) {
        return g.nodes().stream().mapToInt(g::degree);
    }

    @Override
    public Double apply(Graph<Integer> g) {
        return targetFunction.apply(g);
    }
}
