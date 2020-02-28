package core;

import java.util.Set;

interface I_UndirGraph {

    //---
    //adding and deleting stuff

    /**Adds an edge and implicitly also its required nodes to the graph. Therefore, "addNode" is NOT necessary
     * to call before this method. The order of the parameters doesn't matter, as the edges are undirected.
     * @param x ID of one of the nodes.
     * @param y ID of one of the nodes.
     */
    void addEdge(Integer x, Integer y);

    /**Deletes the specified node and therefore also implicitly deletes all edges it was part of.
     * Does nothing if the node didn't exist when called.
     * @param x ID of the not that must be deleted.
     */
    void deleteNode(Integer x);

    /**Deletes the edge between to specified nodes if it exists, does nothing otherwise.
     * The order of the arguments doesn't matter.
     * @param x ID of one of the nodes.
     * @param y ID of one of the nodes.
     */
    void deleteEdge(Integer x, Integer y);


    //---
    //info about the general graph

    /**@return True iff it hasNode 0 nodes (and therefore also 0 edges)
     */
    boolean isEmpty();

    /**@return Number of total edges. Counts each edge only once, even though it connects 2 nodes.
     */
    int edgeCount();

    /**@return Returns the set of ALL nodes, regardless of their properties (e.g. how many neighbours)
     */
    Set<Integer> getNodes();

    /**@return Number of ALL nodes in the total graph
     */
    int nodeCount();


    //---
    //info about specific nodes

    /**@param x ID of the node you want to check
     * @return True iff a node with ID "x" exists in the graph
     */
    boolean hasNode(Integer x);

    /**
     * @param x ID of the node you want to check
     * @return Number of neighbours of node "x". Isolated nodes have 0 neighbours.
     */
    Integer degree(Integer x);

    /**
     * @param x ID of one of the nodes.
     * @param y ID of one of the nodes.
     * @return True iff "x" and "y" are neighbours (if an edge exists between them).
     */
    boolean adjacent(Integer x, Integer y);

    /**
     * @param x The node whose neighbours you want.
     * @return A set of all neighbours of "x" (all nodes that have an edge with "x").
     */
    Set<Integer> getNeighbours(Integer x);
}
