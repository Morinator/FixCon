package de.umr.fixcon.itarators;

import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;
import de.umr.core.Mutator;

public final class SubIter implements Mutator<Graph<Integer>> {

    private final int targetSize;
    private final MutableGraph<Integer> originalGraph;
    private SubIter_fromStart subgraph_Iterator;

    public SubIter(MutableGraph<Integer> originalGraph, int targetSize) {
        this.originalGraph = originalGraph;
        this.targetSize = targetSize;
        subgraph_Iterator = new SubIter_fromStart(originalGraph, anyVertex(), targetSize);
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
                subgraph_Iterator = new SubIter_fromStart(originalGraph, originalGraph.nodes().iterator().next(), targetSize);
            }
        }
    }

    private int anyVertex() {
        return originalGraph.nodes().iterator().next();
    }
}