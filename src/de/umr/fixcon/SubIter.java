package de.umr.fixcon;

import com.google.common.graph.MutableGraph;

import java.util.Iterator;
import java.util.Set;

public class SubIter implements Iterator<Set<Integer>> {

    private final int k;
    private final MutableGraph<Integer> graph;
    private SubIter_fromStart subIter_startVertex;

    public SubIter(MutableGraph<Integer> graph, int k) {
        this.graph = graph;
        this.k = k;
        subIter_startVertex = new SubIter_fromStart(graph, graph.nodes().iterator().next(), k);
    }


    @Override
    public boolean hasNext() {
        return subIter_startVertex.hasNext();
    }

    @Override
    public Set<Integer> next() {    //fixed_subgraphIterator throws exception if it doesn't have next element
        Set<Integer> next_subGraph = subIter_startVertex.next();
        adjust_FixedSubgraphIterator();
        return next_subGraph;
    }

    private void adjust_FixedSubgraphIterator() {
        while (!subIter_startVertex.hasNext() && graph.nodes().size() > k) {
            graph.removeNode(subIter_startVertex.startVertex);
            subIter_startVertex = new SubIter_fromStart(graph, graph.nodes().iterator().next(), k);
        }
    }
}