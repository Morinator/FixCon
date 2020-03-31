package de.umr.fixcon;

import com.google.common.graph.Graph;
import com.google.common.graph.MutableGraph;
import de.umr.core.BlindIterator;

public class SubIter implements BlindIterator<Graph<Integer>> {

    private final int k;
    private final MutableGraph<Integer> graph;
    private SubIter_fromStart sub_iter;

    public SubIter(MutableGraph<Integer> graph, int k) {
        this.graph = graph;
        this.k = k;
        sub_iter = new SubIter_fromStart(graph, graph.nodes().iterator().next(), k);
    }

    @Override
    public boolean hasCurrent() {
        return sub_iter.hasCurrent();
    }

    @Override
    public Graph<Integer> getCurrent() {
        return sub_iter.getCurrent();
    }

    @Override
    public void generateNext() {    //fixed_subgraphIterator throws exception if it doesn't have next element
        sub_iter.generateNext();
        while (!sub_iter.hasCurrent()) {
            if (graph.nodes().size() < k)
                break;
            else {
                graph.removeNode(sub_iter.startVertex);
                sub_iter = new SubIter_fromStart(graph, graph.nodes().iterator().next(), k);
            }
        }
    }
}