package de.umr.fixcon.itarators;

import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;
import de.umr.core.Mutator;

import static com.google.common.base.Preconditions.checkNotNull;

public final class SubgraphMutator implements Mutator<Graph<Integer>> {

    private final int targetSize;
    private final MutableGraph<Integer> originalGraph;
    private SubgraphMutator_FromStart subgraph_Iterator;

    public SubgraphMutator(MutableGraph<Integer> originalGraph, int targetSize) {
        this.originalGraph = checkNotNull(originalGraph);
        this.targetSize = targetSize;
        subgraph_Iterator = new SubgraphMutator_FromStart(originalGraph, anyVertex(), targetSize);
    }

    @Override
    public boolean isValid() {
        return subgraph_Iterator.isValid();
    }

    @Override
    public Graph<Integer> current() {
        return Graphs.copyOf(subgraph_Iterator.current());
    }

    @Override
    public void mutate() {    //fixed_subgraphIterator throws exception if it doesn't have next element
        subgraph_Iterator.mutate();
        while (!subgraph_Iterator.isValid()) {
            if (originalGraph.nodes().size() <= targetSize)
                break;
            else {
                originalGraph.removeNode(subgraph_Iterator.getStartVertex());
                subgraph_Iterator = new SubgraphMutator_FromStart(originalGraph, anyVertex(), targetSize);
            }
        }
    }

    private int anyVertex() {
        return originalGraph.nodes().iterator().next();
    }
}