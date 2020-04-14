package de.umr.fixcon.itarators;

import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;
import de.umr.core.Mutator;

import static com.google.common.base.Preconditions.checkNotNull;

public final class SubIterator implements Mutator<Graph<Integer>> {

    private final int targetSize;
    private final MutableGraph<Integer> originalGraph;
    public int searchTreeCounter = 0;
    public int sizeKSubgraphCount = 0;
    private SubIterator_FromStart subgraphMutator;

    public SubIterator(MutableGraph<Integer> originalGraph, int targetSize) {
        this.originalGraph = checkNotNull(originalGraph);
        this.targetSize = targetSize;
        subgraphMutator = new SubIterator_FromStart(originalGraph, anyVertex(), targetSize);
    }

    @Override
    public boolean isValid() {
        return subgraphMutator.isValid();
    }

    @Override
    public Graph<Integer> current() {
        return Graphs.copyOf(subgraphMutator.current());
    }

    @Override
    public void mutate() {    //fixed_subgraphIterator throws exception if it doesn't have next element
        subgraphMutator.mutate();
        sizeKSubgraphCount++;
        while (!subgraphMutator.isValid() && originalGraph.nodes().size() > targetSize) {
            searchTreeCounter += subgraphMutator.searchTreeCounter;
            originalGraph.removeNode(subgraphMutator.startVertex());
            subgraphMutator = new SubIterator_FromStart(originalGraph, anyVertex(), targetSize);
        }
    }

    private int anyVertex() {
        return originalGraph.nodes().iterator().next();
    }
}