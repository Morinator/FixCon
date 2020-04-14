package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
import de.umr.fixcon.graphFunctions.DecisionProblem;
import de.umr.fixcon.graphFunctions.GraphFunction;

import static com.google.common.base.Preconditions.checkArgument;

public class IsTreeFunction extends DecisionProblem implements GraphFunction {

    @Override
    public boolean isEdgeMonotone() {
        return false;
    }

    @Override
    public double apply(final Graph<Integer> g, final int... parameters) {
        checkArgument(!g.nodes().isEmpty());
        boolean isConnected = Graphs.reachableNodes(g, g.nodes().iterator().next()).equals(g.nodes());
        boolean isAcyclic = !Graphs.hasCycle(g);
        return (isAcyclic && isConnected) ? 1.0 : 0.0;
    }
}