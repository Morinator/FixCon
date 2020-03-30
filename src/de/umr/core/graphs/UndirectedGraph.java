package de.umr.core.graphs;

import java.util.Set;

/**
 * A finite undirected graph with Integer-IDs for every vertex. No two distinct vertices share the same ID.
 * It doesn't extend collection because a graph has both vertices and edges, therefore it contains
 * elements of different types. The Collection-Interface only supports homogeneous elements of one type.
 *
 * Methods which can potentially change the graph have the return value boolean. They return true iff the graph
 * was actually changed as a result of the method call.
 */
public interface UndirectedGraph {

    /**Deletes the specified vertex and also deletes all adjacent edges.
     * Does nothing if the vertex didn't exist when called.
     * @param x ID of the not that must be deleted.
     * @return true iff the graph changed as a result of the call (i.e. had existed before the method call).
     */
    boolean removeVertex(Integer x);

    /**Adds the edge and implicitly also its required vertices to the graph. Therefore, "addVertex" is NOT necessary
     * to call before this method. The order of the parameters doesn't matter, as the edges are undirected.
     * @param x ID of one of the vertices.
     * @param y ID of one of the vertices.
     * @return true iff the graph changed as a result of the call.
     */
    boolean addEdge(Integer x, Integer y);

    /**Deletes the edge between to specified vertices if it exists, does nothing otherwise.
     * The order of the arguments doesn't matter, as the edges are undirected.
     * @param x ID of one of the vertices.
     * @param y ID of one of the vertices.
     * @return true iff the graph changed as a result of the call.
     */
    boolean removeEdge(Integer x, Integer y);

    /**@return Number of total edges in the graph. Counts each edge only once, even though it connects 2 vertices.
     */
    default int edgeCount() {
        return getVertices().stream()
                .mapToInt(x -> getNeighbours(x).size())
                .sum() / 2;
    }

    /**@return Returns a set view of all vertices in the graph. The set is backed by the graph,
     * so changes to the set are reflected in the graph, and vice-versa.
     * If you want to use the set further, use a copy-constructor
     */
    Set<Integer> getVertices();

    /**@return Number of ALL vertices in the total graph. Returns 0 for an empty graph.
     */
    default int vertexCount() {
        return getVertices().size();
    }

    /**@return true iff the graph is empty i.e. it contains 0 vertices and therefore also 0 edges.
     */
    default boolean isEmpty() {
        return vertexCount() == 0;
    }

    /**
     * @return A String which represents the graph for easy readability.
     */
    @Override
    String toString();

    /**
     * @param x ID of the vertex you want to check
     * @return Number of neighbours of vertex "x". Returns 0 if the requested vertex doesn't exist.
     */
    default Integer degree(Integer x) {
        return getVertices().contains(x) ? getNeighbours(x).size() : 0;
    }

    /**
     * @param x ID of one of the vertices.
     * @param y ID of one of the vertices.
     * @return True iff "x" and "y" are neighbours (if an edge exists between "x" and "y").
     * Therefore returns false if "x" or "y" don't exist.
     */
    default boolean areNeighbours(Integer x, Integer y) {
        return getVertices().contains(x) && getNeighbours(x).contains(y);
    }

    /**
     * @param x The vertex whose neighbours you want.
     * @return A view of all the neighbours of this vertex. Never returns 0 because isolated vertices
     * are removed automatically. Returns null if the vertex doesn't exist.
     */
    Set<Integer> getNeighbours(Integer x);
}