package de.umr.core.graphs;

import java.util.Arrays;
import java.util.Collection;

import static java.util.stream.Collectors.joining;

/**
 * This class condenses functionality of undirected graphs that is
 * not directly dependent on the data-structure of the graph but only on intermediate
 * functions provided by the interface.
 * Therefore these methods can already be implemented here.
 * <p>
 * Check the Undirected_Graph Interface for comments on the methods.
 */
public abstract class Abstract_UndirectedGraph implements UndirectedGraph {

    /**
     * This method is only used internally and is intentionally not included in the interface.
     * Any vertex from @param vertexSet that has no neighbours in the graph gets removed in the graph.
     *
     * @param vertexSet Set of vertices that is checked if they have degree 0 and get removed if so.
     * @return true iff the graph has changed as a result of the call
     */
    protected boolean remove_vertices_if_isolated(Integer... vertexSet) {
        int vertexCount_before = vertexCount();
        Arrays.stream(vertexSet).filter(i -> getNeighbours(i).size() == 0).forEach(this::removeVertex);
        return vertexCount_before != vertexCount();
    }

    protected boolean remove_vertices_if_isolated(Collection<Integer> vertices) {
        return remove_vertices_if_isolated(vertices.toArray(new Integer[0]));
    }

    public String toString() {
        return getVertices().stream().sorted()
                .map(i -> i + ":\t" + getNeighbours(i).stream().sorted().map(String::valueOf).collect(joining(", ")))
                .collect(joining("\n"));
    }
}