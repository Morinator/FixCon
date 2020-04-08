package de.umr.fixcon.graphFunctions;

import com.google.common.graph.Graph;

import java.util.stream.IntStream;

public abstract class AbstractGraphFu implements GraphFu {

    IntStream degreeStream(Graph<Integer> g) {
        return g.nodes().stream().mapToInt(g::degree);
    }
}
