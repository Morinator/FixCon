package de.umr.fixcon;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import de.umr.core.utils.BlindIterator;
import de.umr.core.utils.FastList;

import java.util.*;

import static de.umr.core.utils.CollectionUtil.*;
import static java.util.stream.Collectors.toList;

public class SubIter_fromStart implements BlindIterator<Graph<Integer>> {

    final int startVertex;
    private final int k;
    private final MutableGraph<Integer> graph;

    private MutableGraph<Integer> subgraph = GraphBuilder.undirected().allowsSelfLoops(false).build();
    private Deque<Integer> subgraph_stack = new ArrayDeque<>();
    private List<Integer> extension_list = new FastList<>();
    private Deque<Integer> pointerStack = new ArrayDeque<>(List.of(0));
    private Deque<Integer> privateStack = new ArrayDeque<>();

    SubIter_fromStart(MutableGraph<Integer> graph, int startVertex, int k) {
        if (k == 1) throw new IllegalArgumentException("Does not support search for subgraphs of size 1");
        this.startVertex = startVertex;
        this.graph = graph;
        this.k = k;
        subgraph_stack.push(startVertex);
        subgraph.addNode(startVertex);
        extension_list.addAll(graph.adjacentNodes(startVertex));
        privateStack.push(extension_list.size());
        generateNext();
    }

    @Override
    public boolean hasCurrent() {
        return subgraph_stack.size() == k;
    }

    @Override
    public Graph<Integer> getCurrent() {
        return subgraph;
    }

    @Override
    public void generateNext() {
        do {
            if (subgraph_stack.size() == k) {
                delete_subset_head();
                continue;
            }
            if (pointerHead_is_outOfRange()) {  //size of subset is < k for following code:
                if (!pointerHead_is_pending())
                    delete_subset_head();
                delete_subset_head();
                pointerStack.pop();
            } else {                            //pivot is not out of range
                if (pointerHead_is_pending())
                    add_pivot();
                else
                    duplicate_stack_head(pointerStack);
            }
        } while (!hasCurrent() && pointerStack.size()>0);
    }

    /*for the last element in the subset it is not necessary to generate the extension-list, because the subset
    wont be extended further. Therefore in this case the adjustment of the extension-list is omitted.*/
    private void add_pivot() {
        if (subgraph_stack.size() != k-1) {
            List<Integer> new_extension = get_new_extension(pivot_vertex());
            extension_list.addAll(new_extension);
            privateStack.push(new_extension.size());
        }
        subgraph_stack.push(pivot_vertex());
        graph.adjacentNodes(pivot_vertex()).stream()
                .filter(x -> subgraph.nodes().contains(x))
                .forEach(x -> subgraph.putEdge(pivot_vertex(), x));
        change_stack_head(pointerStack, 1);
    }

    /*Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
    to be delete here in this case. This is the case if the size of the subset is k*/
    private void delete_subset_head() {
        if (subgraph_stack.size() != k)
            list_remove_lastN(extension_list, privateStack.pop());  //pop() deletes head as side-effect
        subgraph.removeNode(subgraph_stack.pop());
    }

    private List<Integer> get_new_extension(int pivot_vertex) {
        return graph.adjacentNodes(pivot_vertex).stream()
                .filter(x -> !extension_list.contains(x) && x!=startVertex)
                .collect(toList());
    }

    private boolean pointerHead_is_outOfRange() {
        return pointerStack.getFirst() == extension_list.size();
    }

    private boolean pointerHead_is_pending() {
        return subgraph_stack.size() == pointerStack.size();
    }

    private int pivot_vertex() {
        return extension_list.get(pointerStack.getFirst());
    }
}
