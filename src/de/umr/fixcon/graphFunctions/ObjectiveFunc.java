package de.umr.fixcon.graphFunctions;

import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
import de.umr.core.GraphStatics;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class ObjectiveFunc {

    final boolean isEdgeMonotone;
    private final GraphFunction func;

    public ObjectiveFunc(StandardObjectives standardFunction) {
        switch (standardFunction) {
            case edgeCount:
                isEdgeMonotone = true;
                func = (g, paras) -> degreeStream(g).sum() / 2d;
                break;

            case minDegree:
                isEdgeMonotone = true;
                func = (g, paras) -> {
                    OptionalInt result = degreeStream(g).min();
                    if (result.isPresent()) return result.getAsInt() * 1d;
                    throw new IllegalArgumentException("Graph does not contain any edges");
                };
                break;

            case maxDegree:
                isEdgeMonotone = true;
                func = maxDegreeWithSign(1);
                break;

            case negativeMaxDegree:
                isEdgeMonotone = false;
                func = maxDegreeWithSign(-1);
                break;

            case isTree:
                isEdgeMonotone = false;
                func = (g, paras) -> {
                    if (g.nodes().isEmpty()) throw new IllegalArgumentException("This graph has 0 vertices");
                    boolean isConnected = Graphs.reachableNodes(g, g.nodes().iterator().next()).equals(g.nodes());
                    boolean containsCycle = Graphs.hasCycle(g);
                    return (!containsCycle && isConnected) ? 1d : 0d;
                };
                break;

            case isDegreeConstrained:
                isEdgeMonotone = false;
                func = (g, paras) -> {
                    if (paras[0] > paras[1])
                        throw new IllegalArgumentException("Lower and upper bounds are in wrong order");
                    return degreeStream(g).allMatch(x -> paras[0] <= x && x <= paras[1]) ? 1 : 0;
                };
                break;

            case is_N_Regular:
                isEdgeMonotone = false;
                func = (g, paras) -> {
                    if (paras[0] < 0) throw new IllegalArgumentException("A vertex cannot have a degree lower than 0");
                    return degreeStream(g).allMatch(x -> g.degree(x) == paras[0]) ? 1 : 0;
                };
                break;

            case hasNoTriangle:
                isEdgeMonotone = false;
                func = (g, paras) -> (!GraphStatics.hasTriangle(g) || g.nodes().size() < 3) ? 1 : 0;
                break;

            default:
                throw new IllegalArgumentException("This function is not supported yet");
        }
    }

    private IntStream degreeStream(Graph<Integer> g) {
        return g.nodes().stream().mapToInt(g::degree);
    }

    private GraphFunction maxDegreeWithSign(int factor) {
        return (g, paras) -> {
            OptionalInt result = degreeStream(g).max();
            if (result.isPresent()) return result.getAsInt() * factor;
            throw new IllegalArgumentException("Graph does not contain any edges");
        };
    }

    public double apply(Graph<Integer> g, int... paras) {
        return func.apply(g, paras);
    }
}
