package de.umr.core.io;

import de.umr.core.graphs.UndirectedGraph;
import de.umr.core.graphs.linked_hash_graph.LinkedHashGraph;
import de.umr.fixcon.SubgraphIterator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReader_Test {

    @Test
    void from_NetworkRepo_UnitTest() throws IOException {
        List<Integer[]> edges = FileReader.from_NetworkRepo(".//data//sample");
        assertEquals(1, edges.get(0)[0]);
        assertEquals(2, edges.get(0)[1]);
        assertEquals(2, edges.get(1)[0]);
        assertEquals(3, edges.get(1)[1]);
        assertEquals(3, edges.get(1)[1]);

        assertEquals(15, edges.get(20)[0]);
        assertEquals(17, edges.get(20)[1]);
        assertEquals(16, edges.get(21)[0]);
        assertEquals(17, edges.get(21)[1]);
    }

    @Test
    void from_NetworkRepo_IntegrationTest() throws IOException {
        UndirectedGraph g = new LinkedHashGraph(FileReader.from_NetworkRepo(".//data//sample"));
        assertTrue(g.areNeighbours(1,2));
        assertFalse(g.areNeighbours(1, 3));
        assertTrue(g.areNeighbours(2,3));
        assertFalse(g.areNeighbours(2, 4));
        assertTrue(g.areNeighbours(8,9));
        assertFalse(g.areNeighbours(8,10));
        assertTrue(g.areNeighbours(8,12));
        assertTrue(g.areNeighbours(15,16));
        assertTrue(g.areNeighbours(15,17));
        assertFalse(g.areNeighbours(15,18));
    }
}